package com.ip737.transportcompany.transportcompany.data.rowmappers;

import com.ip737.transportcompany.transportcompany.data.entities.DayOff;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DayOffMapper implements RowMapper<DayOff> {
    @Override
    public DayOff mapRow(ResultSet resultSet, int i) throws SQLException {

        return DayOff.builder()
        .driverId(UUID.fromString(resultSet.getString("user_id")))
        .date(resultSet.getDate("date"))
        .build();
        }
}
