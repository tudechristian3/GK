package com.goodkredit.myapplication.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetServiceChargeResponse {
    @SerializedName("data")
    @Expose
    private double servicecharge = 0;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public double getServicecharge() {
        return servicecharge;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
