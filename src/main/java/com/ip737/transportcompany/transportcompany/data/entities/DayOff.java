package com.ip737.transportcompany.transportcompany.data.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
public class DayOff {
    private UUID driverId;
    private Date date;
}
