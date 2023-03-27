package com.github.yildizmy.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

import static com.github.yildizmy.common.Constants.*;

/**
 * Global exception handler class for handling all the exceptions
 */
@Slf4j(topic = "GlobalExceptionHandler")
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * This parameter is used to print StackTrace while debugging. Its value is false by default.
     */
    @Value("${exception.trace:false}")
    private boolean printStackTrace;

    /**
     * Handles MethodArgumentNotValidException
     *
     * @param ex
     * @param headers
     * @param statusCode
     * @param request
     * @return ResponseEntity<Object> with detailed information related to the error
     */
    @Override
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode statusCode,
                                                                  WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), VALIDATION_ERROR);
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorResponse.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        log.error(METHOD_ARGUMENT_NOT_VALID, ex);
        return ResponseEntity.unprocessableEntity().body(errorResponse);
    }

    /**
     * Handles custom NoSuchElementFoundException
     *
     * @param ex
     * @param request
     * @return ResponseEntity<Object> with detailed information related to the error
     */
    @ExceptionHandler(NoSuchElementFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleNoSuchElementFoundException(NoSuchElementFoundException ex, WebRequest request) {
        log.error(NOT_FOUND, ex);
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND, request);
    }

    /**
     * Handles custom ElementAlreadyExistsException
     *
     * @param ex
     * @param request
     * @return ResponseEntity<Object> with detailed information related to the error
     */
    @ExceptionHandler(ElementAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleElementAlreadyExistsException(ElementAlreadyExistsException ex, WebRequest request) {
        log.error(ALREADY_EXISTS, ex);
        return buildErrorResponse(ex, HttpStatus.CONFLICT, request);
    }

    /**
     * Handles custom InsufficientFundsException
     *
     * @param ex
     * @param request
     * @return ResponseEntity<Object> with detailed information related to the error
     */
    @ExceptionHandler(InsufficientFundsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleInsufficientFundsException(InsufficientFundsException ex, WebRequest request) {
        log.error(METHOD_ARGUMENT_NOT_VALID, ex);
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Handles AuthenticationException
     *
     * @param ex
     * @param request
     * @return ResponseEntity<Object> with detailed information related to the error
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        log.error(UNAUTHORIZED, ex);
        return buildErrorResponse(ex, HttpStatus.UNAUTHORIZED, request);
    }

    /**
     * Handles ConstraintViolationException during field validation
     *
     * @param ex
     * @param request
     * @return ResponseEntity<Object> with detailed information related to the error
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleConstraintValidationException(ConstraintViolationException ex, WebRequest request) {
        log.warn(FIELD_NOT_VALIDATED, ex);
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Handles all the uncaught exceptions that cannot be caught by the previous methods
     *
     * @param ex
     * @param request
     * @return ResponseEntity<Object> with detailed information related to the error
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAllUncaughtException(Exception ex, WebRequest request) {
        log.error(UNKNOWN_ERROR, ex);
        return buildErrorResponse(ex, UNKNOWN_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    /**
     * Build error message by the given exception, status and request
     *
     * @param ex
     * @param statusCode
     * @param request
     * @return ResponseEntity<Object> with detailed information related to the error
     */
    private ResponseEntity<Object> buildErrorResponse(Exception ex,
                                                      HttpStatusCode statusCode,
                                                      WebRequest request) {
        return buildErrorResponse(ex, ex.getMessage(), statusCode, request);
    }

    /**
     * Build error message by the given exception, message, status and request
     *
     * @param ex
     * @param message
     * @param statusCode
     * @param request
     * @return ResponseEntity<Object> with detailed information related to the error
     */
    private ResponseEntity<Object> buildErrorResponse(Exception ex,
                                                      String message,
                                                      HttpStatusCode statusCode,
                                                      WebRequest request) {
        final ErrorResponse errorResponse = new ErrorResponse(statusCode.value(), message);
        if (printStackTrace && isTraceOn(request)) {
            errorResponse.setStackTrace(ExceptionUtils.getStackTrace(ex));
        }
        return ResponseEntity.status(statusCode).body(errorResponse);
    }

    /**
     * Checks the tracing parameter sent by request
     *
     * @param request
     * @return the tracing status based on the request
     */
    private boolean isTraceOn(WebRequest request) {
        String[] value = request.getParameterValues(TRACE);
        return Objects.nonNull(value)
                && value.length > 0
                && value[0].contentEquals("true");
    }

    /**
     * Handles internal exceptions
     *
     * @param ex
     * @param body
     * @param headers
     * @param statusCode
     * @param request
     * @return ResponseEntity<Object> with detailed information related to the error
     */
    @Override
    public ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request) {

        return buildErrorResponse(ex, statusCode, request);
    }
}
