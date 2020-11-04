package com.ip737.transportcompany.transportcompany.data.dao;

import com.ip737.transportcompany.transportcompany.data.entities.User;

import java.util.UUID;

public interface UserDao {

    User getById(UUID id);

    User getByEmail(String email);

    void save(User user, int roleId);

    void update(User user);

    User getByActivationUrl(String activationUrl);

    User getByRecoverUrl(String recoverUrl);

    void delete(String email, String password);
}
