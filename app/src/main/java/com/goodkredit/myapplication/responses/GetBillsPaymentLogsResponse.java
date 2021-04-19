package com.goodkredit.myapplication.responses;

import com.goodkredit.myapplication.bean.BillsPaymentLogs;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class GetBillsPaymentLogsResponse {
    @SerializedName("data")
    @Expose
    private List<BillsPaymentLogs> billsPaymentLogs = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<BillsPaymentLogs> getBillsPaymentLogs() {
        return billsPaymentLogs;
    }

    public void setBillsPaymentLogs(List<BillsPaymentLogs> billsPaymentLogs) {
        this.billsPaymentLogs = billsPaymentLogs;
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
