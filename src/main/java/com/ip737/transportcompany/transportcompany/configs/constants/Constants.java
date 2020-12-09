package com.ip737.transportcompany.transportcompany.configs.constants;

public class Constants {
    public static final String EMAIL_TAKEN = "User with email %s already exists!";
    public static final String USER_NOT_FOUND_WITH_EMAIL = "User with such email does not exist:";
    public static final String NOT_ACTIVATED = "This user is not activated!";
    public static final String USER_NOT_FOUND_WITH_ACTIVATION_URL = "User with such activation url not found";
    public static final String USER_NOT_FOUND_WITH_RECOVER_URL = "User not found with recovery URL: ";
    public static final String REG_MAIL_NOT_SENT = "Registration Mail not sent to user %s";
    public static final String RECOVERY_MAIL_NOT_SENT = "Recovery Mail not sent to user %s";
    public static final String INCORRECT_PASSWORD = "Wrong password!";
    public static final String EXIST_CAR_WITH_PLATE = "Car with such plate already exists!";
    public static final String CAR_NOT_FOUND = "Such car not found!";
    public static final String CAR_OCCUPIED = "This car is alredy occupied!";
    public static final String DRIVER_HAS_CAR = "You alredy chose a car!";
    public static final String ADMIN_CHOOSE_CAR = "Admin can`t choose a car!";
    public static final String CAR_NOT_FOUND_WITH_PLATE = "No car with plate %s";
    public static final String ORDER_NOT_FOUND_WITH_ID = "The order with id %s not found";
    public static final String ORDER_CANNOT_CHANGE_STATUS = "The status of order with id %s cannot be changed to %s as it's status is %s";
    public static final String ORDER_WRONG_DRIVER = "Driver with id %s is not assigned to order with id %s";
    public static final String FORBIDDEN_BY_ROLE = "Resource forbidden for this user due to their role";
    public static final String SCHEDULER_INFO = "Orders, that weren't confirmed two days before the deadline, was reassigned";

    public static final String JWT_CLAIMS_ID = "id";
    public static final String JWT_CLAIMS_FULL_NAME = "fullname";
    public static final String JWT_CLAIMS_ROLE = "role";
    public static final String JWT_CLAIMS_EMAIL = "email";

    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_DRIVER = "driver";

    public static final int ROLE_ADMIN_ID = 2;
    public static final int ROLE_DRIVER_ID = 1;

    public static final String MAIL_MODEL_LINK = "link";
    public static final String MAIL_MODEL_EMAIL = "email";
    public static final String MAIL_MODEL_CREATOR = "creator";
    public static final String MAIL_MODEL_ROLE = "role";

    public static final int MAX_DRIVER_ORDERS_FOR_DAY = 4;
    public enum Status
    {
        CONFIRMED, REJECTED, PENDING_CONFIRMATION, STARTED, FAILED, FINISHED,
    }

    public static final String SWAGGER_TITLE = "KyivTransComp Documentation";
    public static final String SWAGGER_DESCRIPTION = "KyivTransComp Documentation";

    public static final String SECUR_URLS = "/api/**";
    public static final String SECUR_DOCS_URLS = "/v2/api-docs";
    public static final String SECUR_CONFIG_UI_URLS = "/configuration/ui";
    public static final String SECUR_SWAGGER_RESOURCES_URLS = "/swagger-resources/**";
    public static final String SECUR_CONFIG_SECURITY_URLS = "/configuration/security";
    public static final String SECUR_SWAGGER_UI_URLS = "/swagger-ui.html";
    public static final String SECUR_WEBJARS_URLS = "/webjars/**";
}
