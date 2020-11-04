package com.ip737.transportcompany.transportcompany.web.dto;

import com.ip737.transportcompany.transportcompany.configs.constants.Constants;
import com.ip737.transportcompany.transportcompany.data.entities.Coordinates;
import com.ip737.transportcompany.transportcompany.data.entities.Order;
import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class OrderDto {
    private Coordinates source;
    private Coordinates destination;
    private double volume;
    private double weight;
    private String car_id;
    private String description;
    private String deadline;
    public String admins_id;
    public String status;

    public Order toOrder() {
        return Order.builder()
                .sourceLatitude(this.source.latitude)
                .sourceLongitude(this.source.longitude)
                .destinationLatitude(this.destination.latitude)
                .destinationLongitude(this.destination.longitude)
                .volume(this.volume)
                .weight(this.weight)
                .car_id(this.car_id)
                .admins_id(this.admins_id)
                .deadline(LocalDateTime.parse(this.deadline, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S")))
                .description(this.description)
                .status(Constants.Status.PENDING_CONFIRMATION.toString())
                .build();
    }
}
