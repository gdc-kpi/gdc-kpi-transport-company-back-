package com.ip737.transportcompany.transportcompany.web.dto;

import com.ip737.transportcompany.transportcompany.data.entities.Vehicle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleDto {
    private String plate;
    private double capacity;
    private double loadCapacity;
    private int fuelConsumption;

    public Vehicle toVehicle() {
        return Vehicle.builder()
                .plate(this.plate)
                .capacity(this.capacity)
                .fuelConsumption(this.fuelConsumption)
                .loadCapacity(this.loadCapacity)
                .build();
    }
}
