package com.nelsontr.shoppinglist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ApiError> handleItemNotFound(ItemNotFoundException ex, WebRequest request) {
        ApiError body = new ApiError();
        body.setTimestamp(LocalDateTime.now());
        body.setStatus(HttpStatus.NOT_FOUND.value());
        body.setError("Not Found");
        body.setMessage(ex.getMessage());
        body.setPath(request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining("; "));
        ApiError body = new ApiError();
        body.setTimestamp(LocalDateTime.now());
        body.setStatus(HttpStatus.BAD_REQUEST.value());
        body.setError("Bad Request");
        body.setMessage(errors.isBlank() ? "Validation failed" : errors);
        body.setPath(request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex, WebRequest request) {
        ApiError body = new ApiError();
        body.setTimestamp(LocalDateTime.now());
        body.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.setError("Internal Server Error");
        body.setMessage(ex.getMessage());
        body.setPath(request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
