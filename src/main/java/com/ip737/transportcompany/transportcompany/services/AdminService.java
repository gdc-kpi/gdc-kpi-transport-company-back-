package com.ip737.transportcompany.transportcompany.services;

import com.ip737.transportcompany.transportcompany.data.entities.Vehicle;

import java.util.List;
import java.util.Map;

public interface AdminService {

    void addVehicle(Vehicle vehicle);

    List<Map<String, Object>> getAllVehicle();

    Vehicle getVehicle(String plate);

    List<Vehicle> getVehicleFilterByPartOfPlate(String plate);

}
