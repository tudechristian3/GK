package com.goodkredit.myapplication.responses;

import com.goodkredit.myapplication.bean.PrepaidLogs;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class GetPrepaidLogsResponse {
    @SerializedName("data")
    @Expose
    private List<PrepaidLogs> prepaidLogs = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<PrepaidLogs> getPrepaidLogs() {
        return prepaidLogs;
    }

    public void setPrepaidLogs(List<PrepaidLogs> prepaidLogs) {
        this.prepaidLogs = prepaidLogs;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
