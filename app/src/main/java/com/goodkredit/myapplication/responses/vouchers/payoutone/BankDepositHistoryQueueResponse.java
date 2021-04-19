package com.goodkredit.myapplication.responses.vouchers.payoutone;

import com.goodkredit.myapplication.model.vouchers.BankDepositHistoryQueue;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BankDepositHistoryQueueResponse {

    @SerializedName("data")
    @Expose
    private List<BankDepositHistoryQueue> bankDepositHistoryQueueList = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<BankDepositHistoryQueue> getBankDepositHistoryQueueList() {
        return bankDepositHistoryQueueList;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
