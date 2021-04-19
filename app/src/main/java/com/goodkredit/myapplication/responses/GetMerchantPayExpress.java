package com.goodkredit.myapplication.responses;

import com.goodkredit.myapplication.model.MerchantPayExpress;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ban_Lenovo on 11/16/2017.
 */

public class GetMerchantPayExpress {
    @SerializedName("data")
    @Expose
    private List<MerchantPayExpress> merchantsPayExpress = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status = "";
    @SerializedName("message")
    @Expose
    private String message = "";

    public List<MerchantPayExpress> getMerchantsPayExpress() {
        return merchantsPayExpress;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
