package com.ip737.transportcompany.transportcompany.data.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Coordinates {
    public final double longitude;
    public final double latitude;

    @Override
    public String toString() {
        return '(' + String.valueOf(longitude) + ',' + String.valueOf(latitude)+')';
    }
    public Coordinates(String s) {
        double lon = 0;
        double lat = 0;
        long devider = 0;
        for(int i = 1; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c == '.') {
                devider = 1;
                continue;
            }
            if(c == ',') {
                lon = lat/Math.max(1, devider);
                lat = 0;
                devider = 0;
                continue;
            }
            if(c < '0' || '9' < c) continue;
            lat = lat*10 + c - '0';
            devider *= 10;
        }
        lat /= Math.max(1, devider);
        longitude = lon;
        latitude = lat;
    }
}