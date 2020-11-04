package com.ip737.transportcompany.transportcompany.data.dao.impl;

import com.ip737.transportcompany.transportcompany.configs.constants.Constants;
import com.ip737.transportcompany.transportcompany.configs.constants.SqlConstants;
import com.ip737.transportcompany.transportcompany.data.entities.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

//import com.ip737.transportcompany.transportcompany.data.rowmappers.VehicleMapper;
//import com.ip737.transportcompany.transportcompany.data.entities.Vehicle;

@Repository
@Slf4j
public class OrderDaoImpl implements com.ip737.transportcompany.transportcompany.data.dao.OrderDao {
    final private JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Order insert(Order order) {
        order.setOrderId(UUID.randomUUID().toString());
        order.setTitle("Order " + order.getOrderId());
        order.setStatus(Constants.Status.PENDING_CONFIRMATION.toString());
        jdbcTemplate.update(SqlConstants.USER_INSERT_ODER,
                UUID.fromString(order.getOrderId()),
                order.getSourceLongitude(), order.getSourceLatitude(), order.getDestinationLongitude(), order.getSourceLatitude(), order.getVolume(),
                order.getCar_id(), UUID.fromString(order.getAdmins_id()), order.getTitle(), order.getDescription(), order.getWeight()
        );
        return order;
    }
}
