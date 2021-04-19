package com.goodkredit.myapplication.responses.vouchers.payoutone;

import com.goodkredit.myapplication.model.vouchers.SubscriberBankAccounts;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SubscriberBankAccountsResponse {

    @SerializedName("data")
    @Expose
    private List<SubscriberBankAccounts> subscriberBankAccountsList = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<SubscriberBankAccounts> getSubscriberBankAccountsList() {
        return subscriberBankAccountsList;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}