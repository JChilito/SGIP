package dev.chilito.backend.shared.domain.exception;

import lombok.Getter;

/**
 * Exception that represents a business error
 */
@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
