package com.example.sample.common.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {DateCheckValidator.class})
public @interface DateCheck {

    String message() default "{message.common.validation.datecheck}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @interface List {
        DateCheck[] value();
    }
}
