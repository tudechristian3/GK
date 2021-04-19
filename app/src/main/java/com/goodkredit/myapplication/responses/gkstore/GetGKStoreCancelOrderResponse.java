package com.goodkredit.myapplication.responses.gkstore;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetGKStoreCancelOrderResponse {
    @SerializedName("data")
    @Expose
    private String ordertxn;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public String getOrdertxn() {
        return ordertxn;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
