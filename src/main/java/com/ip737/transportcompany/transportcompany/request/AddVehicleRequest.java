package com.ip737.transportcompany.transportcompany.request;

import com.ip737.transportcompany.transportcompany.configs.constants.Constants;
import com.ip737.transportcompany.transportcompany.data.entities.Vehicle;

public class AddVehicleRequest {
    private String plate;
    private double capacity;
    private double loadCapacity;
    private int fuelConsumption;

    public Vehicle toVehicle(){
        return Vehicle.builder()
                .plate(this.plate)
                .capacity(this.capacity)
                .fuelConsumption(this.fuelConsumption)
                .loadCapacity(this.loadCapacity)
                .build();
    }
}
