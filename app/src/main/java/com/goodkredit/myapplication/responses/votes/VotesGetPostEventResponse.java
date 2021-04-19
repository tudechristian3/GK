package com.goodkredit.myapplication.responses.votes;

import com.goodkredit.myapplication.model.votes.VotesPostEvent;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class VotesGetPostEventResponse {
    @SerializedName("data")
    @Expose
    private List<VotesPostEvent> votesPostEventList = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<VotesPostEvent> getVotesPostEventList() {
        return votesPostEventList;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
