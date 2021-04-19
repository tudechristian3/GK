package com.goodkredit.myapplication.responses;

import com.goodkredit.myapplication.bean.Sponsor;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Ban_Lenovo on 2/20/2017.
 */

public class GetMySponsorResponse {

    @SerializedName("data")
    @Expose
    private ArrayList<Sponsor> sponsor = new ArrayList<>();

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Sponsor> getSponsor() {
        return sponsor;
    }

    public void setSponsor(ArrayList<Sponsor> sponsor) {
        this.sponsor = sponsor;
    }
}
