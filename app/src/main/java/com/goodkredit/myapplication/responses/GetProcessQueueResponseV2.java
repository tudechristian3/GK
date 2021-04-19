package com.goodkredit.myapplication.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProcessQueueResponseV2 {

    @SerializedName("loaddata")
    @Expose
    private String loaddata;
    @SerializedName("billspaydata")
    @Expose
    private String billspaydata;
    @SerializedName("smartreloadqueue")
    @Expose
    private String smartreloadqueue;
    @SerializedName("buyvoucherqueue")
    @Expose
    private String buyvoucherqueue;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;


    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getLoaddata() {
        return loaddata;
    }

    public String getBillspaydata() {
        return billspaydata;
    }

    public String getSmartreloadqueue() {
        return smartreloadqueue;
    }

    public String getBuyvoucherqueue() {
        return buyvoucherqueue;
    }

}
