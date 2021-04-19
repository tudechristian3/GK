package com.goodkredit.myapplication.responses;

import com.goodkredit.myapplication.bean.TransactionDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class ConsummationTransactionDetailsResponse {
    @SerializedName("data")
    @Expose
    private List<TransactionDetails> transaction = new ArrayList<>();
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<TransactionDetails> getTransaction() {
        return transaction;
    }

    public void setTransaction(List<TransactionDetails> transaction) {
        this.transaction = transaction;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
