package com.daniellsantiago.spaceprobe.interfaces.http.dto;

public class ApiErrorResponse {
    private final String type;
    private final String message;

    public ApiErrorResponse(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
