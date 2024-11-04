package com.orion.foody;

public class OtpResponse {
    private String message; // Atur properti sesuai respons API Anda

    private boolean verified;

    // constructor, getters, and setters

    public boolean isVerified() {
        return verified;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
