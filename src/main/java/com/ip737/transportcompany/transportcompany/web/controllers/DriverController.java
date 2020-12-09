package com.ip737.transportcompany.transportcompany.web.controllers;

import com.ip737.transportcompany.transportcompany.configs.constants.Constants;
import com.ip737.transportcompany.transportcompany.configs.security.services.AuthenticationFacade;
import com.ip737.transportcompany.transportcompany.exceptions.ValidationException;
import com.ip737.transportcompany.transportcompany.services.DriverService;
import com.ip737.transportcompany.transportcompany.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.UUID;


@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/driver/")
public class DriverController {

    DriverService driverService;
    UserService userService;
    private AuthenticationFacade authenticationFacade;

    @Autowired
    public DriverController(DriverService driverService, UserService userService, AuthenticationFacade authenticationFacade) {
        this.driverService = driverService;
        this.userService = userService;
        this.authenticationFacade = authenticationFacade;
    }

    @GetMapping("/free-vehicles")
    public ResponseEntity<?> getFreeCars() {
        return new ResponseEntity<>(driverService.getFreeVehicle(), HttpStatus.OK);
    }

    @GetMapping("/{driverId}/vehicle")
    public ResponseEntity<?> getCar(@PathVariable UUID driverId) {
        return new ResponseEntity<>(driverService.getVehicle(driverId), HttpStatus.OK);
    }

    @GetMapping("/{driverId}/days-off")
    public ResponseEntity<?> getDaysOff(@PathVariable UUID driverId) {
        return new ResponseEntity<>(driverService.getDaysOff(driverId), HttpStatus.OK);
    }

    @PostMapping("/set-days-off")
    public ResponseEntity<?> setDaysOff(@RequestBody List<Date> days) {
        System.out.println(authenticationFacade.getId());
        return new ResponseEntity<>(userService.setDaysOff(authenticationFacade.getId(), days), HttpStatus.OK);
    }

    @GetMapping("/{driverId}/choose-car")
    public ResponseEntity<?> chooseCar(@PathVariable UUID driverId, String plate) throws ValidationException {
        if(userService.getById(driverId).getRole().equals("admin")) {
            throw new ValidationException(Constants.ADMIN_CHOOSE_CAR);
        } else if(driverService.getVehicle(driverId) != null) {
            throw new ValidationException(Constants.DRIVER_HAS_CAR);
        } else if (driverService.getVehicle(plate) == null) {
            throw new ValidationException(Constants.CAR_NOT_FOUND);
        } else if (driverService.getVehicle(plate).getUserId() != null) {
            throw new ValidationException(Constants.CAR_OCCUPIED);
        } else {
            driverService.chooseVehicle(driverId, plate);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/{driverId}/orders/finished")
    public ResponseEntity<?> getOrdersFin(@PathVariable UUID driverId) {
        return new ResponseEntity<>(driverService.getOrdersFilterByDriver(driverId.toString(), Constants.Status.FINISHED.toString(), Constants.Status.FAILED.toString()), HttpStatus.OK);
    }

    @GetMapping("/{driverId}/orders/upcoming")
    public ResponseEntity<?> getOrdersUpcom(@PathVariable UUID driverId) {
        return new ResponseEntity<>(driverService.getOrdersFilterByDriver(driverId.toString(), Constants.Status.CONFIRMED.toString(),  Constants.Status.STARTED.toString() ), HttpStatus.OK);
    }

    @GetMapping("/{driverId}/orders/to-confirm")
    public ResponseEntity<?> getOrdersToConfirm(@PathVariable UUID driverId) {
        return new ResponseEntity<>(driverService.getOrdersFilterByDriver(driverId.toString(), Constants.Status.PENDING_CONFIRMATION.toString()), HttpStatus.OK);
    }
}
