package com.goodkredit.myapplication.responses.mdp;

import com.goodkredit.myapplication.model.SupportConversation;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetMDPSupportConversationResponse {


    @SerializedName("data")
    @Expose
    private List<SupportConversation> MDPSupportConversationList = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<SupportConversation> getMDPSupportConversationList() {
        return MDPSupportConversationList;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}
