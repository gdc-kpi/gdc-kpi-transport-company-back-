package com.ip737.transportcompany.transportcompany.web.dto;

import com.ip737.transportcompany.transportcompany.data.entities.Coordinates;
import com.ip737.transportcompany.transportcompany.data.entities.Order;
import lombok.*;

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
    private String drivers_id;
    private String admins_id;
    private String title;
    private String description;

    public Order toOrder(){
        return Order.builder()
                .sourceLatitude(this.source.latitude)
                .sourceLongitude(this.source.longitude)
                .destinationLatitude(this.destination.latitude)
                .destinationLongitude(this.destination.longitude)
                .volume(this.volume)
                .drivers_id(this.drivers_id)
                .admins_id(this.admins_id)
                .title(this.title)
                .description(this.description)
                .build();
    }
}
