package com.goodkredit.myapplication.responses.paramount;

import com.goodkredit.myapplication.bean.ParamountCharges;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class GetParamountVehicleTypeResponse {
    @SerializedName("data")
    @Expose
    private List<ParamountCharges> ltoMVTypes = new ArrayList<>();
    @SerializedName("currentyear")
    @Expose
    private String currentyear;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<ParamountCharges> getLtoMVTypes() {
        return ltoMVTypes;
    }

    public String getCurrentyear() {
        return currentyear;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
