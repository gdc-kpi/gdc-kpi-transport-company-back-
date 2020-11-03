package com.ip737.transportcompany.transportcompany.services.impl;

import com.ip737.transportcompany.transportcompany.data.dao.VehicleDao;
import com.ip737.transportcompany.transportcompany.data.entities.Vehicle;
import com.ip737.transportcompany.transportcompany.services.AdminService;
import com.ip737.transportcompany.transportcompany.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@PropertySource("classpath:application.properties")
public class AdminServiceImpl implements AdminService {

    final private VehicleDao vehicleDao;

    public AdminServiceImpl(UserService userService, VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    @Override
    public void addVehicle(Vehicle vehicle) {
        vehicleDao.save(vehicle);
    }
}
