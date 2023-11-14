package ru.clevertec.bank.validator.impl;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import ru.clevertec.bank.dto.UserDto;
import ru.clevertec.bank.validator.UserDtoValidator;

public class UserDtoValidatorImpl implements UserDtoValidator {

    private final Validator validator;

    public UserDtoValidatorImpl() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public void validateUserDto(UserDto userDto) throws IllegalArgumentException {
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException("Invalid userDto: " + violations);
        }
    }

}
