package com.ip737.transportcompany.transportcompany.services.impl;

import com.ip737.transportcompany.transportcompany.configs.constants.Constants;
import com.ip737.transportcompany.transportcompany.data.dao.UserDao;
import com.ip737.transportcompany.transportcompany.data.entities.User;
import com.ip737.transportcompany.transportcompany.exceptions.IdentificationException;
import com.ip737.transportcompany.transportcompany.services.EmailService;
import com.ip737.transportcompany.transportcompany.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.util.Pair;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
@PropertySource("classpath:application.properties")
public class UserServiceImpl implements UserService {
    private final EmailService mailService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserDao userDao;

    @Value("${user.reg.template}")
    private String userRegTemplate;

    @Autowired
    public UserServiceImpl(EmailService mailService, BCryptPasswordEncoder bCryptPasswordEncoder, UserDao userDao) {
        this.mailService = mailService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDao = userDao;
    }

    @Override
    public void save(User newUser) {
        if (userDao.getByEmail(newUser.getEmail()) != null) {
            throw new IdentificationException(String.format(Constants.EMAIL_TAKEN, newUser.getEmail()));
        }
        int roleId = (newUser.getRole().equals(Constants.ROLE_ADMIN)) ? Constants.ROLE_ADMIN_ID : Constants.ROLE_DRIVER_ID;
        Random random = new Random();
        User user = User.builder()
                .password(newUser.getPassword())
                .email(newUser.getEmail())
                .role(newUser.getRole())
                .fullname(newUser.getFullname())
                .link(bCryptPasswordEncoder.encode(newUser.getFullname() + newUser.getEmail() + random.nextLong()))
                .build();

        userDao.save(user, roleId);

        try {
            mailService.sendMailMessage(mailService.createBasicRegMail(user), userRegTemplate);
        } catch (MessagingException e) {
            log.error(String.format(Constants.REG_MAIL_NOT_SENT, user.getEmail()), e);
        }
    }


    @Override
    public void activateAdmin(String activationLink, String password) {
        userDao.activateAdmin(activationLink, password);
    }

    @Override
    public User getByActivationUrl(String activationUrl) {
        return userDao.getByActivationUrl(activationUrl);
    }

    @Override
    public User getByRecoverUrl(String recoverUrl) {
        return userDao.getByRecoverUrl(recoverUrl);
    }

    @Override
    public User getByEmail(String email) {
        return userDao.getByEmail(email);
    }

    @Override
    public User getById(UUID id) {
        return userDao.getById(id);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void delete(String email, String password) {
        userDao.delete(email, password);
    }

    @Override
    public List<Pair<Date, String>> setDaysOff(UUID userId, List<Date> days) {
        List<Date> rejected = userDao.getListOfApprovedDays(userId, days);
        ArrayList<Pair<Date, String>> res = new ArrayList<Pair<Date, String>>();
        for(var date: rejected)
            res.add(Pair.of(date, "date is confirmed"));
        return res;
    }
}
