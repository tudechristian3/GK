package com.goodkredit.myapplication.responses.support;

import com.goodkredit.myapplication.bean.SkycableSupportFAQ;
import com.goodkredit.myapplication.model.SupportFAQ;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetSupportFAQResponse {
    @SerializedName("data")
    @Expose
    private List<SupportFAQ> supportFAQList = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<SupportFAQ> getSupportFAQList() {
        return supportFAQList;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
