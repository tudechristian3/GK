package com.goodkredit.myapplication.responses.projectcoop;

import com.goodkredit.myapplication.bean.projectcoop.GameProduct;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class GetGameProductsResponse {
    @SerializedName("data")
    @Expose
    private List<GameProduct> gameProductList = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<GameProduct> getGameProductList() {
        return gameProductList;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
