package com.ip737.transportcompany.transportcompany.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter

public class DayOffDto {
    private UUID driverId;
    private Date[] dates;
    private String isApproved;
}
