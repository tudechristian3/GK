package com.goodkredit.myapplication.responses.mdp;

import com.goodkredit.myapplication.model.mdp.MDPSupportHelpTopics;
import com.goodkredit.myapplication.model.mdp.MDPSupportThread;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetMDPSupportThreadResponse {

    @SerializedName("data")
    @Expose
    private List<MDPSupportThread> MDPSupportThreadList = new ArrayList<>();
    @SerializedName("helptopics")
    @Expose
    private List<MDPSupportHelpTopics> MDPSupportHelpTopicsList = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<MDPSupportThread> getMDPSupportThreadList() {
        return MDPSupportThreadList;
    }

    public List<MDPSupportHelpTopics> getMDPSupportHelpTopicsList() {
        return MDPSupportHelpTopicsList;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
