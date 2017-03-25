package com.letzunite.applabs.exceptions;

public class CustomConnectionException extends Exception {
    private static final long serialVersionUID = 4484004320855701414L;
    private int statusCode;
    private String message;

    public CustomConnectionException(int statusCode) {
        this.statusCode = statusCode;
        message = "Connection error. Unable to connect to web service";
    }

    public CustomConnectionException(String message) {
        super(message);
        this.message = message;
    }

    public CustomConnectionException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String getLocalizedMessage() {
        return message;
    }

    @Override
    public String getMessage() {
        return getLocalizedMessage();
    }
}
