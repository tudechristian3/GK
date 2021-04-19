package com.goodkredit.myapplication.responses;

import com.goodkredit.myapplication.bean.Promotions;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class GetMerchantPromosResponse {
    @SerializedName("data")
    @Expose
    private List<Promotions> promotions = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<Promotions> getPromotions() {
        return promotions;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
