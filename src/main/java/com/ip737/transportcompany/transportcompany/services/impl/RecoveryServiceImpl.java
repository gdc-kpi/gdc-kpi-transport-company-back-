package com.ip737.transportcompany.transportcompany.services.impl;

import com.ip737.transportcompany.transportcompany.configs.constants.Constants;
import com.ip737.transportcompany.transportcompany.data.entities.User;
import com.ip737.transportcompany.transportcompany.exceptions.ValidationException;
import com.ip737.transportcompany.transportcompany.services.RecoveryService;
import com.ip737.transportcompany.transportcompany.services.UserService;
import com.ip737.transportcompany.transportcompany.web.dto.DtoForgotPassword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.mail.MessagingException;
import java.util.Random;

@Slf4j
@Service
public class RecoveryServiceImpl implements RecoveryService {

    @Value("${recover.secret.key}")
    private String recoverSecret;

    @Value("${recover.template}")
    private String recoverNameTemplate;

    final private UserService userService;
    final private EmailServiceImpl mailService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public RecoveryServiceImpl(UserService userService, EmailServiceImpl mailService,
                               BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.mailService = mailService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void sendRecoveryLink(String email) {

        User user = userService.getByEmail(email);

        if (user == null) {
            throw new ValidationException(Constants.USER_NOT_FOUND_WITH_EMAIL + email);
        }

        Random random = new Random();
        user.setRecoveryLink(bCryptPasswordEncoder.encode(user.getFullname() + user.getEmail() + random.nextLong()));
        userService.update(user);

        try {
            mailService.sendMailMessage(mailService.createRecoverMail(user), recoverNameTemplate);
        } catch (MessagingException e) {
            log.error(String.format(Constants.RECOVERY_MAIL_NOT_SENT, user.getEmail()), e);
        }

    }

    @Override
    public void confirmRecovery(String recoverUrl) {
        User user = userService.getByRecoverUrl(recoverUrl);

        if (user == null) {
            throw new ValidationException(Constants.USER_NOT_FOUND_WITH_RECOVER_URL + recoverUrl);
        }
    }

    @Override
    public void changePassword(@RequestBody DtoForgotPassword passwordDto) {
        User user = userService.getByRecoverUrl(passwordDto.getRecoveryLink());

        if (user == null) {
            throw new ValidationException(Constants.USER_NOT_FOUND_WITH_RECOVER_URL + passwordDto.getRecoveryLink());
        }

        user.setPassword(passwordDto.getPassword());
        user.setRecoveryLink(null);
        userService.update(user);
    }

}
