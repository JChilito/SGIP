package dev.chilito.backend.shared.infrastructure.output.exception.structure;

import java.time.LocalDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Exclude null fields from the JSON response
public class ErrorResponse {

    /**
     * Error code generated
     */
    private String errorCode;
    /**
     * Error message generated
     */
    private String errorMessage;
    /**
     * Http status code
     */
    private Integer httpStatusCode;
    /**
     * Url of the request that generated the error
     */
    @Accessors(chain = true)
    private String url;
    /**
     * Method of the request that generated the error
     */
    @Accessors(chain = true)
    private String method;
    /**
     * Timestamp of the error
     */
    private LocalDateTime timestamp;
    /**
     * Validation errors
     */
    private Map<String, String> validationErrors;
}
