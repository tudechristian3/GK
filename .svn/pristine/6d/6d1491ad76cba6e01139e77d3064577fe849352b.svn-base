package com.goodkredit.myapplication.responses.coopassistant;

import com.goodkredit.myapplication.model.GKConfigurations;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CoopandMemberInformationResponse {
    @SerializedName("data")
    @Expose
    private List<GKConfigurations> gkConfigurationsList = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<GKConfigurations> getGkConfigurationsList() {
        return gkConfigurationsList;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}

