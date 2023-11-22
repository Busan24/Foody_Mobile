package com.example.foody;
// BmiCategoryModel.java

import com.google.gson.annotations.SerializedName;

public class BmiCategoryModel {
    @SerializedName("status")
    private String status;

    @SerializedName("color")
    private String color;

    @SerializedName("strongColor")
    private String strongColor;

    // Getter methods

    public String getStatus() {
        return status;
    }

    public String getColor() {
        return color;
    }

    public String getStrongColor() {
        return strongColor;
    }
}

