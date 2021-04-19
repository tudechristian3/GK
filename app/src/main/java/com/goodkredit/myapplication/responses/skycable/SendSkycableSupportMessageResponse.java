package com.goodkredit.myapplication.responses.skycable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SendSkycableSupportMessageResponse {
    @SerializedName("data")
    @Expose
    private String ThreadID;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public String getThreadID() {
        return ThreadID;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
