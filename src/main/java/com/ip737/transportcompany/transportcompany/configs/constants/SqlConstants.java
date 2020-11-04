package com.ip737.transportcompany.transportcompany.configs.constants;

public class SqlConstants {
    public static final String USER_SAVE_QUERY =
            "INSERT INTO users (user_id, fullname, email, role_id, password, is_activated, link, recovery_link) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?) ;";

    public static final String SELECT_USER_QUERY =
            "SELECT user_id, fullname, email, role, password, is_activated, link, recovery_link " +
                    "FROM users INNER JOIN roles ON users.role_id = roles.role_id\n";

    public static final String USER_GET_BY_EMAIL = SELECT_USER_QUERY +
            "WHERE email = ? ;";

    public static final String USER_GET_BY_ID = SELECT_USER_QUERY +
            "WHERE user_id = ? ;";

    public static final String USERS_GET_BY_ACTIVATION_URL = SELECT_USER_QUERY +
            "WHERE link = ? ;";

    public static final String USERS_GET_BY_RECOVERY_URL = SELECT_USER_QUERY +
            "WHERE recovery_link = ?";

    public static final String USER_UPDATE_QUERY_BY_ID =
            "UPDATE users SET fullname = ?, email = ?, password= ?, is_activated = ?, link = ?, recovery_link = ? " +
            "WHERE user_id = UUID(?) ;";

    public static final String VEHICLE_SAVE_QUERY =
            "INSERT INTO vehicles (plate, capacity, load_capacity, fuel_consumption, user_id) " +
                    "VALUES (?, ?, ?, ?, ?) ;";

    public static final String VEHICLE_GET_BY_ID = "SELECT plate, capacity, load_capacity, fuel_consumption, user_id " +
            "FROM vehicles WHERE user_id = ? ;";

    public static final String USER_INSERT_ODER =
            "INSERT INTO orders (order_id, source, destination, volume, car_id, admin_id, title, description) " +
                    "VALUES ((?), ?, ?, ?, (?), (?), ?, ?) ;";

    public static final String DELETE_USER_QUERY =
            "DELETE FROM users WHERE email = ? AND password = ?;";

    public static final String DELETE_VEHICLE_QUERY =
            "DELETE FROM vehicles WHERE plate = ? AND user_id = ?;";
}
