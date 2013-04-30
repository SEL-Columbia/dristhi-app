package org.ei.drishti.domain;

public enum LoginResponse {
    SUCCESS("Login successful."),
    NO_INTERNET_CONNECTIVITY("No internet connection. Please ensure data connectivity"),
    UNKNOWN_RESPONSE("Dristhi login failed. Try later"),
    UNAUTHORIZED("Please check the credentials");

    private String message;

    private LoginResponse(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
