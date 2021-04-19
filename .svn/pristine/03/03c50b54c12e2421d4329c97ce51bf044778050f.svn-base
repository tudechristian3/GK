package com.goodkredit.myapplication.model.freesms;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FreeSMSHistory implements Parcelable {

    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("DateTimeIN")
    @Expose
    private String DateTimeIN;
    @SerializedName("DateTimeCompleted")
    @Expose
    private String DateTimeCompleted;
    @SerializedName("TemplateType")
    @Expose
    private String TemplateType;
    @SerializedName("TemplateID")
    @Expose
    private String TemplateID;
    @SerializedName("Recipient")
    @Expose
    private String Recipient;
    @SerializedName("XMLDetails")
    @Expose
    private String XMLDetails;
    @SerializedName("Sender")
    @Expose
    private String Sender;
    @SerializedName("Message")
    @Expose
    private String Message;
    @SerializedName("SendContent")
    @Expose
    private String SendContent;
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

    public FreeSMSHistory(int ID, String dateTimeIN, String dateTimeCompleted, String templateType,
                          String templateID, String recipient, String XMLDetails, String sender,
                          String message, String sendContent, String status, String extra1, String extra2,
                          String extra3, String extra4, String notes1, String notes2) {
        this.ID = ID;
        DateTimeIN = dateTimeIN;
        DateTimeCompleted = dateTimeCompleted;
        TemplateType = templateType;
        TemplateID = templateID;
        Recipient = recipient;
        this.XMLDetails = XMLDetails;
        Sender = sender;
        Message = message;
        SendContent = sendContent;
        Status = status;
        Extra1 = extra1;
        Extra2 = extra2;
        Extra3 = extra3;
        Extra4 = extra4;
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

    public String getTemplateType() {
        return TemplateType;
    }

    public String getTemplateID() {
        return TemplateID;
    }

    public String getRecipient() {
        return Recipient;
    }

    public String getXMLDetails() {
        return XMLDetails;
    }

    public String getSender() {
        return Sender;
    }

    public String getMessage() {
        return Message;
    }

    public String getSendContent() {
        return SendContent;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ID);
        dest.writeString(this.DateTimeIN);
        dest.writeString(this.DateTimeCompleted);
        dest.writeString(this.TemplateType);
        dest.writeString(this.TemplateID);
        dest.writeString(this.Recipient);
        dest.writeString(this.XMLDetails);
        dest.writeString(this.Sender);
        dest.writeString(this.Message);
        dest.writeString(this.SendContent);
        dest.writeString(this.Status);
        dest.writeString(this.Extra1);
        dest.writeString(this.Extra2);
        dest.writeString(this.Extra3);
        dest.writeString(this.Extra4);
        dest.writeString(this.Notes1);
        dest.writeString(this.Notes2);
    }

    protected FreeSMSHistory(Parcel in) {
        this.ID = in.readInt();
        this.DateTimeIN = in.readString();
        this.DateTimeCompleted = in.readString();
        this.TemplateType = in.readString();
        this.TemplateID = in.readString();
        this.Recipient = in.readString();
        this.XMLDetails = in.readString();
        this.Sender = in.readString();
        this.Message = in.readString();
        this.SendContent = in.readString();
        this.Status = in.readString();
        this.Extra1 = in.readString();
        this.Extra2 = in.readString();
        this.Extra3 = in.readString();
        this.Extra4 = in.readString();
        this.Notes1 = in.readString();
        this.Notes2 = in.readString();
    }

    public static final Creator<FreeSMSHistory> CREATOR = new Creator<FreeSMSHistory>() {
        @Override
        public FreeSMSHistory createFromParcel(Parcel source) {
            return new FreeSMSHistory(source);
        }

        @Override
        public FreeSMSHistory[] newArray(int size) {
            return new FreeSMSHistory[size];
        }
    };
}
