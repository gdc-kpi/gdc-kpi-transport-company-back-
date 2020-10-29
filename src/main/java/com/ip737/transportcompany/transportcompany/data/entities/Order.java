package com.ip737.transportcompany.transportcompany.data.entities;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
@AllArgsConstructor
@Table(name = "order")
public class Order {

    @Id
    //geography??
    private int source;
    //geography??
    private int destination;
    private float volume;
    private UUID drivers_id;
    private UUID admins_id;
    private String title;
    private String description;

    public Order() {}
}