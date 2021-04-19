package com.goodkredit.myapplication.responses.medpadala;

import com.goodkredit.myapplication.model.MedPadalaTransaction;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ban_Lenovo on 2/27/2018.
 */

public class GetMedPadalaTransactionsResponse {

    @SerializedName("data")
    @Expose
    private List<MedPadalaTransaction> transactions = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("meessage")
    @Expose
    private String meessage;

    public List<MedPadalaTransaction> getTransactions() {
        return transactions;
    }

    public String getStatus() {
        return status;
    }

    public String getMeessage() {
        return meessage;
    }
}
