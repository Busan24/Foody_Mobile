package com.example.foody;

import java.util.List;

public class ApiError<T> {

    private String status;
    private T message;  // Mengubah tipe data message menjadi T
    private T error;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

    public T getError() {
        return error;
    }

    public void setError(T error) {
        this.error = error;
    }
}
