// OtpVerificationRequest.java

package com.example.foody;

import com.google.gson.annotations.SerializedName;

// OtpVerificationRequest.java
public class OtpVerificationRequest {
    private String otp;

    public OtpVerificationRequest(String otp) {
        this.otp = otp;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}


