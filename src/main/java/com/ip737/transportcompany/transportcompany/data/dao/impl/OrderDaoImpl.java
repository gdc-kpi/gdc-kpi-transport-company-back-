package com.ip737.transportcompany.transportcompany.data.dao.impl;

import com.ip737.transportcompany.transportcompany.configs.constants.SqlConstants;

//import com.ip737.transportcompany.transportcompany.data.rowmappers.VehicleMapper;
//import com.ip737.transportcompany.transportcompany.data.entities.Vehicle;
import com.ip737.transportcompany.transportcompany.data.entities.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@Slf4j
public class OrderDaoImpl implements com.ip737.transportcompany.transportcompany.data.dao.OrderDao{
    final private JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert(Order order) {
        jdbcTemplate.update(SqlConstants.USER_INSERT_ODER,
                order.getSource(), order.getDestination(), order.getVolume(),
                order.getDrivers_id(), order.getAdmins_id(), order.getTitle(), order.getDescription()
        );
    }
}
