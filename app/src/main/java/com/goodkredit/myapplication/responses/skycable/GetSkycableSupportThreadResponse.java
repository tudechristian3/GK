package com.goodkredit.myapplication.responses.skycable;

import com.goodkredit.myapplication.bean.SkycableSupportHelpTopics;
import com.goodkredit.myapplication.bean.SkycableSupportThread;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class GetSkycableSupportThreadResponse {
    @SerializedName("data")
    @Expose
    private List<SkycableSupportThread> skycableSupportThreadList = new ArrayList<>();
    @SerializedName("helptopics")
    @Expose
    private List<SkycableSupportHelpTopics> skycableSupportHelpTopicsList = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<SkycableSupportThread> getSkycableSupportThreadList() {
        return skycableSupportThreadList;
    }

    public List<SkycableSupportHelpTopics> getSkycableSupportHelpTopicsList() {
        return skycableSupportHelpTopicsList;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
