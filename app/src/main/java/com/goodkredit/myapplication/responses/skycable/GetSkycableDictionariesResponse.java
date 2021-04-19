package com.goodkredit.myapplication.responses.skycable;

import com.goodkredit.myapplication.bean.SkycableDictionary;
import com.goodkredit.myapplication.bean.SkycableRegistration;
import com.goodkredit.myapplication.bean.SkycableServiceArea;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class GetSkycableDictionariesResponse {
    @SerializedName("data")
    @Expose
    private List<SkycableDictionary> skycableDictionaries = new ArrayList<>();
    @SerializedName("registrations")
    @Expose
    private List<SkycableRegistration> skycableRegistrations = new ArrayList<>();
    @SerializedName("location")
    @Expose
    private List<SkycableServiceArea> skycableServiceAreas = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<SkycableDictionary> getSkycableDictionaries() {
        return skycableDictionaries;
    }

    public List<SkycableRegistration> getSkycableRegistrations() {
        return skycableRegistrations;
    }

    public List<SkycableServiceArea> getSkycableServiceAreas() {
        return skycableServiceAreas;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
