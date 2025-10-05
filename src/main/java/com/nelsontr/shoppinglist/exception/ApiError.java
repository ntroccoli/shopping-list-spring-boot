package com.nelsontr.shoppinglist.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Schema(name = "ApiError", description = "Standard error response body")
public class ApiError {

    @Schema(description = "Error timestamp (server local time)", example = "2025-10-05T12:34:56.789")
    private LocalDateTime timestamp;

    @Schema(description = "HTTP status code", example = "404")
    private int status;

    @Schema(description = "HTTP reason phrase", example = "Not Found")
    private String error;

    @Schema(description = "Error message", example = "Item with id 42 not found")
    private String message;

    @Schema(description = "Request path", example = "/api/v1/items/42")
    private String path;
}

