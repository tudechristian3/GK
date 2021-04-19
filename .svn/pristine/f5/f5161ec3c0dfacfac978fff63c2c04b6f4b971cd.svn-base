package com.goodkredit.myapplication.responses;

import com.goodkredit.myapplication.bean.PrepaidVoucherTransaction;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class GetPurchasesResponse {
    @SerializedName("data")
    @Expose
    private List<PrepaidVoucherTransaction> transaction = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<PrepaidVoucherTransaction> getTransaction() {
        return transaction;
    }

    public void setTransaction(List<PrepaidVoucherTransaction> transaction) {
        this.transaction = transaction;
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
