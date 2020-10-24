package com.ip737.transportcompany.transportcompany.web.validators;

import com.ip737.transportcompany.transportcompany.exceptions.ValidationException;
import com.ip737.transportcompany.transportcompany.web.dto.DtoForgotPassword;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecoverDtoValidator {

    private static final String REGEX_EXCEPTION_MESSAGE = "Field parameter '%s' must match these parameters: '%s'";

    private static final String REGEX_EMAIL = "^[a-zA-Z0-9_+&*-]+(?:\\." +
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";

    private static final String REGEX_PASSWORD = "^(?=.*\\d).{4,28}$";

    private static final String EMPTY_PROPERTY_EXCEPTION_MESSAGE = "Field parameter '%s' must be provided";

    public static void validate(DtoForgotPassword dtoForgotPassword) throws ValidationException {
        validateNotEmptyProperty(dtoForgotPassword.getPassword(), "password");
        validateWithRegularExpression(dtoForgotPassword.getPassword(), REGEX_PASSWORD, "password");
    }

    public static void validate(String email) throws ValidationException {
        validateNotEmptyProperty(email, "email");
        validateWithRegularExpression(email, REGEX_EMAIL,"email");
    }

    private static void validateNotEmptyProperty(Object value, String propertyName) {
        if (value == null || StringUtils.isEmpty(value)) {
            throw new ValidationException(String.format(EMPTY_PROPERTY_EXCEPTION_MESSAGE, propertyName));
        }
    }

    private static void validateWithRegularExpression(Object value, String regex, String propertyName) {
        Matcher matcher = Pattern.compile(regex).matcher(String.valueOf(value));
        if (!matcher.matches()) {
            throw new ValidationException(String.format(REGEX_EXCEPTION_MESSAGE, propertyName, regex));
        }

    }
}
