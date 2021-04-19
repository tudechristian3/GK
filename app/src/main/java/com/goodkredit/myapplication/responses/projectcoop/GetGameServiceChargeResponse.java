package com.goodkredit.myapplication.responses.projectcoop;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class GetGameServiceChargeResponse {
    @SerializedName("data")
    @Expose
    private double serviceCharge;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public double getServiceCharge() {
        return serviceCharge;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
