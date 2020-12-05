package com.ip737.transportcompany.transportcompany.services;

import com.ip737.transportcompany.transportcompany.data.entities.Coordinates;
import com.ip737.transportcompany.transportcompany.data.entities.Order;
import com.ip737.transportcompany.transportcompany.data.entities.Vehicle;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    Order insertOrder(Order order);

    void assignDriver(String orderId, String driverId);

    Order getOrder(String orderId);

    void changeStatus(String orderId, String status, String driverId);

    List<Coordinates> getPath(UUID id, Boolean update);
    List<Vehicle> getDriversList();
}
