package com.ip737.transportcompany.transportcompany.data.dao;

import com.ip737.transportcompany.transportcompany.data.entities.Vehicle;

import java.util.UUID;

public interface VehicleDao {

    void save(Vehicle vehicle);

    void delete(String plate, UUID ownerID);

    Vehicle getByOwnerId(UUID ownerId);

    Vehicle getOwnerId(String carId);
}
