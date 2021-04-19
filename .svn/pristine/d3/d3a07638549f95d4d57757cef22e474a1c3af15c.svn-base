package com.goodkredit.myapplication.responses.freesms;

import com.goodkredit.myapplication.model.freesms.FreeSMSHistory;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetFreeSMSHistoryResponse {
    @SerializedName("data")
    @Expose
    private List<FreeSMSHistory> freeSMSHistoryList = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<FreeSMSHistory> getFreeSMSHistoryList() {
        return freeSMSHistoryList;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
