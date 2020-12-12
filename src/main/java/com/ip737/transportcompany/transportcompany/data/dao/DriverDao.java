package com.ip737.transportcompany.transportcompany.data.dao;

import com.ip737.transportcompany.transportcompany.configs.constants.SqlConstants;
import com.ip737.transportcompany.transportcompany.data.entities.DayOff;
import com.ip737.transportcompany.transportcompany.data.entities.Driver;
import com.ip737.transportcompany.transportcompany.data.entities.Vehicle;
import com.ip737.transportcompany.transportcompany.data.rowmappers.DayOffMapper;
import com.ip737.transportcompany.transportcompany.data.rowmappers.DriverMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Repository
@Slf4j
public class DriverDao {
    final private JdbcTemplate jdbcTemplate;

    @Autowired
    public DriverDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Boolean driverWorksThisDay(String userID, LocalDateTime day){
          try {
              return jdbcTemplate.queryForObject(SqlConstants.GET_DRIVERS_DAY_OFF_FOR_THE_DATE,
                      new Object[]{UUID.fromString(userID), Date.from(day.toInstant(ZoneOffset.UTC))},
                      Integer.class) == 0;
          } catch (EmptyResultDataAccessException | NullPointerException e ) {
              return true;
        }
    }


    public int getOrderCountForTheDay(String carId, LocalDateTime day){
        try {
            int x = jdbcTemplate.queryForObject(SqlConstants.GET_DRIVERS_ORDERS_FOR_THE_DAY,
                    new Object[]{carId ,Date.from( day.atZone( ZoneId.systemDefault()).toInstant())},
                    Integer.class);
            return x;
        } catch (EmptyResultDataAccessException | NullPointerException e ) {
            return 0;
        }
    }

    public List<Driver> getDriversFilterByName(String fullname){
        try {
            return jdbcTemplate.query(SqlConstants.DRIVER_GET_FILTERED, new Object[]{ "%" + fullname + "%"},new DriverMapper());

        } catch (EmptyResultDataAccessException | NullPointerException e ) {
            return null;
        }
    }

    public List<DayOff> getAllDaysOffOfDrivers() {
        try {
            return jdbcTemplate.query(SqlConstants.GET_DRIVERS_DAYS_OFF_FOR_ADMIN_APPROVES,
                    new Object[]{}, new DayOffMapper());
        } catch (EmptyResultDataAccessException | NullPointerException e ) {
            return null;
        }
    }
    public List<Driver> getDrivers(double weight, double volume, LocalDateTime deadline) {
        try {
            return jdbcTemplate.query(SqlConstants.GET_DRIVERS_LIST,
                    new Object[]{String.valueOf(weight), String.valueOf(volume), Date.from(deadline.toInstant(ZoneOffset.UTC))},
                    new DriverMapper());
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    public void approveDaysOff(UUID driverId, java.util.Date[] daysOff, String isApproved){
        for (java.util.Date date : daysOff) {
            if (isApproved.equals("true")) {
                jdbcTemplate.update(SqlConstants.APPROVE_DAYS_OFF, driverId, date);
            } else {
                jdbcTemplate.query(SqlConstants.DELETE_DAYS_OFF, new Object[]{driverId, date},
                        new DayOffMapper());
            }
        }
    }

}
