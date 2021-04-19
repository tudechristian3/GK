package com.goodkredit.myapplication.responses.votes;

import com.goodkredit.myapplication.model.votes.VotesParticipants;
import com.goodkredit.myapplication.model.votes.VotesPayment;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class VotesPaymentResponse {
    @SerializedName("data")
    @Expose
    private VotesPayment votesPayment;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public VotesPayment getVotesPayment() {
        return votesPayment;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
