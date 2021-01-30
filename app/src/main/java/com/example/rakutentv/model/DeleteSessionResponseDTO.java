package com.example.rakutentv.model;

import com.google.gson.annotations.SerializedName;

public class DeleteSessionResponseDTO {

    @SerializedName("success")
    private Boolean success;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
