package com.ip737.transportcompany.transportcompany.data.rowmappers;

import com.ip737.transportcompany.transportcompany.data.entities.Coordinates;
import com.ip737.transportcompany.transportcompany.data.entities.FirstLastPoint;
import com.ip737.transportcompany.transportcompany.data.entities.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FirstLastPointMapper implements RowMapper<FirstLastPoint> {
    private Coordinates SplitString(String s) {
        double lon = 0;
        double lat = 0;
        long latDevider = 0;
        for(int i = 1; i < s.length()-1; i++) {
            char c = s.charAt(i);
            if(c == '.') {
                latDevider = 1;
                continue;
            }
            if(c == ',') {
                lon = lat/latDevider;
                lat = 0;
                latDevider = 0;
                continue;
            }
            if(c < '0' || '9' < c) continue;
            lat = lat*10 + c - '0';
            latDevider *= 10;
        }
        lat /= latDevider;
        return new Coordinates(lon, lat);
    }

    @Override
    public FirstLastPoint mapRow(ResultSet resultSet, int j) throws SQLException {
        System.out.println(resultSet.getString("source"));

        return FirstLastPoint.builder()
                .source(SplitString(resultSet.getString("source")))
                .destination(SplitString(resultSet.getString("destination")))
                .build();
    }
}
//INSERT INTO table_name
//VALUES (value1, value2, value3, ...);
//INSERT INTO orders VALUES ('abcdefde-8b3d-443b-893a-1ce3287c8e46', 0.5, 1, 'test order', '', 'ab7568', 'b9c991b2-7a71-43a8-924e-6b3aad3bf3e3', POINT(30.443625, 50.448888), POINT(30.481769, 50.446878), 'FAILED', '2020-11-06 14:59:00');
