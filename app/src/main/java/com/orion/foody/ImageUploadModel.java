package com.orion.foody;

import com.google.gson.annotations.SerializedName;

public class ImageUploadModel {
    @SerializedName("image") // Sesuaikan dengan nama field di API
    private String imageBase64; // Gunakan Base64 untuk mengirim gambar

    public ImageUploadModel(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
}

