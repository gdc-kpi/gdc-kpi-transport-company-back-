package com.ip737.transportcompany.transportcompany.web.validators;

import com.ip737.transportcompany.transportcompany.exceptions.ValidationException;
import com.ip737.transportcompany.transportcompany.web.dto.OrderDto;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderDtoValidator {

    private static final String EMPTY_PROPERTY_EXCEPTION_MESSAGE = "Field parameter '%s' must be provided";
    private static final String FLOAT_PROPERTY_EXCEPTION_MESSAGE = "Field parameter '%s' msut be greater than zero";
    private static final String DATE_PROPERTY_EXCEPTION_MESSAGE = "Date parameter '%s' must refer to time in 24 hours at least ";

    public static void validate(OrderDto orderDto) throws ValidationException {
        validateNotEmptyProperty(orderDto.getSource(), "source");

        validateFloat(orderDto.getSource().latitude, "source latitude");
        validateFloat(orderDto.getSource().longitude, "source longitude");
        validateNotEmptyProperty(orderDto.getDestination(), "destination");

        validateFloat(orderDto.getDestination().latitude, "destination latitude");
        validateFloat(orderDto.getDestination().longitude, "destination longitude" );

        validateNotEmptyProperty(orderDto.getVolume(), "volume");
        validateFloat(orderDto.getVolume(), "volume");
        validateNotEmptyProperty(orderDto.getWeight(), "weight");
        validateFloat(orderDto.getWeight(), "weight");

        validateNotEmptyProperty(orderDto.getCar_id(), "car id");
        validateNotEmptyProperty(orderDto.getDeadline(), "deadline");
        validateDeadline(LocalDateTime.parse(orderDto.getDeadline(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S")), "deadline");
    }

    private static void validateNotEmptyProperty(Object value, String propertyName) {
        if (value == null || StringUtils.isEmpty(value)) {
            throw new ValidationException(String.format(EMPTY_PROPERTY_EXCEPTION_MESSAGE, propertyName));
        }
    }

    private static void validateDeadline(LocalDateTime value, String propertyName) {
        if (value.isBefore(LocalDateTime.now().plusDays(1))) {
            throw new ValidationException(String.format(DATE_PROPERTY_EXCEPTION_MESSAGE, propertyName));
        }
    }

    private static void validateFloat(Double value, String propertyName) {
        if (value <= 0) {
            throw new ValidationException(String.format(FLOAT_PROPERTY_EXCEPTION_MESSAGE, propertyName));
        }
    }



}
