package com.ip737.transportcompany.transportcompany.services;

import com.ip737.transportcompany.transportcompany.data.entities.User;
import org.springframework.data.util.Pair;

import java.sql.Date;
import java.util.List;
import java.util.UUID;


public interface UserService {

    void activateAdmin(String activationLink, String password);

    void save(User user);

    void saveAdmin(User newAdmin, String currentAdmin);

    User getByActivationUrl(String activationUrl);

    User getByRecoverUrl(String recoverUrl);

    User getByEmail(String email);

    User getById(UUID id);

    void update(User user);

    void delete(String email, String password);

    List<Pair<Date, String>> setDaysOff(UUID userId, List<Date> days);

    void activateAdmin(String activationLink, String password);
}
