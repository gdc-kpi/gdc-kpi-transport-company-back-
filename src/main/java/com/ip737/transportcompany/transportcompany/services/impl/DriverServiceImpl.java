package com.ip737.transportcompany.transportcompany.services.impl;

import com.ip737.transportcompany.transportcompany.data.entities.Vehicle;
import com.ip737.transportcompany.transportcompany.services.DriverService;
import lombok.extern.slf4j.Slf4j;
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
}
