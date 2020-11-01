package com.ip737.transportcompany.transportcompany.data.dao.impl;

import com.ip737.transportcompany.transportcompany.data.dao.UserDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration
@WebAppConfiguration
class UserDaoImplTest {

    @Autowired
    private UserDao userDao;

    @Test
    void getByActivationUrl() {
    }

    @Test
    void getByRecoverUrl() {
    }

    @Test
    void getByEmail() {
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}