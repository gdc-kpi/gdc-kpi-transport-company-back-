package com.ip737.transportcompany.transportcompany.data.dao;

import com.ip737.transportcompany.transportcompany.configs.constants.Constants;
import com.ip737.transportcompany.transportcompany.data.entities.User;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@SpringBootTest
class UserDaoImplTest {

    @Autowired
    private UserDao userDao;

    @Before
    public void setUp() {
        userDao.delete("notamongass@gmail.com", "password");
    }

    @After
    public void tearDown() {
        userDao.delete("notamongass@gmail.com", "password");
    }

    @Test
    void getByActivationUrl() {
        userDao.delete("notamongass@gmail.com", "password");
        User user = User.builder()
                .fullname("Some One")
                .email("notamongass@gmail.com")
                .role(Constants.ROLE_DRIVER)
                .password("password")
                .isActivated(true)
                .link("link")
                .recoveryLink("recovery_link")
                .build();
        userDao.save(user, Constants.ROLE_DRIVER_ID);
        User user2 = userDao.getByActivationUrl(user.getLink());
        user.setId(user2.getId());
        assertEquals(user, user2);
        userDao.delete("notamongass@gmail.com", "password");
    }

    @Test
    void getByRecoverUrl() {
        userDao.delete("notamongass@gmail.com", "password");
        User user = User.builder()
                .fullname("Some One")
                .email("notamongass@gmail.com")
                .role(Constants.ROLE_DRIVER)
                .password("password")
                .isActivated(true)
                .link("link")
                .recoveryLink("recovery_linkkk")
                .build();
        userDao.save(user, Constants.ROLE_DRIVER_ID);
        User user2 = userDao.getByRecoverUrl((user.getRecoveryLink()));
        user.setId(user2.getId());
        assertEquals(user, user2);
        userDao.delete("notamongass@gmail.com", "password");
    }

    @Test
    void getByEmail() {
        userDao.delete("notamongass@gmail.com", "password");
        User user = User.builder()
                .fullname("Some One")
                .email("notamongass@gmail.com")
                .role(Constants.ROLE_DRIVER)
                .password("password")
                .isActivated(true)
                .link("link")
                .recoveryLink("recovery_link")
                .build();
        userDao.save(user, Constants.ROLE_DRIVER_ID);
        User user2 = userDao.getByEmail((user.getEmail()));
        user.setId(user2.getId());
        assertEquals(user, user2);
        userDao.delete("notamongass@gmail.com", "password");
    }

    @Test
    void getById() {
        User user = User.builder()
                .id(UUID.fromString("fb2eb387-6390-4c11-9371-d84ae4f46c00"))
                .fullname("awesome.persona")
                .email("awesome.persona@glenwoodave.com")
                .role(Constants.ROLE_DRIVER)
                .password("0e2efe4205e3b46b7596156be6d7b11e8bb48220")
                .isActivated(true)
                .link("$2a$10$EohoEylJOCUj/lB.O5Es9ukD9YNZfPMTwySSy9At1zA0VTKN78WSC")
                .build();
        User user2 = userDao.getById((user.getId()));
        assertEquals(user, user2);
    }

    @Test
    void save() {
        userDao.delete("notamongass@gmail.com", "password");
        User user = User.builder()
                .fullname("Some One")
                .email("notamongass@gmail.com")
                .role(Constants.ROLE_DRIVER)
                .password("password")
                .isActivated(true)
                .link("link")
                .recoveryLink("recovery")
                .build();
        userDao.save(user, Constants.ROLE_DRIVER_ID);
        User user2 = userDao.getByEmail(user.getEmail());
        user.setId(user2.getId());
        assertEquals(user, user2);
        userDao.delete("notamongass@gmail.com", "password");
    }

    @Test
    void update() {
        User user = User.builder()
                .id(UUID.fromString("63122894-72a6-4dce-8a40-575f99af5801"))
                .fullname("Mr NotBro")
                .email("pozhyloi@gmail.com")
                .role(Constants.ROLE_DRIVER)
                .password("vseshchelublusemki")
                .isActivated(true)
                .recoveryLink("recovery_link")
                .build();
        userDao.update(user);
        User user2 = userDao.getByEmail(user.getEmail());
        assertEquals(user, user2);
    }

    @Test
    void delete() {
        userDao.delete("notamongass@gmail.com", "password");
        User user = User.builder()
                .fullname("Some One")
                .email("notamongass@gmail.com")
                .role(Constants.ROLE_DRIVER)
                .password("password")
                .isActivated(true)
                .link("link")
                .recoveryLink("recovery")
                .build();
        userDao.save(user, Constants.ROLE_DRIVER_ID);
        User user2 = userDao.getByEmail(user.getEmail());
        user.setId(user2.getId());
        assertEquals(user, user2);
        userDao.delete("notamongass@gmail.com", "password");
        User user3 = userDao.getByEmail(user.getEmail());
        assertNull(user3);
    }
}