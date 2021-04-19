package com.goodkredit.myapplication.responses.projectcoop;

import com.goodkredit.myapplication.bean.projectcoop.GameHistory;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class GetGamePreviousEntriesResponse {
    @SerializedName("data")
    @Expose
    private List<GameHistory> gameHistoryList = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<GameHistory> getGameHistoryList() {
        return gameHistoryList;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
