package com.ip737.transportcompany.transportcompany.data.dao.impl;

import com.ip737.transportcompany.transportcompany.configs.constants.Constants;
import com.ip737.transportcompany.transportcompany.data.dao.UserDao;
import com.ip737.transportcompany.transportcompany.data.entities.User;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertEquals;

@SpringBootTest
class UserDaoImplTest {

    @Autowired
    private UserDao userDao;

    @Before
    void setUp() {
        userDao.delete("notamongass@gmail.com", "password");
    }

    @After
    void tearDown() {
        userDao.delete("notamongass@gmail.com", "password");
    }

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
        User user = User.builder()
                .fullname("Some One")
                .email("notamongass@gmail.com")
                .role(Constants.ROLE_DRIVER)
                .password("password")
                .isActivated(true)
                .link("link")
                .build();
        userDao.save(user, Constants.ROLE_DRIVER_ID);
        User user2 = userDao.getByEmail(user.getEmail());
        user.setId(user2.getId());
        assertEquals(user, user2);
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}