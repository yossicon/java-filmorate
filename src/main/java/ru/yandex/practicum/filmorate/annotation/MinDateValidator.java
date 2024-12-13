package ru.yandex.practicum.filmorate.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MinDateValidator implements ConstraintValidator<MinDate, LocalDate> {

    private LocalDate minDate;

    @Override
    public void initialize(MinDate constraintAnnotation) {
        minDate = LocalDate.parse(constraintAnnotation.value(), DateTimeFormatter.ISO_DATE);
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return !value.isBefore(minDate);
    }
}
