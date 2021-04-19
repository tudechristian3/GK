package com.goodkredit.myapplication.responses;

import com.goodkredit.myapplication.model.GKProcessRequest;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GKProcessRequestResponse {
    @SerializedName("data")
    @Expose
    private GKProcessRequest data;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

    public GKProcessRequest getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }
}
