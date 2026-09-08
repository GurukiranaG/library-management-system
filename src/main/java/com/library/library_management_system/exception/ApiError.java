package com.library.library_management_system.exception;
import java.time.LocalDateTime;
import java.util.Map;
public class ApiError {
    private  final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;
    private Map<String, String> fieldErrors;

    public ApiError(int status, String error, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }
    public void setFieldErrors(Map<String, String> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}
