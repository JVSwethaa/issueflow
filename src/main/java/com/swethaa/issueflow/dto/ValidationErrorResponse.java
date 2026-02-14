package com.swethaa.issueflow.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class ValidationErrorResponse {

    private final int status;
    private final Map<String, String> errors;
    private final String path;
    private final LocalDateTime timestamp;

    public ValidationErrorResponse(int status, Map<String, String> errors, String path, LocalDateTime timestamp) {
        this.status = status;
        this.errors = errors;
        this.path = path;
        this.timestamp = timestamp;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public int getStatus() {
        return status;
    }

    public String getPath() {
        return path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}

