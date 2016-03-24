package org.ei.telemedicine.domain;

public enum LoginResponse {
    SUCCESS("Login successful."),
    NO_INTERNET_CONNECTIVITY("Please check server connectivity"),
    UNKNOWN_RESPONSE("OpenSRP login failed. Try later"),
    UNAUTHORIZED("Please check the credentials");

    private String payload;
    private String message;

    private LoginResponse(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }

    public String payload() {
        return payload;
    }

    public LoginResponse withPayload(String payload) {
        this.payload = payload;
        return this;
    }
}
