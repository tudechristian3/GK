package com.goodkredit.myapplication.responses.skycable;

import com.goodkredit.myapplication.bean.SkycableSupportFAQ;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class GetSkycableSupportFAQResponse {
    @SerializedName("data")
    @Expose
    private List<SkycableSupportFAQ> skycableSupportFAQList = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<SkycableSupportFAQ> getSkycableSupportFAQList() {
        return skycableSupportFAQList;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
