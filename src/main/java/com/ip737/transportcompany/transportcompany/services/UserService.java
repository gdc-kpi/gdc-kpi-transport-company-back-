package com.ip737.transportcompany.transportcompany.services;

import com.ip737.transportcompany.transportcompany.data.entities.User;
import com.ip737.transportcompany.transportcompany.exceptions.ValidationException;

public interface UserService {

    void save(User user);

    User getByActivationUrl(String activationUrl);

    User getByRecoverUrl(String recoverUrl);

    User getByEmail(String email);

    void update(User user);


}
