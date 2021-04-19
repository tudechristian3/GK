package com.goodkredit.myapplication.responses.uno;

import com.goodkredit.myapplication.model.UnoPointsConversion;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetUNOPointsConversionResponse {

    @SerializedName("data")
    @Expose
    private UnoPointsConversion data;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public UnoPointsConversion getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
