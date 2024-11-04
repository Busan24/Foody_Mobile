package com.orion.foody;

import com.google.gson.annotations.SerializedName;

public class LoginResponseModel {
    // Contoh: Jika respons dari API termasuk token, ID pengguna, dll.
    @SerializedName("token")
    private String token;

    @SerializedName("userId")
    private String userId;

    // Getter dan setter sesuai kebutuhan
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
