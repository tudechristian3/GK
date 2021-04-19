package com.goodkredit.myapplication.responses.gkstore;

import com.goodkredit.myapplication.model.FetchStoreOrderList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GoodApps on 13/03/2018.
 */

public class FetchStoreOrderListResponse {
    @SerializedName("data")
    @Expose
    private List<FetchStoreOrderList> gkstorehistory = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<FetchStoreOrderList> getGkStoreHistory() {
        return gkstorehistory;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
