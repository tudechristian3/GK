package com.goodkredit.myapplication.responses.gknegosyo;

import com.goodkredit.myapplication.model.DiscountPerTransactionType;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetGKNegosyoTransactionResponse {

    @SerializedName("data")
    @Expose
    private List<DiscountPerTransactionType> data = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<DiscountPerTransactionType> getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}

