package com.goodkredit.myapplication.responses.skycable;

import com.goodkredit.myapplication.bean.SkycableRegistration;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class GetSkycableNewApplicationRegistrationsResponse {
    @SerializedName("data")
    @Expose
    private List<SkycableRegistration> skycableRegistrations = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<SkycableRegistration> getSkycableRegistrations() {
        return skycableRegistrations;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
