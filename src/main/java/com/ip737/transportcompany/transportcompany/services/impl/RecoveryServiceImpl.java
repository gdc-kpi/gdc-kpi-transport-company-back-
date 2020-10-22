package com.ip737.transportcompany.transportcompany.services.impl;

import com.ip737.transportcompany.transportcompany.configs.constants.Constants;
import com.ip737.transportcompany.transportcompany.data.entities.User;
import com.ip737.transportcompany.transportcompany.exceptions.ValidationException;
import com.ip737.transportcompany.transportcompany.services.RecoveryService;
import com.ip737.transportcompany.transportcompany.services.UserService;
import com.ip737.transportcompany.transportcompany.web.dto.DtoForgotPassword;
import com.ip737.transportcompany.transportcompany.web.dto.DtoMail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.mail.MessagingException;

@Slf4j
@Service
public class RecoveryServiceImpl implements RecoveryService {

    @Value("${recover.secret.key}")
    private String recoverSecret;

    @Value("${recover.template}")
    private String recoverNameTemplate;

    final private UserService userService;
    final private EmailServiceImpl mailService;
    final private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public RecoveryServiceImpl(UserService userService, EmailServiceImpl mailService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void sendRecoveryLink(DtoMail userMail) {

        User user = userService.getByEmail(userMail.getEmail());

        if (user == null) {
            throw new ValidationException(Constants.USER_NOT_FOUND_WITH_EMAIL + userMail.getEmail());
        }

        user.setRecoveryLink(passwordEncoder.encode(userMail.getEmail() + recoverSecret));
        userService.update(user);

        try {
            mailService.sendMailMessage(mailService.createRecoverMail(user), recoverNameTemplate);
        } catch (MessagingException e) {
            log.error(String.format(Constants.RECOVERY_MAIL_NOT_SENT, user.getEmail()), e);
        }

    }

    @Override
    public String confirmRecovery(String recoverUrl) {
        User user = userService.getByRecoverUrl(recoverUrl);

        if (user == null) {
            throw new ValidationException(Constants.USER_NOT_FOUND_WITH_RECOVER_URL + recoverUrl);
        }

        return recoverUrl;
    }

    @Override
    public void changePassword(@RequestBody DtoForgotPassword passwordDto) {
        User user = userService.getByRecoverUrl(passwordDto.getRecoverLink());

        if (user == null) {
            throw new ValidationException(Constants.USER_NOT_FOUND_WITH_RECOVER_URL + passwordDto.getRecoverLink());
        }

        user.setPassword(passwordEncoder.encode(passwordDto.getPassword()));
        user.setRecoveryLink(null);
        userService.update(user);
    }

}
