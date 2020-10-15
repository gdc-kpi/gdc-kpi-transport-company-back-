package com.ip737.transportcompany.transportcompany.constants;

public class SqlConstants {
    public static final String USER_SAVE_QUERY =
            "INSERT INTO users (user_id, fullname, email, role_id, password, is_activated, link) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?) ;";

    public static final String SELECT_USER_QUERY =
            "SELECT user_id, fullname, email, role, password, is_activated, link " +
                    "FROM users INNER JOIN roles ON users.role_id = roles.role_id\n";

    public static final String USER_GET_BY_EMAIL = SELECT_USER_QUERY +
            "WHERE email = ? ;";
}
