package com.goodkredit.myapplication.responses.gkstore;

import com.goodkredit.myapplication.bean.GKStoreMerchants;
import com.goodkredit.myapplication.bean.GKStoreProducts;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GoodApps on 22/01/2018.
 */

public class GetStoreProductsResponse {
    @SerializedName("data")
    @Expose
    private List<GKStoreProducts> gkstores = new ArrayList<>();
    @SerializedName("storeinfo")
    @Expose
    private GKStoreMerchants gkStoreMerchants;
    @SerializedName("isagent")
    @Expose
    private boolean isagent;
    @SerializedName("currentdate")
    @Expose
    private String currentdate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<GKStoreProducts> getGKStore() {
        return gkstores;
    }

    public boolean getisIsagent() {
        return isagent;
    }

    public GKStoreMerchants getGkStoreMerchants() {
        return gkStoreMerchants;
    }

    public String getCurrentdate() {
        return currentdate;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
