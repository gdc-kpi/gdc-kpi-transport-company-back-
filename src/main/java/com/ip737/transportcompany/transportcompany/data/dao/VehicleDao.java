package com.ip737.transportcompany.transportcompany.data.dao;

import com.ip737.transportcompany.transportcompany.data.dao.impl.VehicleDaoImpl;
import com.ip737.transportcompany.transportcompany.data.entities.Vehicle;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface VehicleDao {

    void save(Vehicle vehicle);

    void addOwner(UUID ownerId, String plate);

    Vehicle getByOwnerId(UUID ownerId);

    Vehicle getOwnerId(String carId);

    Vehicle getByPlate(String plate);

    List<Map<String, Object>> getAll();

    List<Vehicle> getFree();
}
