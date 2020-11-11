package com.ip737.transportcompany.transportcompany.services.impl;

import com.ip737.transportcompany.transportcompany.configs.constants.Constants;
import com.ip737.transportcompany.transportcompany.data.dao.DriverDao;
import com.ip737.transportcompany.transportcompany.data.dao.OrderDao;
import com.ip737.transportcompany.transportcompany.data.dao.VehicleDao;
import com.ip737.transportcompany.transportcompany.data.entities.Order;
import com.ip737.transportcompany.transportcompany.data.entities.Vehicle;
import com.ip737.transportcompany.transportcompany.exceptions.ValidationException;
import com.ip737.transportcompany.transportcompany.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    final private OrderDao orderDao;
    final private DriverDao driverDao;
    final private VehicleDao vehicleDao;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, DriverDao driverDao, VehicleDao vehicleDao) {
        this.orderDao = orderDao;
        this.driverDao = driverDao;
        this.vehicleDao = vehicleDao;
    }

    @Override
    public Order insertOrder(Order order) {
        Vehicle car = vehicleDao.getOwnerId(order.getPlate());
        if (car == null) {
            throw new ValidationException("No car with plate " + order.getPlate());
        } else  if (car.getUserId() == null) {
            throw new ValidationException("The car with plate " + order.getPlate() + " hasn't been assigned to anyone yet");
        } else if (!driverDao.driverWorksThisDay(car.getUserId().toString(), order.getDeadline())) {
            throw new ValidationException("The driver with id " + car.getUserId() + " has a day off on this day");
        } else if (driverDao.getOrderCountForTheDay(order.getPlate(), order.getDeadline()) == Constants.MAX_DRIVER_ORDERS_FOR_DAY) {
            throw new ValidationException("The driver with id " + car.getUserId() + " already has max orders per day assigned for this day");
        }
        order =  orderDao.insert(order);
        return orderDao.getOrder(order.getOrderId());
    }

    @Override
    public void assignDriver(String orderId, String driverId) {
        Order order = orderDao.getOrder(orderId);
        if(order == null ) {
            throw new ValidationException("The order with id " + orderId + " not found");
        } else if(! order.getStatus().equals(Constants.Status.PENDING_CONFIRMATION.toString())){
            throw new ValidationException("The order with id " + orderId + " cannot be changed as status is " + order.getStatus());
        } else {
           Vehicle vehicle = vehicleDao.getByOwnerId(UUID.fromString(driverId));
            if(vehicle == null) {
                throw new ValidationException("The car for user with  id " + driverId + " not found");

            }
            orderDao.assignDriver(orderId, vehicle.getPlate());
        }
    }

    @Override
    public Order getOrder(String orderId) {
      return orderDao.getOrder(orderId);
    }


}
