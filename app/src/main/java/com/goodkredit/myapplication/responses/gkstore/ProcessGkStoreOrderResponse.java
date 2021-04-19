package com.goodkredit.myapplication.responses.gkstore;

import com.goodkredit.myapplication.bean.GKStoreProducts;
import com.goodkredit.myapplication.bean.PrepaidVoucherTransaction;
import com.goodkredit.myapplication.bean.ProcessGKStoreOrder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GoodApps on 09/02/2018.
 */

public class ProcessGkStoreOrderResponse {
    @SerializedName("data")
    @Expose
    private String ordertxnno;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public String getOrdertxnno() {
        return ordertxnno;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
