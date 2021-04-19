package com.goodkredit.myapplication.responses.skycable;

import com.goodkredit.myapplication.bean.SkycableConfig;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class GetSkycableConfigResponse {
    @SerializedName("data")
    @Expose
    private List<SkycableConfig> skycableConfigs = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<SkycableConfig> getSkycableConfigs() {
        return skycableConfigs;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
