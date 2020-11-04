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

    public enum Status
    {
        CONFIRMED, REJECTED, PENDING_CONFIRMATION, STARTED, FINISHED
    }

}
