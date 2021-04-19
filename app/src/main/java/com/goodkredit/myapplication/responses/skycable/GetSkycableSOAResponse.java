package com.goodkredit.myapplication.responses.skycable;

import com.goodkredit.myapplication.bean.SkycableSOA;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class GetSkycableSOAResponse {
    @SerializedName("data")
    @Expose
    private List<SkycableSOA> skycableSOAList = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<SkycableSOA> getSkycableSOAList() {
        return skycableSOAList;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
