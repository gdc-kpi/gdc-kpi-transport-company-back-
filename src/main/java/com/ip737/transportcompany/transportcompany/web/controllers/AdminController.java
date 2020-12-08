package com.ip737.transportcompany.transportcompany.web.controllers;

import com.ip737.transportcompany.transportcompany.configs.constants.Constants;
import com.ip737.transportcompany.transportcompany.configs.security.services.AuthenticationFacade;
import com.ip737.transportcompany.transportcompany.exceptions.AccessDeniedException;
import com.ip737.transportcompany.transportcompany.exceptions.ValidationException;
import com.ip737.transportcompany.transportcompany.services.AdminService;
import com.ip737.transportcompany.transportcompany.web.dto.VehicleDto;
import com.ip737.transportcompany.transportcompany.web.validators.VehicleValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@CrossOrigin
@Controller
@RequestMapping("api/admin")
public class AdminController {

    @Autowired
    final private AdminService profileService;

    final private AuthenticationFacade authenticationFacade;

    public AdminController(AdminService profileService, AuthenticationFacade authenticationFacade) {
        this.profileService = profileService;
        this.authenticationFacade = authenticationFacade;

    }

    @GetMapping("/all-vehicles")
    public ResponseEntity<?> getAllCars() {
        if (!authenticationFacade.isAllowed(Constants.ROLE_ADMIN)) {
            throw new AccessDeniedException(Constants.FORBIDDEN_BY_ROLE);
        }
        return new ResponseEntity<>(profileService.getAllVehicle(), HttpStatus.OK);
    }

    @PostMapping("/add-vehicle")
    public ResponseEntity<?> addVehicle(@RequestBody VehicleDto vehicle) {
        if (!authenticationFacade.isAllowed(Constants.ROLE_ADMIN)) {
            throw new AccessDeniedException(Constants.FORBIDDEN_BY_ROLE);
        }
        VehicleValidator.validate(vehicle);

        if (profileService.getVehicle(vehicle.getPlate()) == null) {
            profileService.addVehicle(vehicle.toVehicle());
        } else {
            throw new ValidationException(Constants.EXIST_CAR_WITH_PLATE);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/vehicles")
    public ResponseEntity<?> getVehicleFilterByPartOfPlate(@RequestParam(value = "plate") String plate) {
        if (authenticationFacade.isAllowed(Constants.ROLE_ADMIN)) {
            return new ResponseEntity<>(profileService.getVehicleFilterByPartOfPlate(plate), HttpStatus.OK);
        } else {
            throw new AccessDeniedException(Constants.FORBIDDEN_BY_ROLE);
        }
    }


    @GetMapping("/drivers")
    public ResponseEntity<?> getDriversByPartOfTheFullName(@RequestParam(value = "fullname") String fullname) {
        if (authenticationFacade.isAllowed(Constants.ROLE_ADMIN)) {
            return new ResponseEntity<>(profileService.getDriversFilterByName(fullname), HttpStatus.OK);
        } else {
            throw new AccessDeniedException(Constants.FORBIDDEN_BY_ROLE);
        }
    }

    @GetMapping("/days-off")
    public ResponseEntity<?> getDaysOffToConfirm() {
        if (authenticationFacade.isAllowed(Constants.ROLE_ADMIN)) {
            return new ResponseEntity<>(profileService.getDaysOff(), HttpStatus.OK);
        } else {
            throw new AccessDeniedException(Constants.FORBIDDEN_BY_ROLE);
        }
    }


    @GetMapping("/{adminId}/orders/finished")
    public ResponseEntity<?> getOrdersFin(@PathVariable UUID adminId) {
        if (!authenticationFacade.isAllowed(Constants.ROLE_ADMIN)) {
            throw new AccessDeniedException(Constants.FORBIDDEN_BY_ROLE);
        }
        return new ResponseEntity<>(profileService.getOrdersFilterByStatus(adminId.toString(), Constants.Status.FAILED.toString(), Constants.Status.FINISHED.toString()), HttpStatus.OK);
    }

    @GetMapping("/{adminId}/orders/confirmed")
    public ResponseEntity<?> getOrdersCon(@PathVariable UUID adminId) {
        if (!authenticationFacade.isAllowed(Constants.ROLE_ADMIN)) {
            throw new AccessDeniedException(Constants.FORBIDDEN_BY_ROLE);
        }
        return new ResponseEntity<>(profileService.getOrdersFilterByStatus(adminId.toString(), Constants.Status.CONFIRMED.toString(), Constants.Status.STARTED.toString()), HttpStatus.OK);
    }


    @GetMapping("/{adminId}/orders/confirmation-pending")
    public ResponseEntity<?> getOrdersToConfirm(@PathVariable UUID adminId) {
        if (!authenticationFacade.isAllowed(Constants.ROLE_ADMIN)) {
            throw new AccessDeniedException(Constants.FORBIDDEN_BY_ROLE);
        }
        return new ResponseEntity<>(profileService.getOrdersFilterByStatus(adminId.toString(), Constants.Status.PENDING_CONFIRMATION.toString()), HttpStatus.OK);
    }

    @GetMapping("/{adminId}/orders/rejected")
    public ResponseEntity<?> getOrdersRejected(@PathVariable UUID adminId) {
        if (!authenticationFacade.isAllowed(Constants.ROLE_ADMIN)) {
            throw new AccessDeniedException(Constants.FORBIDDEN_BY_ROLE);
        }
        return new ResponseEntity<>(profileService.getOrdersFilterByStatus(adminId.toString(), Constants.Status.REJECTED.toString()), HttpStatus.OK);
    }


}

