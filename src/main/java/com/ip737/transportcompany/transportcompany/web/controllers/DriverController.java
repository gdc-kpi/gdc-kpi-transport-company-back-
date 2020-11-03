package com.ip737.transportcompany.transportcompany.web.controllers;

import com.ip737.transportcompany.transportcompany.services.DriverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/driver/")
public class DriverController {

    DriverService driverService;

    @Autowired
    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping("/{driverId}/vehicle")
    public ResponseEntity<?> getCar(@PathVariable UUID driverId) {
        return new ResponseEntity<>(driverService.getVehicle(driverId), HttpStatus.OK);
    }
}
