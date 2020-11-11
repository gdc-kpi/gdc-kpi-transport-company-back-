package com.ip737.transportcompany.transportcompany.services.impl;

import com.ip737.transportcompany.transportcompany.configs.constants.Constants;
import com.ip737.transportcompany.transportcompany.data.dao.DriverDao;
import com.ip737.transportcompany.transportcompany.data.entities.Order;
import com.ip737.transportcompany.transportcompany.data.entities.Vehicle;
import com.ip737.transportcompany.transportcompany.exceptions.ValidationException;
import com.ip737.transportcompany.transportcompany.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Vehicle car = vehicleDao.getOwnerId(order.getCar_id());
        if (car == null) {
            throw new ValidationException("No car with plate " + order.getCar_id());
        } else  if (car.getUserId() == null) {
            throw new ValidationException("The car with plate " + order.getCar_id() + " hasn't been assigned to anyone yet");
        } else if (!driverDao.driverWorksThisDay(car.getUserId().toString(), order.getDeadline())) {
            throw new ValidationException("The driver with id " + car.getUserId() + " has a day off on this day");
        } else if (driverDao.getOrderCountForTheDay(order.getCar_id(), order.getDeadline()) == Constants.MAX_DRIVER_ORDERS_FOR_DAY) {
            throw new ValidationException("The driver with id " + car.getUserId() + " already has max orders per day assigned for this day");
        }

        return orderDao.insert(order);
    }


}
