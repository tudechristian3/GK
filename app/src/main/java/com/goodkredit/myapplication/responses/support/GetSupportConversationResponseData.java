package com.goodkredit.myapplication.responses.support;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ban_Lenovo on 3/15/2017.
 */

public class GetSupportConversationResponseData {

    @SerializedName("ID")
    @Expose
    private String ID;
    @SerializedName("DateTimeIN")
    @Expose
    private String DateTimeIn;
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

    public GetSupportConversationResponseData(String ID, String dateTimeIn, String threadID, String replySupportUserID, String replySupportName, String threadType, String message) {
        this.ID = ID;
        DateTimeIn = dateTimeIn;
        ThreadID = threadID;
        ReplySupportUserID = replySupportUserID;
        ReplySupportName = replySupportName;
        ThreadType = threadType;
        Message = message;
    }

    public GetSupportConversationResponseData() {

    }

    public String getID() {
        return ID;
    }

    public String getDateTimeIn() {
        return DateTimeIn;
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

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setDateTimeIn(String dateTimeIn) {
        DateTimeIn = dateTimeIn;
    }

    public void setThreadID(String threadID) {
        ThreadID = threadID;
    }

    public void setReplySupportUserID(String replySupportUserID) {
        ReplySupportUserID = replySupportUserID;
    }

    public void setReplySupportName(String replySupportName) {
        ReplySupportName = replySupportName;
    }

    public void setThreadType(String threadType) {
        ThreadType = threadType;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
