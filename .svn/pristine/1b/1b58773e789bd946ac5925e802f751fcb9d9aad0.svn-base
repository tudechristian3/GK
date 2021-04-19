package com.goodkredit.myapplication.responses.votes;

import com.goodkredit.myapplication.model.votes.VotesPending;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class VotesPendingResponse {

    @SerializedName("data")
    @Expose
    private List<VotesPending> votesPendingList = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<VotesPending> getVotesPendingList() {
        return votesPendingList;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
