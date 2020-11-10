package com.ip737.transportcompany.transportcompany.web.controllers;

import com.ip737.transportcompany.transportcompany.configs.constants.Constants;
import com.ip737.transportcompany.transportcompany.data.entities.Vehicle;
import com.ip737.transportcompany.transportcompany.exceptions.ValidationException;
import com.ip737.transportcompany.transportcompany.services.AdminService;
import com.ip737.transportcompany.transportcompany.services.UserService;
import com.ip737.transportcompany.transportcompany.web.dto.VehicleDto;
import com.ip737.transportcompany.transportcompany.web.validators.VehicleValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@Controller
@RequestMapping("api/admin")
public class AdminController {

    @Autowired
    final private AdminService profileService;

    public AdminController(AdminService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/all-vehicles")
    public ResponseEntity<?> getAllCars() {
        return new ResponseEntity<>(profileService.getAllVehicle(), HttpStatus.OK);
    }

    @PostMapping("/add-vehicle")
    public ResponseEntity<?> addVehicle(@RequestBody VehicleDto vehicle) throws ValidationException {
        VehicleValidator.validate(vehicle);
        if (profileService.getVehicle(vehicle.getPlate()) == null) {
            profileService.addVehicle(vehicle.toVehicle());
        } else {
            throw new ValidationException(Constants.EXIST_CAR_WITH_PLATE);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

