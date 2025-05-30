package com.example.walletmanager3.exception;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    private static final Logger log = LoggerFactory.getLogger(ErrorHandlingControllerAdvice.class);

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onConstraintValidationException(
            ConstraintViolationException e
    ) {

        final List<Violation> violations = e.getConstraintViolations().stream()
                .map(
                        violation -> {
                            String fieldName = "";
                            for (Path.Node node : violation.getPropertyPath()) {
                                fieldName = node.getName();
                            }

                            return new Violation(
                                    fieldName,
                                    violation.getMessage()
                            );
                        }
                )
                .collect(Collectors.toList());

        log.warn("Ошибка: {}", e.getMessage());

        return new ValidationErrorResponse(violations);
    }

    @ResponseBody
    @ExceptionHandler({WalletNotFoundException.class, IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ValidationErrorResponse handleWalletNotFound(
            RuntimeException e
    ) {
        log.warn("Ошибка: {}", e.getMessage());

        final List<Violation> violations = Collections.singletonList(
                new Violation("walletId", e.getMessage())
        );
        return new ValidationErrorResponse(violations);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse onMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        final List<Violation> violations = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ValidationErrorResponse(violations);
    }
}