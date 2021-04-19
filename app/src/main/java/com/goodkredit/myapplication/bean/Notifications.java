package com.goodkredit.myapplication.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class Notifications {

    @SerializedName("Message")
    @Expose
    private String message;

    @SerializedName("NotificationStatus")
    @Expose
    private String notificationstatus;


    public String getMessage() {
        return message;
    }

    public String getNotificationstatus() {
        return notificationstatus;
    }

}
