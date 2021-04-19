package com.goodkredit.myapplication.responses;

import com.goodkredit.myapplication.bean.AddressList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class GetProvincesResponse {
    @SerializedName("data")
    @Expose
    private List<AddressList> provinces = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<AddressList> getProvinces() {
        return provinces;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
