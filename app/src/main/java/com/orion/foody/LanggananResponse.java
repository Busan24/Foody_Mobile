package com.orion.foody;

import java.util.List;

public class LanggananResponse {
    private boolean success;
    private String status;
    private List<LanggananModel> data;

    public boolean isSuccess() {
        return success;
    }

    public String getStatus() {
        return status;
    }

    public List<LanggananModel> getData() {
        return data;
    }
}
