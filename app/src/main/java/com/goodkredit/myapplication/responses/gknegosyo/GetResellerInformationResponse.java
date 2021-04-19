package com.goodkredit.myapplication.responses.gknegosyo;

import com.goodkredit.myapplication.model.GKNegosyoResellerInformation;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetResellerInformationResponse {

    @SerializedName("data")
    @Expose
    private GKNegosyoResellerInformation data;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public GKNegosyoResellerInformation getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
