package com.ip737.transportcompany.transportcompany.services;

import com.ip737.transportcompany.transportcompany.data.entities.Coordinates;
import com.ip737.transportcompany.transportcompany.data.entities.Order;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    Order insertOrder(Order order);
    List<Coordinates> getPath(UUID id, Boolean update);
}
