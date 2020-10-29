package com.ip737.transportcompany.transportcompany.data.rowmappers;

import com.ip737.transportcompany.transportcompany.data.entities.Vehicle;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class VehicleMapper implements RowMapper<Vehicle> {

    @Override
    public Vehicle mapRow(ResultSet resultSet, int i) throws SQLException {

        return Vehicle.builder()
                .plate(resultSet.getString("plate"))
                .capacity(resultSet.getDouble("capacity"))
                .loadCapacity(resultSet.getDouble("load_capacity"))
                .fuelConsumption(resultSet.getInt("fuel_consumption"))
                .build();
    }
}