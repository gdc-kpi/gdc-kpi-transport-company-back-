package com.ip737.transportcompany.transportcompany.web.controllers;

import com.ip737.transportcompany.transportcompany.data.entities.User;
import com.ip737.transportcompany.transportcompany.request.LoginRequest;
import com.ip737.transportcompany.transportcompany.response.UserLoginSuccessResponse;
import com.ip737.transportcompany.transportcompany.services.RecoveryService;
import com.ip737.transportcompany.transportcompany.web.dto.DtoEmail;
import com.ip737.transportcompany.transportcompany.web.dto.DtoForgotPassword;
import com.ip737.transportcompany.transportcompany.web.validators.RecoverDtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
@CrossOrigin
@RequestMapping("/api/recovery")
public class RecoveryController {

    final private RecoveryService recoveringService;

    @Autowired
    public RecoveryController(RecoveryService recoveringService) {
        this.recoveringService = recoveringService;
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendRecoveryLink(@RequestBody DtoEmail email) {
        RecoverDtoValidator.validate(email.getEmail());
        recoveringService.sendRecoveryLink(email.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/confirm")
    public ResponseEntity<?> checkRecoveryLink(@PathParam("key") String key) {
        recoveringService.confirmRecovery(key);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody DtoForgotPassword passwordDto) {

        RecoverDtoValidator.validate(passwordDto);
        recoveringService.changePassword(passwordDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
