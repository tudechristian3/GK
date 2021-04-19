package com.goodkredit.myapplication.responses.fairchild;

import com.goodkredit.myapplication.model.fairchild.FairChildMembers;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FairChildMembersResponse {
    @SerializedName("data")
    @Expose
    private List<FairChildMembers> fairChildMembersList = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<FairChildMembers> getFairChildMembersList() {
        return fairChildMembersList;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}


