package com.ip737.transportcompany.transportcompany.data.dao.impl;

import com.ip737.transportcompany.transportcompany.configs.constants.SqlConstants;
import com.ip737.transportcompany.transportcompany.data.dao.VehicleDao;
import com.ip737.transportcompany.transportcompany.data.entities.Vehicle;
import com.ip737.transportcompany.transportcompany.data.rowmappers.VehicleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;


@Repository
@Slf4j
public class VehicleDaoImpl implements VehicleDao {

    final private JdbcTemplate jdbcTemplate;

    @Autowired
    public VehicleDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Vehicle getByOwnerId(UUID ownerId) {
        try {
            return jdbcTemplate.queryForObject(SqlConstants.VEHICLE_GET_BY_USER_ID,
                    new Object[]{ownerId},
                    new VehicleMapper());
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    @Override
    public Vehicle getOwnerId(String carId) {
        try {
            return jdbcTemplate.queryForObject(SqlConstants.VEHICLE_GET_BY_PLATE,
                    new Object[]{carId},
                    new VehicleMapper());
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> getFree() {
        try {
            return jdbcTemplate.queryForList(SqlConstants.VEHICLE_GET_FREE,
                    new Object[]{},
                    new VehicleMapper());
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    @Override
    public void save(Vehicle vehicle) {
        jdbcTemplate.update(SqlConstants.VEHICLE_SAVE_QUERY,
                vehicle.getPlate(), vehicle.getCapacity(), vehicle.getLoadCapacity(),
                vehicle.getFuelConsumption(), vehicle.getUserId()
        );
    }
}

