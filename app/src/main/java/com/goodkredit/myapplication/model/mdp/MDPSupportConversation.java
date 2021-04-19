package com.goodkredit.myapplication.model.mdp;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MDPSupportConversation implements Parcelable {

    @SerializedName("ID")
    @Expose
    private int ID;
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
    @SerializedName("Extra1")
    @Expose
    private String Extra1;
    @SerializedName("Extra2")
    @Expose
    private String Extra2;
    @SerializedName("Extra3")
    @Expose
    private String Extra3;
    @SerializedName("Notes1")
    @Expose
    private String Notes1;
    @SerializedName("Notes2")
    @Expose
    private String Notes2;

    public MDPSupportConversation(int ID, String dateTimeIN, String threadID, String replySupportUserID,
                                  String replySupportName, String threadType, String message, String extra1,
                                  String extra2, String extra3, String notes1, String notes2) {
        this.ID = ID;
        DateTimeIN = dateTimeIN;
        ThreadID = threadID;
        ReplySupportUserID = replySupportUserID;
        ReplySupportName = replySupportName;
        ThreadType = threadType;
        Message = message;
        Extra1 = extra1;
        Extra2 = extra2;
        Extra3 = extra3;
        Notes1 = notes1;
        Notes2 = notes2;
    }

    public int getID() {
        return ID;
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

    public String getExtra1() {
        return Extra1;
    }

    public String getExtra2() {
        return Extra2;
    }

    public String getExtra3() {
        return Extra3;
    }

    public String getNotes1() {
        return Notes1;
    }

    public String getNotes2() {
        return Notes2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ID);
        dest.writeString(this.DateTimeIN);
        dest.writeString(this.ThreadID);
        dest.writeString(this.ReplySupportUserID);
        dest.writeString(this.ReplySupportName);
        dest.writeString(this.ThreadType);
        dest.writeString(this.Message);
        dest.writeString(this.Extra1);
        dest.writeString(this.Extra2);
        dest.writeString(this.Extra3);
        dest.writeString(this.Notes1);
        dest.writeString(this.Notes2);
    }

    protected MDPSupportConversation(Parcel in) {
        this.ID = in.readInt();
        this.DateTimeIN = in.readString();
        this.ThreadID = in.readString();
        this.ReplySupportUserID = in.readString();
        this.ReplySupportName = in.readString();
        this.ThreadType = in.readString();
        this.Message = in.readString();
        this.Extra1 = in.readString();
        this.Extra2 = in.readString();
        this.Extra3 = in.readString();
        this.Notes1 = in.readString();
        this.Notes2 = in.readString();
    }

    public static final Parcelable.Creator<MDPSupportConversation> CREATOR = new Parcelable.Creator<MDPSupportConversation>() {
        @Override
        public MDPSupportConversation createFromParcel(Parcel source) {
            return new MDPSupportConversation(source);
        }

        @Override
        public MDPSupportConversation[] newArray(int size) {
            return new MDPSupportConversation[size];
        }
    };
}
