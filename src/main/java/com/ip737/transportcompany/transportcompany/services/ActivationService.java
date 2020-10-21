package com.ip737.transportcompany.transportcompany.services;


import com.ip737.transportcompany.transportcompany.data.entities.User;

public interface ActivationService {
    String isUserActivated(User user);

    String verifyUser(String activationUrl);
}
