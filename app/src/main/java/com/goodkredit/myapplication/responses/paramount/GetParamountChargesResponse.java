package com.goodkredit.myapplication.responses.paramount;

import com.goodkredit.myapplication.bean.ParamountCharges;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class GetParamountChargesResponse {
    @SerializedName("data")
    @Expose
    private List<ParamountCharges> ltoRequest = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<ParamountCharges> getLtoRequest() {
        return ltoRequest;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
