package com.ip737.transportcompany.transportcompany.services;

import com.ip737.transportcompany.transportcompany.data.entities.User;
import org.springframework.data.util.Pair;

import java.sql.Date;
import java.util.List;
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

<<<<<<< HEAD
    List<Pair<Date, String>> setDaysOff(UUID userId, List<Date> days);
=======
    void activateAdmin(String activationLink, String password);
>>>>>>> 0231b54cf1dd7a5bb4ad6a707714d0b46bae92fe
}
