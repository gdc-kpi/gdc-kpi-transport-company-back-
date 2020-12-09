package com.ip737.transportcompany.transportcompany.services;

import com.ip737.transportcompany.transportcompany.data.entities.User;

import java.util.UUID;


public interface UserService {

    void save(User user);

    void saveAdmin(User newAdmin, String currentAdmin);

    User getByActivationUrl(String activationUrl);

    User getByRecoverUrl(String recoverUrl);

    User getByEmail(String email);

    User getById(UUID id);

    void update(User user);

    void delete(String email, String password);

    void activateAdmin(String activationLink, String password);
}
