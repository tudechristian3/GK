package com.goodkredit.myapplication.responses;

import com.goodkredit.myapplication.bean.Branches;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class GetPaymentPartnerBranchesResponse {
    @SerializedName("data")
    @Expose
    private List<Branches> paymentBranches = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<Branches> getPaymentBranches() {
        return paymentBranches;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
