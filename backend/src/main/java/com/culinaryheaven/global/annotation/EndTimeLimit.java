package com.culinaryheaven.global.annotation;

import com.culinaryheaven.global.annotation.validator.EndTimeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EndTimeValidator.class)
public @interface EndTimeLimit {
    String message() default "대회 끝나는 시간은 일요일 11시 59분 59초이어야 합니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
