package com.ip737.transportcompany.transportcompany.data.dao;

import com.ip737.transportcompany.transportcompany.configs.constants.SqlConstants;
import com.ip737.transportcompany.transportcompany.data.entities.Order;
import com.ip737.transportcompany.transportcompany.data.rowmappers.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
@Slf4j
public class OrderDao {
    final private JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Order insert(Order order) {
        order.setOrderId(UUID.randomUUID().toString());
        order.setTitle("Order " + order.getOrderId());

        jdbcTemplate.update(SqlConstants.USER_INSERT_ODER,
                UUID.fromString(order.getOrderId()),
                order.getSourceLatitude(), order.getSourceLongitude(),order.getDestinationLatitude(), order.getDestinationLongitude(),  order.getVolume(),
                order.getPlate(), UUID.fromString(order.getAdmins_id()), order.getTitle(), order.getDescription(), order.getWeight(), order.getDeadline(), order.getStatus()
        );
        return order;
    }


    public void assignDriver(String orderId, String carId) {
        jdbcTemplate.update(SqlConstants.ORDER_ASSIGN_DRIVER,
                carId, UUID.fromString(orderId));

    }

    public Order getOrder(String orderId) {
        try {
            return jdbcTemplate.queryForObject(SqlConstants.GET_ORDER_BY_ID,
                    new Object[]{orderId},
                    new OrderMapper());
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    public void chageStatus(Order order) {
        jdbcTemplate.update(SqlConstants.ORDER_CHANGE_STATUS,
                order.getStatus(), UUID.fromString(order.getOrderId()));
    }
}
