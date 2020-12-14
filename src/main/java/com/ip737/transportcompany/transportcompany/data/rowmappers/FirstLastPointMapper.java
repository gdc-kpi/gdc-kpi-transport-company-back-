package com.ip737.transportcompany.transportcompany.data.rowmappers;

import com.ip737.transportcompany.transportcompany.data.entities.Coordinates;
import com.ip737.transportcompany.transportcompany.data.entities.FirstLastPoint;
import com.ip737.transportcompany.transportcompany.data.entities.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FirstLastPointMapper implements RowMapper<FirstLastPoint> {

    @Override
    public FirstLastPoint mapRow(ResultSet resultSet, int j) throws SQLException {
        return FirstLastPoint.builder()
                .source(new Coordinates(resultSet.getString("source")))
                .destination(new Coordinates(resultSet.getString("destination")))
                .build();
    }
}