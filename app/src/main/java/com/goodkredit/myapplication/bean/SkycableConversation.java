package com.goodkredit.myapplication.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SkycableConversation {
    @SerializedName("DateTimeIN")
    @Expose
    private String DateTimeIN;
    @SerializedName("ThreadID")
    @Expose
    private String ThreadID;
    @SerializedName("ReplySupportUserID")
    @Expose
    private String ReplySupportUserID;
    @SerializedName("ReplySupportName")
    @Expose
    private String ReplySupportName;
    @SerializedName("ThreadType")
    @Expose
    private String ThreadType;
    @SerializedName("Message")
    @Expose
    private String Message;

    public SkycableConversation(String dateTimeIN, String threadID, String replySupportUserID, String replySupportName, String threadType, String message) {
        DateTimeIN = dateTimeIN;
        ThreadID = threadID;
        ReplySupportUserID = replySupportUserID;
        ReplySupportName = replySupportName;
        ThreadType = threadType;
        Message = message;
    }

    public String getDateTimeIN() {
        return DateTimeIN;
    }

    public String getThreadID() {
        return ThreadID;
    }

    public String getReplySupportUserID() {
        return ReplySupportUserID;
    }

    public String getReplySupportName() {
        return ReplySupportName;
    }

    public String getThreadType() {
        return ThreadType;
    }

    public String getMessage() {
        return Message;
    }
}
