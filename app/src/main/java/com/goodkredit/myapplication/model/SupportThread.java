package com.goodkredit.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SupportThread implements Parcelable {
    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("DateTimeIN")
    @Expose
    private String DateTimeIN;
    @SerializedName("DateTimeCompleted")
    @Expose
    private String DateTimeCompleted;
    @SerializedName("ThreadID")
    @Expose
    private String ThreadID;
    @SerializedName("HelpTopic")
    @Expose
    private String HelpTopic;
    @SerializedName("Subject")
    @Expose
    private String Subject;
    @SerializedName("SupportUserID")
    @Expose
    private String SupportUserID;
    @SerializedName("MobileNo")
    @Expose
    private String MobileNo;
    @SerializedName("GKBorrowerID")
    @Expose
    private String GKBorrowerID;
    @SerializedName("GKBorrowerName")
    @Expose
    private String GKBorrowerName;
    @SerializedName("GKIMEI")
    @Expose
    private String GKIMEI;
    @SerializedName("GKEmailAddress")
    @Expose
    private String GKEmailAddress;
    @SerializedName("SubscriberNotificationStatus")
    @Expose
    private String SubscriberNotificationStatus;
    @SerializedName("SupportNotificationStatus")
    @Expose
    private String SupportNotificationStatus;
    @SerializedName("Status")
    @Expose
    private String Status;
    @SerializedName("Extra1")
    @Expose
    private String Extra1;
    @SerializedName("Extra2")
    @Expose
    private String Extra2;
    @SerializedName("Extra3")
    @Expose
    private String Extra3;
    @SerializedName("Extra4")
    @Expose
    private String Extra4;
    @SerializedName("Notes1")
    @Expose
    private String Notes1;
    @SerializedName("Notes2")
    @Expose
    private String Notes2;

    public SupportThread(int ID, String dateTimeIN, String dateTimeCompleted, String threadID, String helpTopic, String subject, String supportUserID, String mobileNo, String GKBorrowerID, String GKBorrowerName, String GKIMEI, String GKEmailAddress, String subscriberNotificationStatus, String supportNotificationStatus, String status, String extra1, String extra2, String extra3, String extra4, String notes1, String notes2) {
        this.ID = ID;
        DateTimeIN = dateTimeIN;
        DateTimeCompleted = dateTimeCompleted;
        ThreadID = threadID;
        HelpTopic = helpTopic;
        Subject = subject;
        SupportUserID = supportUserID;
        MobileNo = mobileNo;
        this.GKBorrowerID = GKBorrowerID;
        this.GKBorrowerName = GKBorrowerName;
        this.GKIMEI = GKIMEI;
        this.GKEmailAddress = GKEmailAddress;
        SubscriberNotificationStatus = subscriberNotificationStatus;
        SupportNotificationStatus = supportNotificationStatus;
        Status = status;
        Extra1 = extra1;
        Extra2 = extra2;
        Extra3 = extra3;
        Extra4 = extra4;
        Notes1 = notes1;
        Notes2 = notes2;
    }

    protected SupportThread(Parcel in) {
        ID = in.readInt();
        DateTimeIN = in.readString();
        DateTimeCompleted = in.readString();
        ThreadID = in.readString();
        HelpTopic = in.readString();
        Subject = in.readString();
        SupportUserID = in.readString();
        MobileNo = in.readString();
        GKBorrowerID = in.readString();
        GKBorrowerName = in.readString();
        GKIMEI = in.readString();
        GKEmailAddress = in.readString();
        SubscriberNotificationStatus = in.readString();
        SupportNotificationStatus = in.readString();
        Status = in.readString();
        Extra1 = in.readString();
        Extra2 = in.readString();
        Extra3 = in.readString();
        Extra4 = in.readString();
        Notes1 = in.readString();
        Notes2 = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(DateTimeIN);
        dest.writeString(DateTimeCompleted);
        dest.writeString(ThreadID);
        dest.writeString(HelpTopic);
        dest.writeString(Subject);
        dest.writeString(SupportUserID);
        dest.writeString(MobileNo);
        dest.writeString(GKBorrowerID);
        dest.writeString(GKBorrowerName);
        dest.writeString(GKIMEI);
        dest.writeString(GKEmailAddress);
        dest.writeString(SubscriberNotificationStatus);
        dest.writeString(SupportNotificationStatus);
        dest.writeString(Status);
        dest.writeString(Extra1);
        dest.writeString(Extra2);
        dest.writeString(Extra3);
        dest.writeString(Extra4);
        dest.writeString(Notes1);
        dest.writeString(Notes2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SupportThread> CREATOR = new Creator<SupportThread>() {
        @Override
        public SupportThread createFromParcel(Parcel in) {
            return new SupportThread(in);
        }

        @Override
        public SupportThread[] newArray(int size) {
            return new SupportThread[size];
        }
    };

    public int getID() {
        return ID;
    }

    public String getDateTimeIN() {
        return DateTimeIN;
    }

    public String getDateTimeCompleted() {
        return DateTimeCompleted;
    }

    public String getThreadID() {
        return ThreadID;
    }

    public String getHelpTopic() {
        return HelpTopic;
    }

    public String getSubject() {
        return Subject;
    }

    public String getSupportUserID() {
        return SupportUserID;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public String getGKBorrowerID() {
        return GKBorrowerID;
    }

    public String getGKBorrowerName() {
        return GKBorrowerName;
    }

    public String getGKIMEI() {
        return GKIMEI;
    }

    public String getGKEmailAddress() {
        return GKEmailAddress;
    }

    public String getSubscriberNotificationStatus() {
        return SubscriberNotificationStatus;
    }

    public String getSupportNotificationStatus() {
        return SupportNotificationStatus;
    }

    public String getStatus() {
        return Status;
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

    public String getExtra4() {
        return Extra4;
    }

    public String getNotes1() {
        return Notes1;
    }

    public String getNotes2() {
        return Notes2;
    }
}
