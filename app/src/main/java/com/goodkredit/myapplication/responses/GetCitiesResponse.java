package com.goodkredit.myapplication.responses;

import com.goodkredit.myapplication.bean.AddressList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class GetCitiesResponse {
    @SerializedName("data")
    @Expose
    private List<AddressList> cities = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<AddressList> getCities() {
        return cities;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
