package com.ip737.transportcompany.transportcompany.web.dto;

import com.ip737.transportcompany.transportcompany.data.entities.Order;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class OrderDto {
    //geography??
    private int source;
    //geography??
    private int destination;
    private float volume;
    //UUID?
    private String drivers_id;
    //UUID?
    private String admins_id;
    private String title;
    private String description;

    public Order toOrder(){
        return Order.builder()
                .source(this.source)
                .destination(this.destination)
                .volume(this.volume)
                .drivers_id(this.drivers_id)
                .admins_id(this.admins_id)
                .title(this.title)
                .description(this.description)
                .build();
    }
}
