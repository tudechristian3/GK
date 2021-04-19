package com.goodkredit.myapplication.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetGenerateQr {
    @SerializedName("data")
    @Expose
    private double charge;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    public double getCharge() {
        return charge;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}