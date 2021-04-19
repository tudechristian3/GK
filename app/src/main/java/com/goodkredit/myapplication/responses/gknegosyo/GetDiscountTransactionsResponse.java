package com.goodkredit.myapplication.responses.gknegosyo;

import com.goodkredit.myapplication.model.GKNegosyoTransactionDetailPerServiceType;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetDiscountTransactionsResponse {

    @SerializedName("data")
    @Expose
    private List<GKNegosyoTransactionDetailPerServiceType> data = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<GKNegosyoTransactionDetailPerServiceType> getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
