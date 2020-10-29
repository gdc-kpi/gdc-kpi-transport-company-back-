package com.ip737.transportcompany.transportcompany.request;

import com.ip737.transportcompany.transportcompany.data.entities.Order;

import java.util.UUID;

public class InsertOrderRequest {
    //geography??
    private int source;
    //geography??
    private int destination;
    private float volume;
    //UUID?
    private UUID drivers_id;
    //UUID?
    private UUID admins_id;
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
