package com.goodkredit.myapplication.responses.paramount;

import com.goodkredit.myapplication.bean.ParamountVehicleSeriesMaker;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class GetVehicleMakerResponse {
    @SerializedName("data")
    @Expose
    private List<ParamountVehicleSeriesMaker> vehicleMaker = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<ParamountVehicleSeriesMaker> getVehicleMaker() {
        return vehicleMaker;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
