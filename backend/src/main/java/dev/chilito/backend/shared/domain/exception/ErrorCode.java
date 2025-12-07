package dev.chilito.backend.shared.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum that represents the error codes
 */
@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    GENERIC_ERROR("GC-0001", "Generic error"),
    ENTITY_ALREADY_EXISTS("GC-0002", "Entity already exists"),
    ENTITY_NOT_FOUND("GC-0003", "Entity not found"),
    BUSINESS_RULE_VIOLATION("GC-0004", "Business rule violation"),
    INVALID_CREDENTIALS("GC-0005", "Invalid credentials"),
    USER_DISABLED("GC-0006", "The user has not been verified, please check your email to verify your account");

    private final String code;
    private final String message;
}
