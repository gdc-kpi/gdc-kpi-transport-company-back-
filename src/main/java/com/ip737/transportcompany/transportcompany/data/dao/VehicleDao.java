package com.ip737.transportcompany.transportcompany.data.dao;

import com.ip737.transportcompany.transportcompany.configs.constants.SqlConstants;
import com.ip737.transportcompany.transportcompany.data.entities.Order;
import com.ip737.transportcompany.transportcompany.data.entities.Vehicle;
import com.ip737.transportcompany.transportcompany.data.rowmappers.OrderMapper;
import com.ip737.transportcompany.transportcompany.data.rowmappers.VehicleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;


@Repository
@Slf4j
public class VehicleDao {

    final private JdbcTemplate jdbcTemplate;

    @Autowired
    public VehicleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Vehicle getByOwnerId(UUID ownerId) {
        try {
            return jdbcTemplate.queryForObject(SqlConstants.VEHICLE_GET_BY_USER_ID,
                    new Object[]{ownerId},
                    new VehicleMapper());
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }


    public Vehicle getByPlate(String plate) {
        try {
            return jdbcTemplate.queryForObject(SqlConstants.VEHICLE_GET_BY_PLATE,
                    new Object[]{plate},
                    new VehicleMapper());
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    public Vehicle getOwnerId(String carId) {
        try {
            return jdbcTemplate.queryForObject(SqlConstants.VEHICLE_GET_BY_PLATE,
                    new Object[]{carId},
                    new VehicleMapper());
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    public List<Map<String, Object>> getAll() {
        try {
            return jdbcTemplate.queryForList(SqlConstants.VEHICLE_GET_ALL);
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    public List<Vehicle> getFree() {
        try {
            return jdbcTemplate.query(SqlConstants.VEHICLE_GET_FREE, new BeanPropertyRowMapper<>(Vehicle.class));
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    public List<Vehicle> getVehicleFilterByPartOfPlate(String plate) {
        try {
            return jdbcTemplate.query(SqlConstants.VEHICLE_GET_FILTERED, new Object[]{ "%" + plate + "%"}, new VehicleMapper());
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    public void save(Vehicle vehicle) {
        jdbcTemplate.update(SqlConstants.VEHICLE_SAVE_QUERY,
                vehicle.getPlate(), vehicle.getCapacity(), vehicle.getLoadCapacity(),
                vehicle.getFuelConsumption(), vehicle.getUserId()
        );
    }

    public void addOwner(UUID ownerId, String plate) {
        jdbcTemplate.update(SqlConstants.VEHICLE_ADD_OWNER, ownerId, plate);
    }


    public List<Order> getOrdersFilterByDriver(String driverId, String status){
        try {
            return jdbcTemplate.query(SqlConstants.GET_ORDERS_BY_STATUS_FOR_DRIVER, new Object[]{ driverId, status},new OrderMapper());

        } catch (EmptyResultDataAccessException | NullPointerException e ) {
            return null;
        }
    }


    public List<Order> getOrdersFilterByStatus(String adminId, String status){
        try {
            return jdbcTemplate.query(SqlConstants.GET_ORDERS_BY_STATUS_FOR_ADMIN, new Object[]{ adminId, status},new OrderMapper());

        } catch (EmptyResultDataAccessException | NullPointerException e ) {
            return null;
        }
    }



    public List<Order> getOrdersFilterByDriver2(String driverId, String status,  String status2){
        try {
            return jdbcTemplate.query(SqlConstants.GET_ORDERS_BY_STATUS_FOR_DRIVER2, new Object[]{ driverId, status, status2},new OrderMapper());

        } catch (EmptyResultDataAccessException | NullPointerException e ) {
            return null;
        }
    }


    public List<Order> getOrdersFilterByStatus2(String adminId, String status, String status2){
        try {
            return jdbcTemplate.query(SqlConstants.GET_ORDERS_BY_STATUS_FOR_ADMIN2, new Object[]{ adminId, status, status2},new OrderMapper());

        } catch (EmptyResultDataAccessException | NullPointerException e ) {
            return null;
        }
    }
}

