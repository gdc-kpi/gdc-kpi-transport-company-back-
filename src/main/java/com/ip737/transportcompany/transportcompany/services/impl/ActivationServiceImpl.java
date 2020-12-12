package com.ip737.transportcompany.transportcompany.services.impl;

import com.ip737.transportcompany.transportcompany.configs.constants.Constants;
import com.ip737.transportcompany.transportcompany.configs.token.JwtTokenProvider;
import com.ip737.transportcompany.transportcompany.data.entities.User;
import com.ip737.transportcompany.transportcompany.exceptions.ValidationException;
import com.ip737.transportcompany.transportcompany.services.ActivationService;
import com.ip737.transportcompany.transportcompany.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivationServiceImpl implements ActivationService {
    private static final String MESSAGE_ALREADY_ACTIVATED = "Already activated. Please, log in";
    private static final String MESSAGE_ACTIVATED = "Successfully activated. Please, log in";

    final private JwtTokenProvider tokenProvider;
    final private UserService userService;

    @Autowired
    public ActivationServiceImpl(JwtTokenProvider tokenProvider, UserService userService) {
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    @Override
    public String isUserActivated(User user) {
        if (user == null) {
            throw new ValidationException(Constants.USER_NOT_FOUND_WITH_EMAIL);
        }
        if (!user.isActivated()) {
            throw new ValidationException(Constants.NOT_ACTIVATED);
        }
        return tokenProvider.provideToken(user.getEmail());
    }

    @Override
    public String verifyUser(String activationUrl) {
        User user = userService.getByActivationUrl(activationUrl);

        if (user == null) {
            throw new ValidationException(Constants.USER_NOT_FOUND_WITH_ACTIVATION_URL);
        }

        if (user.isActivated()) {
            throw new ValidationException(MESSAGE_ALREADY_ACTIVATED);
        }

        user.setActivated(true);
        userService.update(user);

        return MESSAGE_ACTIVATED;
    }
}
