package com.ip737.transportcompany.transportcompany.services;

import com.ip737.transportcompany.transportcompany.data.entities.User;

import javax.xml.bind.ValidationException;

public interface UserService {

    User save(User user) throws ValidationException;

    User getByActivationUrl(String activationUrl);

    User getByRecoverUrl(String recoverUrl);

    User getByEmail(String email);

    void update(User user);


}
