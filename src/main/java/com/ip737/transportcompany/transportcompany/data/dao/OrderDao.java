package com.ip737.transportcompany.transportcompany.data.dao;

import com.ip737.transportcompany.transportcompany.data.entities.Order;

import java.util.List;

public interface OrderDao {
    Order insert(Order order);
    List getDriversList(double weight, double volume, String deadline);
}
