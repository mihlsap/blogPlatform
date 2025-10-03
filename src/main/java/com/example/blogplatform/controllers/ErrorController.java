package com.example.blogplatform.controllers;

import com.example.blogplatform.utils.GenericResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * Global exception handler for REST APIs in the blog platform.
 *
 * <p>This class uses Spring's {@link ControllerAdvice} to intercept and handle exceptions thrown by
 * REST controllers, ensuring consistent and structured error responses across the application.
 *
 * <p>It handles:
 * <ul>
 *     <li>Validation errors from method arguments annotated with {@code @Valid}</li>
 *     <li>Constraint violations from {@code @RequestParam}, {@code @PathVariable}, etc.</li>
 *     <li>Malformed JSON input (deserialization issues)</li>
 *     <li>Business logic or data integrity violations (e.g., illegal state, bad arguments)</li>
 *     <li>Authentication failures such as invalid login credentials</li>
 *     <li>Generic unhandled exceptions</li>
 * </ul>
 *
 * <p>All responses are wrapped using {@link GenericResponse} to maintain a standardized format
 * for API error handling.
 */
@RestController
@ControllerAdvice
@Slf4j
public class ErrorController {

    /**
     * Handles validation errors when a request body fails {@code @Valid} checks,
     * such as missing fields or invalid field formats in DTOs.
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
     * Handles validation errors for path variables, query parameters, or form data
     * annotated with {@code @Valid} or {@code @Validated}.
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
     * Handles errors caused by malformed or unreadable JSON input,
     * such as syntax errors or incompatible field types during deserialization.
     *
     * @param e the exception thrown during deserialization of the request
     * @return a {@link ResponseEntity} with an error message and HTTP 400 status
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<GenericResponse<Void>> handleMalformedJson(HttpMessageNotReadableException e) {
        log.error("Malformed JSON request: {}", e.getMessage(), e);
        return GenericResponse.error("Malformed JSON request: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link IllegalStateException}, typically thrown when a user request
     * violates business logic or system constraints (e.g., trying to perform an operation
     * that is not currently allowed).
     *
     * @param e the exception thrown during processing of the user request
     * @return a {@link ResponseEntity} with an error message and HTTP 409 status
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<GenericResponse<Void>> handleIllegalStateException(IllegalStateException e) {
        log.error("Caught IllegalStateException: {}", e.getMessage(), e);
        return GenericResponse.error("Illegal state exception: " + e.getMessage(), HttpStatus.CONFLICT);
    }

    /**
     * Handles {@link IllegalArgumentException}, typically thrown when input parameters
     * are invalid, or when a requested resource does not exist or already exists.
     *
     * @param e the exception thrown during processing of the user request
     * @return a {@link ResponseEntity} with an error message and HTTP 400 status
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<GenericResponse<Void>> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("Caught IllegalArgumentException: {}", e.getMessage(), e);
        return GenericResponse.error("Illegal argument exception: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles any other unhandled exceptions to prevent exposing internal details.
     *
     * @param e the exception that was thrown
     * @return a {@link ResponseEntity} with a generic error message and HTTP 500 status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponse<Void>> handleException(Exception e) {
        log.error("Caught Exception: {}", e.getMessage(), e);
        return GenericResponse.error("Unknown Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles authentication failures due to invalid username or password,
     * typically thrown during login attempts with incorrect credentials.
     *
     * @param e the {@link BadCredentialsException} indicating invalid authentication input
     * @return a {@link ResponseEntity} with an error message and HTTP 401 status
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<GenericResponse<Void>> handleBadCredentialsException(BadCredentialsException e) {
        log.error("Caught BadCredentialsException: {}", e.getMessage(), e);
        return GenericResponse.error("Incorrect username or password.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<GenericResponse<Void>> handleEntityNotFoundException(EntityNotFoundException e) {
        log.error("Caught EntityNotFoundException: {}", e.getMessage(), e);
        return GenericResponse.error(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
