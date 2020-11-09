package com.ip737.transportcompany.transportcompany.web.validators;

import com.ip737.transportcompany.transportcompany.exceptions.ValidationException;
import com.ip737.transportcompany.transportcompany.web.dto.VehicleDto;
import org.springframework.util.StringUtils;

public class VehicleValidator {
    private static final String EMPTY_PROPERTY_EXCEPTION_MESSAGE =
            "Vehicle field parameter '%s' must be provided";

    public static void validate(VehicleDto vehicle) throws ValidationException {
        validateNotEmptyProperty(vehicle.getPlate(), "plate");
        validateNotEmptyProperty(vehicle.getCapacity(), "capacity");
        validateNotEmptyProperty(vehicle.getLoadCapacity(), "loadCapacity");
        validateNotEmptyProperty(vehicle.getFuelConsumption(), "fuelConsumption");
    }

    private static void validateNotEmptyProperty(Object value, String propertyName) throws ValidationException {
        if (value == null || StringUtils.isEmpty(value)) {
            throw new ValidationException(String.format(EMPTY_PROPERTY_EXCEPTION_MESSAGE, propertyName));
        }
    }
}
