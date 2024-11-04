package com.orion.foody;

public class OtpRequest {
    private String email;
    private String otp;

    // Constructor with both email and OTP
    public OtpRequest(String email, String otp) {
        this.email = email;
        this.otp = otp;
    }

    // Constructor with only email
    public OtpRequest(String email) {
        this.email = email;
    }

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
