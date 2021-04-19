package com.goodkredit.myapplication.responses.skycable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SkycableProcessVoucherConsummationResponse {
    @SerializedName("data")
    @Expose
    private String transactionNo;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public String getTransactionNo() {
        return transactionNo;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
