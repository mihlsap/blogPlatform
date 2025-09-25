package com.example.blogplatform.controllers;

import com.example.blogplatform.utils.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * Global exception handler for REST APIs in the blog platform.
 * <p>
 * This class uses Spring's {@link ControllerAdvice} to intercept and handle exceptions thrown by
 * REST controllers, ensuring consistent and structured error responses across the application.
 * <p>
 * It handles:
 * <ul>
 *     <li>Validation errors from method arguments annotated with {@code @Valid}</li>
 *     <li>Constraint violations from {@code @RequestParam}, {@code @PathVariable}, etc.</li>
 *     <li>Malformed JSON input (deserialization issues)</li>
 *     <li>Unhandled generic exceptions</li>
 * </ul>
 * All responses are wrapped using {@link GenericResponse} to maintain a standard format.
 */
@RestController
@ControllerAdvice
@Slf4j
public class ErrorController {

    /**
     * Handles validation errors when a request body fails {@code @Valid} checks.
     *
     * @param e the exception containing details about failed validation constraints
     * @return a {@link ResponseEntity} with validation messages and HTTP 400 status
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenericResponse<Void>> handleValidationErrors(MethodArgumentNotValidException e) {
        log.error("Caught MethodArgumentNotValidException: {}", e.getMessage(), e);
        return GenericResponse.error(
                e.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(error -> error.getField() + ": " + error.getDefaultMessage())
                        .collect(Collectors.joining("; ")),
                HttpStatus.BAD_REQUEST
        );
    }

    /**
     * Handles validation errors from path variables or query parameters annotated with {@code @Valid}.
     *
     * @param e the exception containing details about constraint violations
     * @return a {@link ResponseEntity} with detailed messages and HTTP 400 status
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<GenericResponse<Void>> handleConstraintViolation(ConstraintViolationException e) {
        log.error("Caught ConstraintViolationException: {}", e.getMessage(), e);
        String errorMessages = e.getConstraintViolations().stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.joining("; "));
        return GenericResponse.error(errorMessages, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles errors caused by malformed or unreadable JSON input.
     *
     * @param e the exception thrown during deserialization of the request
     * @return a {@link ResponseEntity} with an error message and HTTP 400 status
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<GenericResponse<Void>> handleMalformedJson(HttpMessageNotReadableException e) {
        log.error("Malformed JSON request: {}", e.getMessage(), e);
        return GenericResponse.error("Malformed JSON request", HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles any other uncaught exceptions.
     *
     * @param e the exception that was thrown
     * @return a {@link ResponseEntity} with a generic error message and HTTP 500 status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponse<Void>> handleException(Exception e) {
        log.error("Caught Exception: {}", e.getMessage(), e);
        return GenericResponse.error("Unknown Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
