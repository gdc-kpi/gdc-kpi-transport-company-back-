package com.ip737.transportcompany.transportcompany.data.dao;

import com.ip737.transportcompany.transportcompany.data.entities.Coordinates;
import com.ip737.transportcompany.transportcompany.data.entities.FirstLastPoint;
import com.ip737.transportcompany.transportcompany.data.entities.Order;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public interface OrderDao {
    Order insert(Order order);
    FirstLastPoint getFirstLastPoint(UUID id);
    LinkedList<Coordinates> getPath(UUID id);
    void setPath(UUID id, List<Coordinates> path);
    void insertPath(UUID id, List<Coordinates> path);
}
