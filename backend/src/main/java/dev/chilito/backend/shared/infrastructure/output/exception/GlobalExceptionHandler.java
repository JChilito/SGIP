package dev.chilito.backend.shared.infrastructure.output.exception;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dev.chilito.backend.shared.domain.exception.BusinessException;
import dev.chilito.backend.shared.domain.exception.ErrorCode;
import dev.chilito.backend.shared.infrastructure.output.exception.structure.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;

/**
 * This class handles all exceptions that may occur in the application
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Map of error codes to HTTP status codes
    private static final Map<ErrorCode, HttpStatus> STATUS_MAP = new EnumMap<>(ErrorCode.class);

    // Initialize the map
    static {
        STATUS_MAP.put(ErrorCode.ENTITY_ALREADY_EXISTS, HttpStatus.CONFLICT);
        STATUS_MAP.put(ErrorCode.ENTITY_NOT_FOUND, HttpStatus.NOT_FOUND);
        STATUS_MAP.put(ErrorCode.INVALID_CREDENTIALS, HttpStatus.UNAUTHORIZED);
        STATUS_MAP.put(ErrorCode.USER_DISABLED, HttpStatus.FORBIDDEN);
        STATUS_MAP.put(ErrorCode.BUSINESS_RULE_VIOLATION, HttpStatus.BAD_REQUEST);
        STATUS_MAP.put(ErrorCode.GENERIC_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles validation exceptions
     * 
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();

        // Get the validation errors
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorCode errorCode = ErrorCode.BUSINESS_RULE_VIOLATION;
        HttpStatus status = STATUS_MAP.getOrDefault(errorCode, HttpStatus.BAD_REQUEST);

        // Build the response
        ErrorResponse response = ErrorResponse.builder()
                .errorCode(errorCode.getCode())
                .errorMessage("Input validation failed")
                .httpStatusCode(status.value())
                .url(request.getRequestURI())
                .method(request.getMethod())
                .timestamp(LocalDateTime.now())
                .validationErrors(errors)
                .build();

        return ResponseEntity.status(status).body(response);
    }

    /**
     * Handles business exceptions
     * 
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex, HttpServletRequest request) {

        // Search the status code in the map, if not found use BAD_REQUEST
        HttpStatus status = STATUS_MAP.getOrDefault(ex.getErrorCode(), HttpStatus.BAD_REQUEST);

        // Build the response
        ErrorResponse response = ErrorResponse.builder()
                .errorCode(ex.getErrorCode().getCode())
                .errorMessage(ex.getMessage())
                .httpStatusCode(status.value())
                .url(request.getRequestURI())
                .method(request.getMethod())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(status).body(response);
    }

    /**
     * Handles generic exceptions
     * 
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
        ErrorResponse response = ErrorResponse.builder()
                .errorCode(ErrorCode.GENERIC_ERROR.getCode())
                .errorMessage("Internal Server Error: " + ex.getMessage())
                .httpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .url(request.getRequestURI())
                .method(request.getMethod())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}