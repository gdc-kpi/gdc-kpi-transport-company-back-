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
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    private String orderId;

    //geography??
    private int source;
    //geography??
    private int destination; //TODO

    private float volume;
    private String drivers_id;
    private String admins_id;
    private String title;
    private String description;

}