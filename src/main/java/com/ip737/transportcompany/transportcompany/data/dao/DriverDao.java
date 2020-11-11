package com.ip737.transportcompany.transportcompany.data.dao;


import com.ip737.transportcompany.transportcompany.configs.constants.SqlConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
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
            System.out.println(Date.from( day.atZone( ZoneId.systemDefault()).toInstant()));
            System.out.println(x);
            return x;
        } catch (EmptyResultDataAccessException | NullPointerException e ) {
            return 0;
        }
    };


}
