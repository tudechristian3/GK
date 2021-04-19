package com.goodkredit.myapplication.responses.prepaidload;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CheckNetworkConnectionResponse {
    @SerializedName("data")
    @Expose
    private boolean isIntermittent;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public boolean isIntermittent() {
        return isIntermittent;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
