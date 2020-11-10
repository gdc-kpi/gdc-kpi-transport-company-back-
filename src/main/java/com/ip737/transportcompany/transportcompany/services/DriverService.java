package com.ip737.transportcompany.transportcompany.services;

import com.ip737.transportcompany.transportcompany.data.entities.Vehicle;

import java.util.List;
import java.util.UUID;

public interface DriverService {
    Vehicle getVehicle(UUID userId);

    Vehicle getVehicle(String plate);

    List<Vehicle> getFreeVehicle();

    void chooseVehicle(UUID userId, String plate);
}
