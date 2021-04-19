package com.goodkredit.myapplication.responses;

import com.goodkredit.myapplication.bean.PrepaidRequest;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class GetVirtualVoucherProductResponse {
    @SerializedName("data")
    @Expose
    private List<PrepaidRequest> prepaidRequest = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<PrepaidRequest> getPrepaidRequest() {
        return prepaidRequest;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
