package com.goodkredit.myapplication.responses.dropoff;

import com.goodkredit.myapplication.model.dropoff.DropOffMerchants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SearchDropOffMerchantsResponse {

    @SerializedName("data")
    @Expose
    private List<DropOffMerchants> dropOffMerchantsList = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<DropOffMerchants> getDropOffMerchantsList() {
        return dropOffMerchantsList;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
