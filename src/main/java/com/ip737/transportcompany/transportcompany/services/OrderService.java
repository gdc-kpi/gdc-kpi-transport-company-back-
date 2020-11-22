package com.ip737.transportcompany.transportcompany.services;

import com.ip737.transportcompany.transportcompany.data.entities.Order;

public interface OrderService {
    Order insertOrder(Order order);

    void assignDriver(String orderId, String driverId);

    Order getOrder(String orderId);

    void changeStatus(String orderId, String status, String driverId);
}
