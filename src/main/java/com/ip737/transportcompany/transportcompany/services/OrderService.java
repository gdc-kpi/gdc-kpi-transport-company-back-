package com.ip737.transportcompany.transportcompany.services;

import com.ip737.transportcompany.transportcompany.data.entities.Order;

import java.util.List;

public interface OrderService {
    Order insertOrder(Order order);
    List driversList(double weight, double volume, String deadline);
}
