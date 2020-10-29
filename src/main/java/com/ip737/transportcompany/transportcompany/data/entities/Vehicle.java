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
@Table(name = "vehicles")
public class Vehicle {

    @Id
    private String plate;
    private double capacity;
    private double loadCapacity;
    private int fuelConsumption;
    private UUID userId;

    public Vehicle() {}
}