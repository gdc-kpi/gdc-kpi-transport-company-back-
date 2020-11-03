package com.ip737.transportcompany.transportcompany.services;

import com.ip737.transportcompany.transportcompany.data.entities.Vehicle;

import java.util.UUID;

public interface DriverService {
    Vehicle getVehicle(UUID userId);

}
