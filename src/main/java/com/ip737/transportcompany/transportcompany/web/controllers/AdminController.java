package com.ip737.transportcompany.transportcompany.web.controllers;

import com.ip737.transportcompany.transportcompany.web.dto.VehicleDto;
import com.ip737.transportcompany.transportcompany.services.AdminService;
import com.ip737.transportcompany.transportcompany.services.UserService;
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

    @Autowired
    final private UserService userService;

    public AdminController(AdminService profileService, UserService userService) {
        this.profileService = profileService;
        this.userService = userService;
    }

    @PostMapping("/add-vehicle")
    public ResponseEntity<?> addVehicle(@RequestBody VehicleDto vehicle) {
        profileService.addVehicle(vehicle.toVehicle());
        return new ResponseEntity<>(HttpStatus.OK);
    }


}

