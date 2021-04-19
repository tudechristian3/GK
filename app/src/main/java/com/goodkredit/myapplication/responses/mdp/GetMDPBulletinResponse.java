package com.goodkredit.myapplication.responses.mdp;

import com.goodkredit.myapplication.model.mdp.MDPBulletin;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetMDPBulletinResponse {

    @SerializedName("data")
    @Expose
    private List<MDPBulletin> MDPBulletinList = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<MDPBulletin> getMDPBulletinList() {
        return MDPBulletinList;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}
