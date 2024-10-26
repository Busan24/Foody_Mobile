package com.example.foody;

public class ResetPasswordRequest {
    private String current_password, new_password, new_password_confirmation;

    public ResetPasswordRequest(java.lang.String current_password, java.lang.String new_password, java.lang.String new_password_confirmation) {
        this.current_password = current_password;
        this.new_password = new_password;
        this.new_password_confirmation = new_password_confirmation;
    }

    public String getCurrent_password() {
        return current_password;
    }

    public java.lang.String getNew_password() {
        return new_password;
    }

    public java.lang.String getNew_password_confirmation() {
        return new_password_confirmation;
    }
}
