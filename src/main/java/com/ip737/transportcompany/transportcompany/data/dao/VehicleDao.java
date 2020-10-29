package com.ip737.transportcompany.transportcompany.data.dao;

import com.ip737.transportcompany.transportcompany.data.entities.Vehicle;

public interface VehicleDao {

    void save(Vehicle vehicle);

    Vehicle getByOwnerId(int ownerId);
}
