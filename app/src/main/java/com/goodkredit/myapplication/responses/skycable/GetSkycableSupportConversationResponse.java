package com.goodkredit.myapplication.responses.skycable;

import com.goodkredit.myapplication.bean.SkycableConversation;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class GetSkycableSupportConversationResponse {
    @SerializedName("data")
    @Expose
    private List<SkycableConversation> skycableConversationList = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<SkycableConversation> getSkycableConversationList() {
        return skycableConversationList;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
