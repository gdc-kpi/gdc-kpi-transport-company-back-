package com.ip737.transportcompany.transportcompany.web.controllers;

import com.ip737.transportcompany.transportcompany.configs.constants.Constants;
import com.ip737.transportcompany.transportcompany.data.entities.User;
import com.ip737.transportcompany.transportcompany.exceptions.ValidationException;
import com.ip737.transportcompany.transportcompany.request.AddVehicleRequest;
import com.ip737.transportcompany.transportcompany.request.ChangePasswordRequest;
import com.ip737.transportcompany.transportcompany.services.ProfileService;
import com.ip737.transportcompany.transportcompany.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/profile")
public class ProfileController {

    final private ProfileService profileService;
    final private UserService userService;

    public ProfileController(ProfileService profileService, UserService userService) {
        this.profileService = profileService;
        this.userService = userService;
    }

    @PostMapping("/add-vehicle")
    public ResponseEntity<?> addVehicle(@RequestBody AddVehicleRequest vehicle) {
        profileService.addVehicle(vehicle.toVehicle());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(UUID userId, @RequestBody ChangePasswordRequest pass) {
        User currentUser = userService.getById(userId);

        if (userService.getById(userId).getPassword().equals(currentUser.getPassword())) {
            currentUser.setPassword(pass.getNewPassword());
            userService.update(currentUser);
        } else throw new ValidationException(Constants.INCORRECT_PASSWORD);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> profile() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

