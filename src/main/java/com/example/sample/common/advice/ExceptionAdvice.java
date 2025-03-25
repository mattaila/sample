package com.example.sample.common.advice;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.sample.common.exception.RecordNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 例外Handler
 */
@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ExceptionAdvice {

    private final MessageSource messageSource;

    @ExceptionHandler
    public String handleException(RecordNotFoundException ex, Model model, Locale locale) {

        String message = messageSource.getMessage("message.common.record_not_found", null, locale);
        model.addAttribute("message", message);
        return "error/error";
    }

    @ExceptionHandler
    public String handleAllException(Throwable throwable, Model model, Locale locale) {
        model.addAttribute("message", throwable.getMessage());
        return "error/error";
    }
}
