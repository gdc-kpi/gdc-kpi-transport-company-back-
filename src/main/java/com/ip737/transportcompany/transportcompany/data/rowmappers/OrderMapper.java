package com.ip737.transportcompany.transportcompany.data.rowmappers;

import com.ip737.transportcompany.transportcompany.data.entities.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet resultSet, int i) throws SQLException {

        return Order.builder()
                .orderId(resultSet.getString("order_id"))
                .admins_name(resultSet.getString("admin_name"))
                .driver_name(resultSet.getString("driver_name"))
                .sourceLatitude(resultSet.getDouble("s1"))
                .sourceLongitude(resultSet.getDouble("s2"))
                .destinationLatitude(resultSet.getDouble("d1"))
                .destinationLongitude(resultSet.getDouble("d2"))
                .volume(resultSet.getDouble("volume"))
                .weight(resultSet.getDouble("weight"))
                .plate(resultSet.getString("plate"))
                .driver_id(resultSet.getString("driver"))
                .admins_id(resultSet.getString("admin_id"))
                .title(resultSet.getString("title"))
                .description(resultSet.getString("description"))
                .deadline(resultSet.getTimestamp("deadline").toLocalDateTime())
                .status(resultSet.getString("status"))
                .build();
    }
}