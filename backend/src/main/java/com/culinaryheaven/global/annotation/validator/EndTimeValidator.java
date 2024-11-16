package com.culinaryheaven.global.annotation.validator;

import com.culinaryheaven.global.annotation.EndTimeLimit;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class EndTimeValidator implements ConstraintValidator<EndTimeLimit, LocalDateTime> {

    @Override
    public boolean isValid(LocalDateTime localDateTime, ConstraintValidatorContext constraintValidatorContext) {
        if (localDateTime == null) {
            return false;
        }

        return localDateTime.getDayOfWeek() == DayOfWeek.SUNDAY
                && localDateTime.getHour() == 23
                && localDateTime.getMinute() == 59
                && localDateTime.getSecond() == 59;
    }

    private LocalDateTime getEndOfWeekSaturday(LocalDateTime startDate) {
        DayOfWeek dayOfWeek = startDate.getDayOfWeek();

        if (dayOfWeek != DayOfWeek.MONDAY) {
            throw new IllegalArgumentException("시작일은 월요일만 가능합니다.");
        }

        int daysUntilSaturday = DayOfWeek.SATURDAY.getValue() - dayOfWeek.getValue();

        return startDate.plusDays(daysUntilSaturday)
                .with(LocalTime.of(23, 59, 59));
    }
}
