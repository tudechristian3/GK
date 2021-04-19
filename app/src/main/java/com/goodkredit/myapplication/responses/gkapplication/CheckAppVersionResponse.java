package com.goodkredit.myapplication.responses.gkapplication;

import com.goodkredit.myapplication.bean.AppVersion;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ban_Lenovo on 11/3/2017.
 */

public class CheckAppVersionResponse {
    @SerializedName("data")
    @Expose
    private AppVersion data;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public AppVersion getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
