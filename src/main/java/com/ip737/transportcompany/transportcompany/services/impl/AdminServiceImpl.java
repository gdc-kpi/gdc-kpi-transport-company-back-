package com.ip737.transportcompany.transportcompany.services.impl;

import com.ip737.transportcompany.transportcompany.data.dao.DriverDao;
import com.ip737.transportcompany.transportcompany.data.dao.VehicleDao;
import com.ip737.transportcompany.transportcompany.data.entities.Driver;
import com.ip737.transportcompany.transportcompany.data.entities.Vehicle;
import com.ip737.transportcompany.transportcompany.services.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@PropertySource("classpath:application.properties")
public class AdminServiceImpl implements AdminService {

    final private VehicleDao vehicleDao;
    final private DriverDao driverDao;


    public AdminServiceImpl(VehicleDao vehicleDao,DriverDao driverDao) {
        this.vehicleDao = vehicleDao;
        this.driverDao = driverDao;

    }

    @Override
    public void addVehicle(Vehicle vehicle) {
        vehicleDao.save(vehicle);
    }

    @Override
    public List<Map<String, Object>> getAllVehicle() {
        return vehicleDao.getAll();
    }

    @Override
    public Vehicle getVehicle(String plate) {
        return vehicleDao.getByPlate(plate);
    }

    @Override
    public List<Vehicle> getVehicleFilterByPartOfPlate(String plate) {
        return vehicleDao.getVehicleFilterByPartOfPlate(plate);
    }

    @Override
    public List<Driver> getDriversFilterByName(String plate) {
        return driverDao.getDriversFilterByName(plate);
    }
}
