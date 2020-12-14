package com.ip737.transportcompany.transportcompany.services;

import com.ip737.transportcompany.transportcompany.data.entities.DayOff;
import com.ip737.transportcompany.transportcompany.data.entities.Driver;
import com.ip737.transportcompany.transportcompany.data.entities.Order;
import com.ip737.transportcompany.transportcompany.data.entities.Vehicle;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface AdminService {

    void addVehicle(Vehicle vehicle);

    List<Map<String, Object>> getAllVehicle();

    Vehicle getVehicle(String plate);

    List<Map<String, Object>> getVehicleFilterByPartOfPlate(String plate);

    List<Driver> getDriversFilterByName(String plate);

    List<Order> getOrdersFilterByStatus(String driverId, String status);
    List<Order> getOrdersFilterByStatus(String driverId, String status, String status2);

    List<Map<String, Object>> getDaysOff();

    void approveDaysOff(UUID driverId, Date[] dates, String isApproved);
}
