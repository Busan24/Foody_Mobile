package com.orion.foody;

import com.google.gson.annotations.SerializedName;

public class UpdateProfilePictureRequest {

    @SerializedName("image")
    private String image; // Sesuaikan dengan nama parameter yang digunakan di server

    public UpdateProfilePictureRequest(String image) {
        this.image = image;
    }
}

