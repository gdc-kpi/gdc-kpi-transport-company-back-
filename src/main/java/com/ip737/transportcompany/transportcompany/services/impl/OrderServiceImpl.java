package com.ip737.transportcompany.transportcompany.services.impl;

import com.ip737.transportcompany.transportcompany.data.dao.OrderDao;
import com.ip737.transportcompany.transportcompany.data.entities.Order;
import com.ip737.transportcompany.transportcompany.services.OrderService;
import com.ip737.transportcompany.transportcompany.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@PropertySource("classpath:application.properties")
public class OrderServiceImpl implements OrderService {

    final private OrderDao orderDao;



    public OrderServiceImpl(UserService userService, OrderDao orderDao) {
        this.orderDao = orderDao;
    }


    @Override
    public void insertOrder(Order order) {
        orderDao.insert(order);
    }
}
