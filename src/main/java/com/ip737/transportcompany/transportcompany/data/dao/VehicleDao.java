package com.ip737.transportcompany.transportcompany.data.dao;

import com.ip737.transportcompany.transportcompany.data.entities.Vehicle;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface VehicleDao {

    void save(Vehicle vehicle);

    Vehicle getByOwnerId(UUID ownerId);

    Vehicle getOwnerId(String carId);

    List<Map<String, Object>> getFree();
}
