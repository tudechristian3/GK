package com.goodkredit.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SupportConversation implements Parcelable {
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


    public SupportConversation(String dateTimeIN, String threadID, String replySupportUserID, String replySupportName, String threadType, String message) {
        DateTimeIN = dateTimeIN;
        ThreadID = threadID;
        ReplySupportUserID = replySupportUserID;
        ReplySupportName = replySupportName;
        ThreadType = threadType;
        Message = message;
    }

    protected SupportConversation(Parcel in) {
        DateTimeIN = in.readString();
        ThreadID = in.readString();
        ReplySupportUserID = in.readString();
        ReplySupportName = in.readString();
        ThreadType = in.readString();
        Message = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(DateTimeIN);
        dest.writeString(ThreadID);
        dest.writeString(ReplySupportUserID);
        dest.writeString(ReplySupportName);
        dest.writeString(ThreadType);
        dest.writeString(Message);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SupportConversation> CREATOR = new Creator<SupportConversation>() {
        @Override
        public SupportConversation createFromParcel(Parcel in) {
            return new SupportConversation(in);
        }

        @Override
        public SupportConversation[] newArray(int size) {
            return new SupportConversation[size];
        }
    };

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
