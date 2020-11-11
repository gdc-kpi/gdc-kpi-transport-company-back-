package com.ip737.transportcompany.transportcompany.services.impl;

import com.ip737.transportcompany.transportcompany.configs.constants.SqlConstants;
import com.ip737.transportcompany.transportcompany.data.dao.VehicleDao;
import com.ip737.transportcompany.transportcompany.data.entities.Order;
import com.ip737.transportcompany.transportcompany.data.entities.Vehicle;
import com.ip737.transportcompany.transportcompany.data.rowmappers.OrderMapper;
import com.ip737.transportcompany.transportcompany.services.DriverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class DriverServiceImpl implements DriverService {

    final private VehicleDao vehicleDao;

    public DriverServiceImpl(VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    @Override
    public Vehicle getVehicle(UUID userId) {
        return vehicleDao.getByOwnerId(userId);
    }

    @Override
    public Vehicle getVehicle(String plate) {
        return vehicleDao.getByPlate(plate);
    }

    @Override
    public List<Vehicle> getFreeVehicle() {
        return vehicleDao.getFree();
    }

    @Override
    public void chooseVehicle(UUID userId, String plate){
        vehicleDao.addOwner(userId, plate);
    }


    public List<Order> getOrdersFilterByDriver(String driverId, String status){
        return vehicleDao.getOrdersFilterByDriver(driverId, status);
    }

}
