package com.goodkredit.myapplication.responses.egame;

import com.goodkredit.myapplication.model.egame.EGameProducts;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetEGameProductsResponse {
    @SerializedName("data")
    @Expose
    private List<EGameProducts> EGameProductsList = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<EGameProducts> getEGameProductsList() {
        return EGameProductsList;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
