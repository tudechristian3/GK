package com.goodkredit.myapplication.responses.skycable;

import com.goodkredit.myapplication.bean.SkycableAccounts;
import com.goodkredit.myapplication.bean.SkycableSOA;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class CallLinkSkycableAccountResponse {
    @SerializedName("data")
    @Expose
    private List<SkycableAccounts> skycableAccounts = new ArrayList<>();
    @SerializedName("soa")
    @Expose
    private List<SkycableSOA> skycableSOAList = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<SkycableAccounts> getSkycableAccounts() {
        return skycableAccounts;
    }

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
