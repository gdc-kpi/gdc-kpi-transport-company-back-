package com.ip737.transportcompany.transportcompany.web.controllers;

import com.ip737.transportcompany.transportcompany.data.entities.User;
import com.ip737.transportcompany.transportcompany.exceptions.ValidationException;
import com.ip737.transportcompany.transportcompany.request.LoginRequest;
import com.ip737.transportcompany.transportcompany.request.SignUpRequest;
import com.ip737.transportcompany.transportcompany.response.UserLoginSuccessResponse;
import com.ip737.transportcompany.transportcompany.services.ActivationService;
import com.ip737.transportcompany.transportcompany.services.UserService;
import com.ip737.transportcompany.transportcompany.web.validators.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.websocket.server.PathParam;


@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {

//    @Value("${activation.redirect.url}")
//    private String activationRedirectUrl;

//    private final SettingsService settingsService;

    final private UserService userService;
    final private ActivationService activationService;

    @Autowired
    public AuthController(UserService userService , ActivationService activationService/**,
                                   SettingsService settingsService**/) {
        this.userService = userService;
        this.activationService = activationService;
//        this.settingsService = settingsService;
    }

    @PostMapping("/log-in")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest user) throws ValidationException {
        UserValidator.validate(user);

        User currentUser = userService.getByEmail(user.getEmail());

        if (currentUser == null) {
            throw new ValidationException("User not found with email: " + user.getEmail());
        }

        if (!currentUser.isActivated()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (!user.getPassword().equals(currentUser.getPassword())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        UserLoginSuccessResponse successResponse = UserLoginSuccessResponse.builder()
                .token(activationService.isUserActivated(currentUser))
                .success(true).build();

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest user) {
        UserValidator.validate(user);
        userService.save(user.toUser());
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @GetMapping("/activation")
//    public RedirectView activate(@PathParam("key") String key) {
//
//        return new RedirectView(activationRedirectUrl /**+ activationService.verifyUser(key)**/);
//    }
}
