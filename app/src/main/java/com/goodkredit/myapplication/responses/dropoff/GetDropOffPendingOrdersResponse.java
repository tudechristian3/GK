package com.goodkredit.myapplication.responses.dropoff;

import com.goodkredit.myapplication.bean.dropoff.DropOffOrder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetDropOffPendingOrdersResponse {
    @SerializedName("data")
    @Expose
    private List<DropOffOrder> pendingDropOffs = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<DropOffOrder> getPendingDropOffs() {
        return pendingDropOffs;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
