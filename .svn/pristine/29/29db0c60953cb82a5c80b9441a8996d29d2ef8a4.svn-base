package com.goodkredit.myapplication.responses.paramount;

import com.goodkredit.myapplication.bean.ParamountQueue;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class GetParamountHistoryResponse {
    @SerializedName("data")
    @Expose
    private List<ParamountQueue> paramountHistory = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<ParamountQueue> getParamountHistory() {
        return paramountHistory;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
