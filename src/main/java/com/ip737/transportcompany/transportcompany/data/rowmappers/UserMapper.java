package com.ip737.transportcompany.transportcompany.data.rowmappers;

import com.ip737.transportcompany.transportcompany.data.entities.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {

        return User.builder()
                .id(UUID.fromString(resultSet.getString("user_id")))
                .fullname(resultSet.getString("fullname"))
                .email(resultSet.getString("email"))
                .role(resultSet.getString("role"))
                .password(resultSet.getString("password"))
                .isActivated(resultSet.getBoolean("is_activated"))
                .link(resultSet.getString("link"))
                //.recoveryLink((resultSet.getString("recovery_link")))
                .build();
    }
}
