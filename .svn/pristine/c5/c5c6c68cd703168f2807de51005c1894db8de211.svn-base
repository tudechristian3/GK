package com.goodkredit.myapplication.responses.support;

import com.goodkredit.myapplication.model.SupportThread;
import com.goodkredit.myapplication.model.SupportHelpTopics;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetSupportThreadResponse {
    @SerializedName("data")
    @Expose
    private List<SupportThread> supportThreadList = new ArrayList<>();
    @SerializedName("helptopics")
    @Expose
    private List<SupportHelpTopics> helpTopicsList = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<SupportThread> getSupportThreadList() {
        return supportThreadList;
    }

    public List<SupportHelpTopics> getHelpTopicsList() {
        return helpTopicsList;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
