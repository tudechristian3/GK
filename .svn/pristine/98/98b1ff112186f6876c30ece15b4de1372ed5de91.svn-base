package com.goodkredit.myapplication.responses.projectcoop;

import com.goodkredit.myapplication.bean.projectcoop.GameResult;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class GetGameP2SResultsResponse {
    @SerializedName("data")
    @Expose
    private List<GameResult> gameResultList = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<GameResult> getGameResultList() {
        return gameResultList;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
