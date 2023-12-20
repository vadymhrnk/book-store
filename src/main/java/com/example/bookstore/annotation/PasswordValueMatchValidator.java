package com.example.bookstore.annotation;

import com.example.bookstore.dto.UserRegistrationRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValueMatchValidator
        implements ConstraintValidator<FieldMatch, UserRegistrationRequestDto> {
    @Override
    public boolean isValid(
            UserRegistrationRequestDto requestDto,
            ConstraintValidatorContext constraintValidatorContext
    ) {
        String passwordValue = requestDto.getPassword();
        String repeatPasswordValue = requestDto.getRepeatPassword();

        return passwordValue != null && passwordValue.equals(repeatPasswordValue);
    }
}
