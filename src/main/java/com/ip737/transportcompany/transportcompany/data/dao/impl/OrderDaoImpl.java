package com.ip737.transportcompany.transportcompany.data.dao.impl;

import com.ip737.transportcompany.transportcompany.configs.constants.Constants;
import com.ip737.transportcompany.transportcompany.configs.constants.SqlConstants;
import com.ip737.transportcompany.transportcompany.data.entities.Coordinates;
import com.ip737.transportcompany.transportcompany.data.entities.FirstLastPoint;
import com.ip737.transportcompany.transportcompany.data.entities.Order;
import com.ip737.transportcompany.transportcompany.data.rowmappers.FirstLastPointMapper;
import com.ip737.transportcompany.transportcompany.data.rowmappers.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.UUID;


@Repository
@Slf4j
public class OrderDaoImpl implements com.ip737.transportcompany.transportcompany.data.dao.OrderDao {
    final private JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //TODO fix this
    @Override
    public LinkedList<Coordinates[]> getPath(UUID id) {
        return null;
    }

    //TODO fix this
    @Override
    public FirstLastPoint getFirstLastPoint(UUID id)
    {
        return jdbcTemplate.queryForObject(SqlConstants.GET_SOURCE_AND_DESTINATION_FOR_ORDER,
                new Object[]{id},
                new FirstLastPointMapper());
        //return new Coordinates[]{new Coordinates(30.444648, 50.451482), new Coordinates(30.448861, 50.447617)};
    }

    //TODO fix this
    @Override
    public void setPath(UUID id, Coordinates[] path)
    {

    }

    //TODO fix this
    @Override
    public void insertPath(UUID id, Coordinates[] path)
    {

    }

    @Override
    public Order insert(Order order) {
        order.setOrderId(UUID.randomUUID().toString());
        order.setTitle("Order " + order.getOrderId());

        jdbcTemplate.update(SqlConstants.USER_INSERT_ODER,
                UUID.fromString(order.getOrderId()),
                order.getSourceLongitude(), order.getSourceLatitude(), order.getDestinationLongitude(), order.getSourceLatitude(), order.getVolume(),
                order.getCar_id(), UUID.fromString(order.getAdmins_id()), order.getTitle(), order.getDescription(), order.getWeight(), order.getDeadline(), order.getStatus()
        );
        return order;
    }
}
