package com.goodkredit.myapplication.responses.skycable;

import com.goodkredit.myapplication.bean.SkycableConfig;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CheckIfConfigIsAvailableResponse {
    @SerializedName("data")
    @Expose
    private SkycableConfig skycableConfig = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public SkycableConfig getSkycableConfig() {
        return skycableConfig;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
