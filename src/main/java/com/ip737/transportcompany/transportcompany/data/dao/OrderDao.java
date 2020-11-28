package com.ip737.transportcompany.transportcompany.data.dao;

import com.ip737.transportcompany.transportcompany.configs.constants.SqlConstants;
import com.ip737.transportcompany.transportcompany.data.entities.Coordinates;
import com.ip737.transportcompany.transportcompany.data.entities.FirstLastPoint;
import com.ip737.transportcompany.transportcompany.data.entities.Order;
import com.ip737.transportcompany.transportcompany.data.rowmappers.CoordinateListMapper;
import com.ip737.transportcompany.transportcompany.data.rowmappers.FirstLastPointMapper;
import com.ip737.transportcompany.transportcompany.data.rowmappers.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class OrderDao {
    final private JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public LinkedList<Coordinates> getPath(UUID id) {
        var res = jdbcTemplate.query(SqlConstants.GET_PATH_FOR_ID,
                new Object[]{id},
                new CoordinateListMapper()
        );
        if (res.isEmpty())
            return new LinkedList<Coordinates>();
        else
            return res.get(0);
    }

    public FirstLastPoint getFirstLastPoint(UUID id)
    {
        return jdbcTemplate.queryForObject(SqlConstants.GET_SOURCE_AND_DESTINATION_FOR_ORDER,
                new Object[]{id},
                new FirstLastPointMapper()
        );
    }

    public void setPath(UUID id, List<Coordinates> path)
    {
        jdbcTemplate.update("UPDATE paths set path=PATH'["
                        + path.stream().map(Object::toString).collect(Collectors.joining(", "))
                        + "]' WHERE order_id = ? ;",
                id
        );
    }

    public void insertPath(UUID id, List<Coordinates> path)
    {
        jdbcTemplate.update(SqlConstants.INSERT_PATH_FOR_ID_HACK  + '[' + path.stream().map(Object::toString).collect(Collectors.joining(", ")) + "]');",
                id
        );
    }

    public Order insert(Order order) {
        order.setOrderId(UUID.randomUUID().toString());
        order.setTitle("Order " + order.getOrderId());

        jdbcTemplate.update(SqlConstants.USER_INSERT_ODER,
                UUID.fromString(order.getOrderId()),
                order.getSourceLatitude(), order.getSourceLongitude(), order.getDestinationLatitude(), order.getDestinationLongitude(), order.getVolume(),
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