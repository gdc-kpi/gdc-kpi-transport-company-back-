package com.ip737.transportcompany.transportcompany.services.impl;

import com.ip737.transportcompany.transportcompany.configs.constants.Constants;
import com.ip737.transportcompany.transportcompany.configs.token.JwtTokenProvider;
import com.ip737.transportcompany.transportcompany.data.entities.User;
import com.ip737.transportcompany.transportcompany.exceptions.ValidationException;
import com.ip737.transportcompany.transportcompany.services.ActivationService;
import com.ip737.transportcompany.transportcompany.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ActivationServiceImpl implements ActivationService {

    final private JwtTokenProvider tokenProvider;

    @Autowired
    public ActivationServiceImpl(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
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

}
