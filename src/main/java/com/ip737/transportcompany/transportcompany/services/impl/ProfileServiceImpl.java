package com.ip737.transportcompany.transportcompany.services.impl;

import com.ip737.transportcompany.transportcompany.configs.constants.Constants;
import com.ip737.transportcompany.transportcompany.data.dao.VehicleDao;
import com.ip737.transportcompany.transportcompany.data.entities.User;
import com.ip737.transportcompany.transportcompany.data.entities.Vehicle;
import com.ip737.transportcompany.transportcompany.exceptions.ValidationException;
import com.ip737.transportcompany.transportcompany.services.ProfileService;
import com.ip737.transportcompany.transportcompany.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@PropertySource("classpath:application.properties")
public class ProfileServiceImpl implements ProfileService {

    final private VehicleDao vehicleDao;

    public ProfileServiceImpl(UserService userService, VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    @Override
    public void addVehicle(Vehicle vehicle) {
        vehicleDao.save(vehicle);
    }
}
