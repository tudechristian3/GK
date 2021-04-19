package com.goodkredit.myapplication.model.mdp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MDPSupportThread implements Parcelable {

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
    @SerializedName("Notes1")
    @Expose
    private String Notes1;
    @SerializedName("Notes2")
    @Expose
    private String Notes2;

    public MDPSupportThread(int ID, String dateTimeIN, String dateTimeCompleted, String threadID,
                            String helpTopic, String subject, String supportUserID, String mobileNo,
                            String GKBorrowerID, String GKBorrowerName, String GKIMEI, String GKEmailAddress,
                            String subscriberNotificationStatus, String supportNotificationStatus,
                            String status, String extra1, String extra2, String extra3, String notes1,
                            String notes2) {
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
        Notes1 = notes1;
        Notes2 = notes2;
    }

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
        dest.writeString(this.DateTimeCompleted);
        dest.writeString(this.ThreadID);
        dest.writeString(this.HelpTopic);
        dest.writeString(this.Subject);
        dest.writeString(this.SupportUserID);
        dest.writeString(this.MobileNo);
        dest.writeString(this.GKBorrowerID);
        dest.writeString(this.GKBorrowerName);
        dest.writeString(this.GKIMEI);
        dest.writeString(this.GKEmailAddress);
        dest.writeString(this.SubscriberNotificationStatus);
        dest.writeString(this.SupportNotificationStatus);
        dest.writeString(this.Status);
        dest.writeString(this.Extra1);
        dest.writeString(this.Extra2);
        dest.writeString(this.Extra3);
        dest.writeString(this.Notes1);
        dest.writeString(this.Notes2);
    }

    protected MDPSupportThread(Parcel in) {
        this.ID = in.readInt();
        this.DateTimeIN = in.readString();
        this.DateTimeCompleted = in.readString();
        this.ThreadID = in.readString();
        this.HelpTopic = in.readString();
        this.Subject = in.readString();
        this.SupportUserID = in.readString();
        this.MobileNo = in.readString();
        this.GKBorrowerID = in.readString();
        this.GKBorrowerName = in.readString();
        this.GKIMEI = in.readString();
        this.GKEmailAddress = in.readString();
        this.SubscriberNotificationStatus = in.readString();
        this.SupportNotificationStatus = in.readString();
        this.Status = in.readString();
        this.Extra1 = in.readString();
        this.Extra2 = in.readString();
        this.Extra3 = in.readString();
        this.Notes1 = in.readString();
        this.Notes2 = in.readString();
    }

    public static final Parcelable.Creator<MDPSupportThread> CREATOR = new Parcelable.Creator<MDPSupportThread>() {
        @Override
        public MDPSupportThread createFromParcel(Parcel source) {
            return new MDPSupportThread(source);
        }

        @Override
        public MDPSupportThread[] newArray(int size) {
            return new MDPSupportThread[size];
        }
    };
}
