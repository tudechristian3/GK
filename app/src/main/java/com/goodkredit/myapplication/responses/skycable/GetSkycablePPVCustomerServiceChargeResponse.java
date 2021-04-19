package com.goodkredit.myapplication.responses.skycable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class GetSkycablePPVCustomerServiceChargeResponse {
    @SerializedName("data")
    @Expose
    private String customerServiceCharge = "";
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public String getCustomerServiceCharge() {
        return customerServiceCharge;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
