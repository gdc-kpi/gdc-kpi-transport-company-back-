package com.ip737.transportcompany.transportcompany.data.rowmappers;

import com.ip737.transportcompany.transportcompany.data.entities.Driver;
import com.ip737.transportcompany.transportcompany.data.entities.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DriverMapper implements RowMapper<Driver> {

    @Override
    public Driver mapRow(ResultSet resultSet, int i) throws SQLException {

        return Driver.builder()
                .id(UUID.fromString(resultSet.getString("user_id")))
                .fullname(resultSet.getString("fullname"))
                .carPlate(resultSet.getString("plate"))
                .build();
    }
}
