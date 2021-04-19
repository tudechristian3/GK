package com.goodkredit.myapplication.responses.gkstore;

import com.goodkredit.myapplication.bean.GKStoreMerchants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GoodApps on 30/01/2018.
 */

public class GetStoreMerchantsResponse {
    @SerializedName("data")
    @Expose
    private List<GKStoreMerchants> gkstores = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<GKStoreMerchants> getGkstores() {
        return gkstores;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
