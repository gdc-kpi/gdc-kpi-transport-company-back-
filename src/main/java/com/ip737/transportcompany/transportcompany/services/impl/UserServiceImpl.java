package com.ip737.transportcompany.transportcompany.services.impl;

import com.ip737.transportcompany.transportcompany.constants.Constants;
import com.ip737.transportcompany.transportcompany.data.dao.UserDao;
import com.ip737.transportcompany.transportcompany.data.entities.User;
import com.ip737.transportcompany.transportcompany.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.Date;
import java.util.Random;

@Slf4j
@Service
@PropertySource("classpath:application.properties")
public class UserServiceImpl implements UserService {
    //private final EmailService mailService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserDao userDao;
    /**EmailService mailService,**/
    @Autowired
    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, UserDao userDao) {
        //this.mailService = mailService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDao = userDao;
    }

    @Override
    public User save(User newUser) throws ValidationException {
        if (userDao.getByEmail(newUser.getEmail()) != null) {
            throw new ValidationException(Constants.EMAIl_TAKEN + newUser.getEmail());
        }
        int roleId = (newUser.getRole() == Constants.ROLE_ADMIN) ? Constants.ROLE_ADMIN_ID : Constants.ROLE_DRIVER_ID;
        Random random = new Random();
        User user = User.builder()
                .password(newUser.getPassword())
                .email(newUser.getEmail())
                .role(newUser.getRole())
                .fullname(newUser.getFullname())
                .link(bCryptPasswordEncoder.encode(newUser.getFullname() + newUser.getEmail() + random))
                .build();

        User savedUser = userDao.save(user, roleId);

//        try {
//            mailService.sendMailMessage(mailService.createBasicRegMail(savedUser), userRegTemplate);
//        } catch (MessagingException e) {
//            log.error(String.format(Constants.REG_MAIL_NOT_SENT, user.getUsername()), e);
//        }

        return savedUser;
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
    public void update(User user) {
        userDao.update(user);
    }

}
