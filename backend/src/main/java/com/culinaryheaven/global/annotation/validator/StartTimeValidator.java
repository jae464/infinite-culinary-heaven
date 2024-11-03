package com.culinaryheaven.global.annotation.validator;

import com.culinaryheaven.global.annotation.StartTimeLimit;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class StartTimeValidator implements ConstraintValidator<StartTimeLimit, LocalDateTime> {

    @Override
    public boolean isValid(LocalDateTime localDateTime, ConstraintValidatorContext constraintValidatorContext) {
        if (localDateTime == null) {
            return false;
        }

        return localDateTime.getDayOfWeek() == DayOfWeek.MONDAY
                && localDateTime.getHour() == 0
                && localDateTime.getMinute() == 0
                && localDateTime.getSecond() == 0;
    }
}
