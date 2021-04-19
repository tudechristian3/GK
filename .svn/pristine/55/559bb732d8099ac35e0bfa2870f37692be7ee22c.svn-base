package com.goodkredit.myapplication.responses.projectcoop;

import com.goodkredit.myapplication.bean.projectcoop.GameCutoverDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class GetGameP2SCutoverDetailsResponse {
    @SerializedName("data")
    @Expose
    private List<GameCutoverDetails> gameCutoverDetailsList = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<GameCutoverDetails> getGameCutoverDetailsList() {
        return gameCutoverDetailsList;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
