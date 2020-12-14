package com.ip737.transportcompany.transportcompany.web.controllers;


import com.ip737.transportcompany.transportcompany.services.RecoveryService;
import com.ip737.transportcompany.transportcompany.web.dto.EmailDto;
import com.ip737.transportcompany.transportcompany.web.dto.ForgotPasswordDto;
import com.ip737.transportcompany.transportcompany.web.validators.RecoverDtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> sendRecoveryLink(@RequestBody EmailDto email) {
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
    public ResponseEntity<?> changePassword(@RequestBody ForgotPasswordDto passwordDto) {

        RecoverDtoValidator.validate(passwordDto);
        recoveringService.changePassword(passwordDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
