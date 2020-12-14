package com.ip737.transportcompany.transportcompany.web.validators;

import com.ip737.transportcompany.transportcompany.exceptions.ValidationException;
import com.ip737.transportcompany.transportcompany.web.dto.LoginDto;
import com.ip737.transportcompany.transportcompany.web.dto.SignUpDto;
import org.springframework.util.StringUtils;


public class UserValidator {
    private static final String EMPTY_PROPERTY_EXCEPTION_MESSAGE = "User field parameter '%s' must be provided";

    public static void validate(SignUpDto user) throws ValidationException {
        validateNotEmptyProperty(user.getFullname(), "fullname");
        validateNotEmptyProperty(user.getPassword(), "password");
        validateNotEmptyProperty(user.getEmail(), "email");
    }

    public static void validate(LoginDto user) throws ValidationException {
        validateNotEmptyProperty(user.getPassword(), "password");
        validateNotEmptyProperty(user.getEmail(), "email");
    }

    private static void validateNotEmptyProperty(Object value, String propertyName) throws ValidationException {
        if (value == null || StringUtils.isEmpty(value)) {
            throw new ValidationException(String.format(EMPTY_PROPERTY_EXCEPTION_MESSAGE, propertyName));
        }
    }
}
