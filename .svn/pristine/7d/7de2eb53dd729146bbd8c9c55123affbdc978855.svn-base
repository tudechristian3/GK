package com.goodkredit.myapplication.responses.votes;

import com.goodkredit.myapplication.model.votes.VotesParticipants;
import com.goodkredit.myapplication.model.votes.VotesPostEvent;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class VotesParticipantsResponse {
    @SerializedName("data")
    @Expose
    private List<VotesParticipants> votesParticipantsList = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<VotesParticipants> getVotesParticipantsList() {
        return votesParticipantsList;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
