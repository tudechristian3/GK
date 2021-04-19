package com.goodkredit.myapplication.responses.csb;

import com.goodkredit.myapplication.model.CSBRedemption;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ban_Lenovo on 1/10/2018.
 */

public class GetCSBLogsResponse {

    @SerializedName("data")
    @Expose
    private List<CSBRedemption> data = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<CSBRedemption> getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
