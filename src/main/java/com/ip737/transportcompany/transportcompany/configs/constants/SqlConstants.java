package com.ip737.transportcompany.transportcompany.configs.constants;

public class SqlConstants {
    public static final String GET_DAYS_OFF =
            "SELECT date, is_approved from days_off WHERE user_id = ? ;";

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

    public static final String ADMIN_UPDATE_BY_ACTIVATE_LINK =
            "UPDATE users SET password= ? " +
                    "WHERE link = ? ;";

    public static final String VEHICLE_SAVE_QUERY =
            "INSERT INTO vehicles (plate, capacity, load_capacity, fuel_consumption, user_id) " +
                    "VALUES (?, ?, ?, ?, ?) ;";

    public static final String VEHICLE_ADD_OWNER =
            "UPDATE vehicles SET user_id = ? " + "WHERE plate = ? ;";

    public static final String VEHICLE_GET_BY_USER_ID =
            "SELECT plate, capacity, load_capacity, fuel_consumption, user_id " +
            "FROM vehicles WHERE user_id = ? ;";

    public static final String VEHICLE_GET_BY_PLATE =
            "SELECT plate, capacity, load_capacity, fuel_consumption, user_id " +
            "FROM vehicles WHERE plate = ? ;";

    public static final String VEHICLE_GET_ALL =
            "SELECT users.fullname, plate, capacity, load_capacity, fuel_consumption, vehicles.user_id " +
            "FROM vehicles LEFT JOIN users ON vehicles.user_id = users.user_id";

    public static final String VEHICLE_GET_FILTERED =
            "SELECT users.fullname, plate, capacity, load_capacity, fuel_consumption, vehicles.user_id " +
                    "FROM vehicles LEFT JOIN users ON vehicles.user_id = users.user_id where vehicles.plate like ?";


    public static final String VEHICLE_GET_FREE =
            "SELECT plate, capacity, load_capacity, fuel_consumption, user_id " +
            "FROM vehicles WHERE user_id is null;";


    public static final String DRIVER_GET_FILTERED =
            "select users.user_id as user_id, fullname, vehicles.plate as plate from users left join vehicles on users.user_id = vehicles.user_id where role_id = 1 and is_activated = true and lower(fullname) like lower(?) ;";

    public static final String USER_INSERT_ODER =
            "INSERT INTO orders (order_id, source, destination, volume, car_id, " +
                    "admin_id, title, description, weight, deadline, status) " +
                    "VALUES (?, point(?::double precision, ?::double precision), " +
                    "point(?::double precision, ?::double precision), ?, ?, ?, ?, ?, ?, ?, ?) ;";

    public static final String GET_ORDER_BY_ID =
            "select order_id, drivers.fullname as driver_name, drivers.user_id as driver, vehicles.plate, admins.fullname as admin_name,  volume, weight, title, description, car_id, admin_id, source[0] as s1, source[1] as s2, destination[0] as d1, destination[1] as d2 , status, deadline from orders\n" +
                    "left  join users as admins\n" +
                    "    on orders.admin_id = admins.user_id\n" +
                    "  left join vehicles\n" +
                    "on      orders.car_id = vehicles.plate\n" +
                    "left join  users as drivers on\n" +
                    "        drivers.user_id = vehicles.user_id\n" +
                    "where order_id = uuid(?) ";

    public static final String ORDER_ASSIGN_DRIVER =
            "UPDATE orders set car_id = ? where order_id = uuid(?)";

    public static final String ORDER_CHANGE_STATUS =
            "UPDATE orders SET status = ? WHERE order_id = uuid(?)";

    public static final String DELETE_USER_QUERY =
            "DELETE FROM users WHERE email = ? AND password = ?;";

    public static final String GET_DRIVERS_DAY_OFF_FOR_THE_DATE =
    "select count(1) from days_off where user_id = ? and date = ? and is_approved = true;";


    public static final String GET_DRIVERS_ORDERS_FOR_THE_DAY =
            "select count(1) from orders where car_id = ? and deadline::date = ? and status = 'CONFIRMED' ;";

    public static final String GET_DRIVERS_LIST =
            "select user_id, fullname, plate from\n" +
            "(\n" +
            "    select users.user_id as user_id, users.fullname as fullname, vehicles.plate as plate, days_off.is_approved, count(order_id) as number_of_orders_per_vehicle from users\n" +
            "    inner join vehicles on users.user_id = vehicles.user_id\n" +
            "    left join days_off on users.user_id = days_off.user_id and days_off.date >= ? and days_off.date < ?\n" +
            "    left join orders on vehicles.plate = orders.car_id and orders.deadline >= ? and orders.deadline < ?\n" +
            "    where vehicles.load_capacity >= ? and vehicles.capacity >= ? and (not days_off.is_approved or days_off.is_approved is null)\n" +
            "    group by users.user_id, users.fullname, vehicles.plate, days_off.is_approved\n" +
            ") as drivers\n" +
            "where number_of_orders_per_vehicle <= 3;";

    public static final String GET_ORDERS_BY_STATUS_FOR_DRIVER =
            "select order_id, drivers.fullname as driver_name, drivers.user_id as driver, vehicles.plate, admins.fullname as admin_name,  volume, weight, title, description, car_id, admin_id, source[0] as s1, source[1] as s2, destination[0] as d1, destination[1] as d2 , status, deadline from orders\n" +
                    "left  join users as admins\n" +
                    "    on orders.admin_id = admins.user_id\n" +
                    "  left join vehicles\n" +
                    "on      orders.car_id = vehicles.plate\n" +
                    "left join  users as drivers on\n" +
                    "        drivers.user_id = vehicles.user_id\n" +
                    "where drivers.user_id = uuid(?) and orders.status = ? ";


    public static final String GET_ORDERS_BY_STATUS_FOR_ADMIN =
            "select order_id, drivers.fullname as driver_name, drivers.user_id as driver, vehicles.plate, admins.fullname as admin_name,  volume, weight, title, description, car_id, admin_id, source[0] as s1, source[1] as s2, destination[0] as d1, destination[1] as d2 , status, deadline from orders\n" +
                    "left  join users as admins\n" +
                    "    on orders.admin_id = admins.user_id\n" +
                    "  left join vehicles\n" +
                    "on      orders.car_id = vehicles.plate\n" +
                    "left join  users as drivers on\n" +
                    "        drivers.user_id = vehicles.user_id\n" +
                    "where admins.user_id = uuid(?) and orders.status = ? ";



    public static final String GET_ORDERS_BY_STATUS_FOR_DRIVER2 =
            "select order_id, drivers.fullname as driver_name, drivers.user_id as driver, vehicles.plate, admins.fullname as admin_name,  volume, weight, title, description, car_id, admin_id, source[0] as s1, source[1] as s2, destination[0] as d1, destination[1] as d2 , status, deadline from orders\n" +
                    "left  join users as admins\n" +
                    "    on orders.admin_id = admins.user_id\n" +
                    "  left join vehicles\n" +
                    "on      orders.car_id = vehicles.plate\n" +
                    "left join  users as drivers on\n" +
                    "        drivers.user_id = vehicles.user_id\n" +
                    "where drivers.user_id = uuid(?) and ( orders.status = ? or orders.status = ?) order by status desc";


    public static final String GET_ORDERS_BY_STATUS_FOR_ADMIN2 =
            "select order_id, drivers.fullname as driver_name, drivers.user_id as driver, vehicles.plate, admins.fullname as admin_name,  volume, weight, title, description, car_id, admin_id, source[0] as s1, source[1] as s2, destination[0] as d1, destination[1] as d2 , status, deadline from orders\n" +
                    "left  join users as admins\n" +
                    "    on orders.admin_id = admins.user_id\n" +
                    "  left join vehicles\n" +
                    "on      orders.car_id = vehicles.plate\n" +
                    "left join  users as drivers on\n" +
                    "        drivers.user_id = vehicles.user_id\n" +
                    "where admins.user_id = uuid(?)  and ( orders.status = ? or orders.status = ?) order by status asc";

    public static final String GET_SOURCE_AND_DESTINATION_FOR_ORDER =
            "SELECT source, destination FROM orders WHERE order_id = ? ;";

    public static final String GET_PATH_FOR_ID =
            "SELECT path FROM paths WHERE order_id = ? ;";

    public static final String INSERT_PATH_FOR_ID =
            "INSERT INTO paths (order_id, path) VALUES( ? , PATH' ? ')";

    public static final String INSERT_PATH_FOR_ID_HACK =
            "INSERT INTO paths (order_id, path) VALUES( ? , PATH' ";

    public static final String IS_DATE_BUSY =
            "select orders.deadline, orders.car_id, vehicles.user_id from orders JOIN vehicles on orders.car_id=vehicles.plate WHERE user_id= ? AND DATE(deadline)= ? ;";

    public static final String SET_BUSY_DATE =
            "INSERT INTO days_off VALUES( ? , ? , False)";

    public static final String GET_DRIVERS_DAYS_OFF_FOR_ADMIN_APPROVES =
            "SELECT users.fullname, user_id, date FROM days_off LEFT JOIN users ON days_off.user_id = users.user_id WHERE is_approved = False";

    public static final String GET_UNCONFIRMED_ORDER_BY_DEADLINE =
            "SELECT order_id, drivers.fullname AS driver_name, drivers.user_id AS driver, vehicles.plate, admins.fullname AS admin_name,  " +
                    "volume, weight, title, description, car_id, admin_id, source[0] AS s1, source[1] AS s2, destination[0] AS d1, destination[1] AS d2, " +
                    "status, deadline FROM orders " +
                    "LEFT JOIN users AS admins ON orders.admin_id = admins.user_id " +
                    "LEFT JOIN vehicles ON orders.car_id = vehicles.plate " +
                    "LEFT JOIN users as drivers ON drivers.user_id = vehicles.user_id " +
                    "WHERE to_char(deadline, 'yyyy-MM-dd') = ? AND status = ? ;";


    public static final String APPROVE_DAYS_OFF =
            "update days_off set is_approved = true where user_id = ? AND date = ?";

    public static final String DELETE_DAYS_OFF =
            "delete from days_off where where user_id = ? AND date = ?";
}
