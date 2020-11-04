package com.ip737.transportcompany.transportcompany.data.dao.impl;

import com.ip737.transportcompany.transportcompany.data.dao.UserDao;
import com.ip737.transportcompany.transportcompany.data.dao.VehicleDao;
import com.ip737.transportcompany.transportcompany.data.entities.Vehicle;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;

import java.util.UUID;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VehicleDaoImplTest {

    @Autowired
    private VehicleDao vehicleDao;

    @Test
    void getByOwnerId() {
        vehicleDao.delete("аа1243аа", UUID.fromString("63122894-72a6-4dce-8a40-575f99af5801"));
        Vehicle vehicle = Vehicle.builder()
                .plate("аа1243аа")
                .capacity(5.5)
                .loadCapacity(5.8)
                .fuelConsumption(7)
                .userId(UUID.fromString("63122894-72a6-4dce-8a40-575f99af5801"))
                .build();
        vehicleDao.save(vehicle);
        Vehicle vehicle2 = vehicleDao.getByOwnerId(vehicle.getUserId());
        assertEquals(vehicle, vehicle2);
        vehicleDao.delete("аа1243аа", UUID.fromString("63122894-72a6-4dce-8a40-575f99af5801"));
    }

    @Test
    void samePlates() {
        vehicleDao.delete("plate", UUID.fromString("63122894-72a6-4dce-8a40-575f99af5801"));
        vehicleDao.delete("plate", UUID.fromString("33ddbf1d-f2a4-4c03-af7f-aae1cfbc99c6"));
        Vehicle vehicle = Vehicle.builder()
                .plate("plate")
                .capacity(5.2)
                .loadCapacity(5.55)
                .fuelConsumption(8)
                .userId(UUID.fromString("63122894-72a6-4dce-8a40-575f99af5801"))
                .build();
        vehicleDao.save(vehicle);
        Vehicle vehicle2 = Vehicle.builder()
                .plate("plate")
                .capacity(6.1)
                .loadCapacity(6.5)
                .fuelConsumption(6)
                .userId(UUID.fromString("33ddbf1d-f2a4-4c03-af7f-aae1cfbc99c6"))
                .build();
        assertThrows(DuplicateKeyException.class, () -> vehicleDao.save(vehicle2));
        vehicleDao.delete("plate", UUID.fromString("63122894-72a6-4dce-8a40-575f99af5801"));
        vehicleDao.delete("plate", UUID.fromString("33ddbf1d-f2a4-4c03-af7f-aae1cfbc99c6"));
    }

    @Test
    void save() {
        vehicleDao.delete("аа1243аа", UUID.fromString("63122894-72a6-4dce-8a40-575f99af5801"));
        Vehicle vehicle = Vehicle.builder()
                .plate("аа1243аа")
                .userId(UUID.fromString("63122894-72a6-4dce-8a40-575f99af5801"))
                .build();
        vehicleDao.save(vehicle);
        Vehicle vehicle2 = vehicleDao.getByOwnerId(vehicle.getUserId());
        assertEquals(vehicle, vehicle2);
        vehicleDao.delete("аа1243аа", UUID.fromString("63122894-72a6-4dce-8a40-575f99af5801"));
    }

    @Test
    void delete() {
        vehicleDao.delete("аа1243аа", UUID.fromString("63122894-72a6-4dce-8a40-575f99af5801"));
        Vehicle vehicle = Vehicle.builder()
                .plate("аа1243аа")
                .capacity(5.5)
                .loadCapacity(5.8)
                .fuelConsumption(7)
                .userId(UUID.fromString("63122894-72a6-4dce-8a40-575f99af5801"))
                .build();
        vehicleDao.save(vehicle);
        Vehicle vehicle2 = vehicleDao.getByOwnerId(vehicle.getUserId());
        assertEquals(vehicle, vehicle2);
        vehicleDao.delete("аа1243аа", UUID.fromString("63122894-72a6-4dce-8a40-575f99af5801"));
        Vehicle vehicle3 = vehicleDao.getByOwnerId(vehicle.getUserId());
        assertNull(vehicle3);
    }
}