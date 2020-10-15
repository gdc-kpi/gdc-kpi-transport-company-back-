package com.ip737.transportcompany.transportcompany.web.validators;

import com.ip737.transportcompany.transportcompany.request.SignUpRequest;
import org.springframework.util.StringUtils;

import javax.xml.bind.ValidationException;

public class UserValidator {
    private static final String EMPTY_PROPERTY_EXCEPTION_MESSAGE = "User field parameter '%s' must be provided";

    public static void validate(SignUpRequest user) throws ValidationException {
        validateNotEmptyProperty(user.getFullname(), "fullname");
        validateNotEmptyProperty(user.getPassword(), "password");
        validateNotEmptyProperty(user.getEmail(), "email");
    }

    private static void validateNotEmptyProperty(Object value, String propertyName) throws ValidationException {
        if (value == null || StringUtils.isEmpty(value)) {
            throw new ValidationException(String.format(EMPTY_PROPERTY_EXCEPTION_MESSAGE, propertyName));
        }
    }
}
