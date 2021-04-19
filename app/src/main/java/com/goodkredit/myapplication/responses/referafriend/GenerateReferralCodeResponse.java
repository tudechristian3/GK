package com.goodkredit.myapplication.responses.referafriend;

import com.goodkredit.myapplication.model.Referral;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenerateReferralCodeResponse {

    @SerializedName("data")
    @Expose
    private Referral data;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public Referral getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
