package com.ip737.transportcompany.transportcompany.services;

import com.ip737.transportcompany.transportcompany.configs.constants.SqlConstants;
import com.ip737.transportcompany.transportcompany.data.entities.Order;
import com.ip737.transportcompany.transportcompany.data.entities.Vehicle;
import com.ip737.transportcompany.transportcompany.data.rowmappers.OrderMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.util.Pair;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface DriverService {
    Vehicle getVehicle(UUID userId);

    Vehicle getVehicle(String plate);

    List<Vehicle> getFreeVehicle();

    void chooseVehicle(UUID userId, String plate);


    List<Order> getOrdersFilterByDriver(String driverId, String status);
    List<Order> getOrdersFilterByDriver(String driverId, String status, String status2);

    List<Pair<Boolean, Date>> getDaysOff(UUID userId);
}
