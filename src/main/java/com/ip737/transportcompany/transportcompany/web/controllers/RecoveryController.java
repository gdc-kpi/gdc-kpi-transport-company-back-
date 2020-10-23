package com.ip737.transportcompany.transportcompany.web.controllers;

import com.ip737.transportcompany.transportcompany.services.RecoveryService;
import com.ip737.transportcompany.transportcompany.web.dto.DtoForgotPassword;
import com.ip737.transportcompany.transportcompany.web.dto.DtoMail;
import com.ip737.transportcompany.transportcompany.web.validators.RecoverDtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.websocket.server.PathParam;

@RestController
@CrossOrigin
@RequestMapping("/api/recovery")
public class RecoveryController {

    @Value("${recover.redirect.url}")
    private String recoverRedirectUrl;

    final private RecoveryService recoveringService;

    @Autowired
    public RecoveryController(RecoveryService recoveringService) {
        this.recoveringService = recoveringService;
    }

    @PostMapping("/send")
    public ResponseEntity<?> registerUser(@RequestBody DtoMail user) {
        RecoverDtoValidator.validate(user);
        recoveringService.sendRecoveryLink(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/confirm")
    public RedirectView activate(@PathParam("key") String key) {
        return new RedirectView(recoverRedirectUrl + recoveringService.confirmRecovery(key));
    }

    @PatchMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody DtoForgotPassword passwordDto) {

        RecoverDtoValidator.validate(passwordDto);
        recoveringService.changePassword(passwordDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
