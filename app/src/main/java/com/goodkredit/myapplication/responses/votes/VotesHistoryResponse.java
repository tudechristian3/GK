package com.goodkredit.myapplication.responses.votes;

import com.goodkredit.myapplication.model.votes.VotesHistory;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class VotesHistoryResponse {

    @SerializedName("data")
    @Expose
    private List<VotesHistory> votesHistoryList = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<VotesHistory> getVotesHistoryList() {
        return votesHistoryList;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
