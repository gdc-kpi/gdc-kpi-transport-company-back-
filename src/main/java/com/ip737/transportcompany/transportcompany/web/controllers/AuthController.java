package com.ip737.transportcompany.transportcompany.web.controllers;

import com.ip737.transportcompany.transportcompany.configs.constants.Constants;
import com.ip737.transportcompany.transportcompany.data.entities.User;
import com.ip737.transportcompany.transportcompany.exceptions.IdentificationException;
import com.ip737.transportcompany.transportcompany.exceptions.ValidationException;
import com.ip737.transportcompany.transportcompany.web.dto.ChangePasswordDto;
import com.ip737.transportcompany.transportcompany.web.dto.LoginDto;
import com.ip737.transportcompany.transportcompany.web.dto.SignUpDto;
import com.ip737.transportcompany.transportcompany.response.UserLoginSuccessResponse;
import com.ip737.transportcompany.transportcompany.services.ActivationService;
import com.ip737.transportcompany.transportcompany.services.UserService;
import com.ip737.transportcompany.transportcompany.web.validators.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.UUID;


@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {

    final private UserService userService;
    final private ActivationService activationService;

    @Autowired
    public AuthController(UserService userService , ActivationService activationService) {
        this.userService = userService;
        this.activationService = activationService;
    }

    @PostMapping("/log-in")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDto user) throws ValidationException {
        UserValidator.validate(user);

        User currentUser = userService.getByEmail(user.getEmail());

        if (currentUser == null) {
            throw new ValidationException(Constants.USER_NOT_FOUND_WITH_EMAIL);
        }

        if (!currentUser.isActivated()) {
            throw new IdentificationException(Constants.NOT_ACTIVATED);
        }

        if (!user.getPassword().equals(currentUser.getPassword())) {
            throw new IdentificationException(Constants.INCORRECT_PASSWORD);
        }

        UserLoginSuccessResponse successResponse = UserLoginSuccessResponse.builder()
                .token(activationService.isUserActivated(currentUser))
                .success(true).build();

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto user) {
        UserValidator.validate(user);
        userService.save(user.toUser());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/activate")
    public ResponseEntity<?> activate(@PathParam("key") String key) {
        activationService.verifyUser(key);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //TODO won't work
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(UUID userId, @RequestBody ChangePasswordDto pass) {
        User currentUser = userService.getById(userId);

        if (userService.getById(userId).getPassword().equals(currentUser.getPassword())) {
            currentUser.setPassword(pass.getNewPassword());
            userService.update(currentUser);
        } else throw new ValidationException(Constants.INCORRECT_PASSWORD);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody LoginDto user) {
        userService.delete(user.getEmail(), user.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/get-link") // TEMPORARY
    public ResponseEntity<?> getLink(@RequestBody LoginDto user) {
        User currentUser = userService.getByEmail(user.getEmail());
        UserLoginSuccessResponse successResponse = UserLoginSuccessResponse.builder()
                .token(currentUser.getLink())
                .success(true).build();
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PostMapping("/get-recovery") // TEMPORARY
    public ResponseEntity<?> getRecovery(@RequestBody LoginDto user) {
        User currentUser = userService.getByEmail(user.getEmail());
        UserLoginSuccessResponse successResponse = UserLoginSuccessResponse.builder()
                .token(currentUser.getRecoveryLink())
                .success(true).build();
        return new ResponseEntity<>(successResponse, HttpStatus.OK);

    }
}
