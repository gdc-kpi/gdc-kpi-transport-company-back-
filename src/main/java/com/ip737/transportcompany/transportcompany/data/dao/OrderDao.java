package com.ip737.transportcompany.transportcompany.data.dao;

import com.ip737.transportcompany.transportcompany.data.entities.Coordinates;
import com.ip737.transportcompany.transportcompany.data.entities.Order;

import java.util.LinkedList;
import java.util.UUID;

public interface OrderDao {
    Order insert(Order order);
    Coordinates[] getFirstLastPoint(UUID id);
    LinkedList<Coordinates[]> getPath(UUID id);
    void setPath(UUID id, Coordinates[] path);
    void insertPath(UUID id, Coordinates[] path);
}
