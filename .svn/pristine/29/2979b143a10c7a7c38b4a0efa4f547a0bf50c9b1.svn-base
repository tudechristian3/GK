package com.goodkredit.myapplication.responses.paramount;

import com.goodkredit.myapplication.bean.ParamountVehicleSeriesMaker;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class GetVehicleSeriesResponse {
    @SerializedName("data")
    @Expose
    private List<ParamountVehicleSeriesMaker> vehicleSeries = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<ParamountVehicleSeriesMaker> getVehicleSeries() {
        return vehicleSeries;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
