package com.goodkredit.myapplication.responses.vouchers;

import com.goodkredit.myapplication.model.vouchers.AccreditedBanks;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AccreditedBanksResponse {
    @SerializedName("data")
    @Expose
    private List<AccreditedBanks> accreditedBanksList = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<AccreditedBanks> getAccreditedBanksList() {
        return accreditedBanksList;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}