package com.ip737.transportcompany.transportcompany.data.rowmappers;

import com.ip737.transportcompany.transportcompany.data.entities.Coordinates;
import com.ip737.transportcompany.transportcompany.data.entities.FirstLastPoint;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class CoordinateListMapper  implements RowMapper<LinkedList<Coordinates>> {
    @Override
    public LinkedList<Coordinates> mapRow(ResultSet resultSet, int j) throws SQLException {
        LinkedList<Coordinates> arr = new LinkedList<Coordinates>();
        var strArr = resultSet.getString("path");
        for(int i = 1; i < strArr.length(); i++) {
            if(strArr.charAt(i) == '(') {
                int next = strArr.indexOf(')', i);
                arr.add(new Coordinates(strArr.substring(i, next)));
                i = next;
            }
        }
        return arr;
    }
}
