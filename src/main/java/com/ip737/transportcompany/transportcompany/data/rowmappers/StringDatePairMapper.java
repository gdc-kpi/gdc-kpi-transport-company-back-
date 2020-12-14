package com.ip737.transportcompany.transportcompany.data.rowmappers;

import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.RowMapper;

import java.text.SimpleDateFormat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;

public class StringDatePairMapper implements RowMapper<Pair<Boolean, Date>> {

    @Override
    public Pair<Boolean, Date> mapRow(ResultSet resultSet, int i) throws SQLException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try{
            return Pair.of(resultSet.getString("is_approved").equals("t"), df.parse(resultSet.getString("date")));
        } catch (Exception e){
            return null;
        }
    }
}
