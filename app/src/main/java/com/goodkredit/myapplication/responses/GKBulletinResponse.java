package com.goodkredit.myapplication.responses;

import com.goodkredit.myapplication.model.GenericBulletin;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GKBulletinResponse {
    @SerializedName("data")
    @Expose
    private List<GenericBulletin> genericBulletinList = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<GenericBulletin> getGKBulletin() {
        return genericBulletinList;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
