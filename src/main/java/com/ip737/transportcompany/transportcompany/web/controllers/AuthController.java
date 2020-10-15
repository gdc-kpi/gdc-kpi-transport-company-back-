package com.ip737.transportcompany.transportcompany.web.controllers;

import com.ip737.transportcompany.transportcompany.data.entities.User;
import com.ip737.transportcompany.transportcompany.request.SignUpRequest;
import com.ip737.transportcompany.transportcompany.response.MessageResponse;
import com.ip737.transportcompany.transportcompany.services.UserService;
import com.ip737.transportcompany.transportcompany.web.validators.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.ValidationException;


@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {

//    @Value("${activation.redirect.url}")
//    private String activationRedirectUrl;

//    private final SettingsService settingsService;

    final private UserService userService;
//    final private ActivationService activationService;

    @Autowired
    public AuthController(UserService userService /**, ActivationService activationService,
                                   SettingsService settingsService**/) {
        this.userService = userService;
//        this.activationService = activationService;
//        this.settingsService = settingsService;
    }

//    @PostMapping("/log-in")
//    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
//
//        LoginRequestValidator.validate(loginRequest);
//
//        User currentUser = userService.getByEmail(loginRequest.getUsername());
//
//        if (currentUser == null) {
//            currentUser = userService.getByUsername(loginRequest.getUsername());
//        }
//
//        if (currentUser == null) {
//            throw new ValidationException(Constants.USER_NOT_FOUND_WITH_EMAIL_OR_USERNAME + loginRequest.getUsername());
//        }
//
//        if (!currentUser.isActivated()) {
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
//
//        userService.checkCorrectPassword(currentUser, loginRequest.getPassword());
//
//        UserLoginSuccessResponse successResponse = UserLoginSuccessResponse.builder()
//                .token(activationService.isUserVerified(currentUser))
//                .success(true).build();
//
//        return new ResponseEntity<>(successResponse, HttpStatus.OK);
//    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest user) throws ValidationException {
        UserValidator.validate(user);
        User saved = userService.save(user.toUser());
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

//    @GetMapping("/activation")
//    public RedirectView activate(@PathParam("key") String key) {
//
//        return new RedirectView(activationRedirectUrl + activationService.verifyUser(key));
//    }


}
