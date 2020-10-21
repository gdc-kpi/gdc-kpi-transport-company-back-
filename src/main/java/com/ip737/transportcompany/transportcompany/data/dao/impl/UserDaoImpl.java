package com.ip737.transportcompany.transportcompany.data.dao.impl;

import com.ip737.transportcompany.transportcompany.configs.constants.SqlConstants;
import com.ip737.transportcompany.transportcompany.data.dao.UserDao;
import com.ip737.transportcompany.transportcompany.data.rowmappers.UserMapper;
import com.ip737.transportcompany.transportcompany.data.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
@Slf4j
public class UserDaoImpl implements UserDao {

    final private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getByActivationUrl(String activationUrl) {
        try {
            return jdbcTemplate.queryForObject(SqlConstants.USERS_GET_BY_ACTIVATION_URL,
                    new Object[]{activationUrl},
                    new UserMapper());
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    @Override
    public User getByRecoverUrl(String recoverUrl) {
        try {
            return jdbcTemplate.queryForObject(SqlConstants.USERS_GET_BY_RECOVERY_URL,
                    new Object[]{recoverUrl},
                    new UserMapper());
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    @Override
    public User getByEmail(String email) {
        try {
            return jdbcTemplate.queryForObject(SqlConstants.USER_GET_BY_EMAIL,
                    new Object[]{email},
                    new UserMapper());
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    @Override
    public void save(User user, int roleId) {
        jdbcTemplate.update(SqlConstants.USER_SAVE_QUERY,
                UUID.randomUUID(), user.getFullname(), user.getEmail(), roleId, user.getPassword(),
                user.isActivated(), user.getLink()
        );
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update(SqlConstants.USER_UPDATE_QUERY_BY_ID,
                user.getFullname(), user.getEmail(), user.getPassword(), user.isActivated(),
                user.getLink(), user.getRecoveryLink(), user.getId());

    }


}
