package com.goodkredit.myapplication.responses.schoolmini;

import com.goodkredit.myapplication.model.schoolmini.SchoolMiniPaymentLogs;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SchoolMiniPaymentLogsResponse {
    @SerializedName("data")
    @Expose
    private List<SchoolMiniPaymentLogs> schoolminipaymentlogsList = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<SchoolMiniPaymentLogs> getSchoolminipaymentlogsList() {
        return schoolminipaymentlogsList;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
