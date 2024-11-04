package com.orion.foody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifikasiOtpModel {
    @SerializedName("otp")
    @Expose
    private String otp;


    public VerifikasiOtpModel(String otp) {
        this.otp = otp;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}

