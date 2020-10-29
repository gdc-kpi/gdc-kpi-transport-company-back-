package com.ip737.transportcompany.transportcompany.data.dao.impl;

import com.ip737.transportcompany.transportcompany.configs.constants.SqlConstants;
import com.ip737.transportcompany.transportcompany.data.dao.VehicleDao;
import com.ip737.transportcompany.transportcompany.data.rowmappers.VehicleMapper;
import com.ip737.transportcompany.transportcompany.data.entities.Vehicle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
@Slf4j
public class VehicleDaoImpl implements VehicleDao {

    final private JdbcTemplate jdbcTemplate;

    @Autowired
    public VehicleDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Vehicle getByOwnerId(int ownerId) {
        try {
            return jdbcTemplate.queryForObject(SqlConstants.VEHICLE_GET_BY_ID,
                    new Object[]{ownerId},
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
