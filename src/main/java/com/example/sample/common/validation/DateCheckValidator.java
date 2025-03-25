package com.example.sample.common.validation;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateCheckValidator implements ConstraintValidator<DateCheck, Object> {

    /** エラー時のメッセージ */
    String message;

    @Override
    public void initialize(DateCheck constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    /**
     * 開始日が締め切りより後の場合はエラーとする
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(value);

        LocalDate startDate = (LocalDate) beanWrapper.getPropertyValue("startDate");
        LocalDate deadline = (LocalDate) beanWrapper.getPropertyValue("deadline");

        if(Objects.isNull(startDate) || Objects.isNull(deadline)) {
            return false;
        }

        if(startDate.isAfter(deadline)) {
            context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode("startDate").addConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode("deadline").addConstraintViolation();
            return false;
        }

        return true;
    }
}
