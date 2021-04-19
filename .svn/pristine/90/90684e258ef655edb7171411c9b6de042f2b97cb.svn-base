package com.goodkredit.myapplication.model.votes;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VotesPostEvent implements Parcelable {
    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("DateTimeIN")
    @Expose
    private String DateTimeIN;
    @SerializedName("DateTimeArchived")
    @Expose
    private String DateTimeArchived;
    @SerializedName("MerchantID")
    @Expose
    private String MerchantID;
    @SerializedName("MerchantName")
    @Expose
    private String MerchantName;
    @SerializedName("EventID")
    @Expose
    private String EventID;
    @SerializedName("EventName")
    @Expose
    private String EventName;
    @SerializedName("EventDescription")
    @Expose
    private String EventDescription;
    @SerializedName("EventDateTimeStart")
    @Expose
    private String EventDateTimeStart;
    @SerializedName("EventDateTimeEnd")
    @Expose
    private String EventDateTimeEnd;
    @SerializedName("EventPictureURL")
    @Expose
    private String EventPictureURL;
    @SerializedName("XMLDetails")
    @Expose
    private String XMLDetails;
    @SerializedName("NoParticipants")
    @Expose
    private int NoParticipants;
    @SerializedName("TotalAmountCollected")
    @Expose
    private double TotalAmountCollected;
    @SerializedName("TotalVotes")
    @Expose
    private int TotalVotes;
    @SerializedName("AddedBy")
    @Expose
    private String AddedBy;
    @SerializedName("UpdateDateTime")
    @Expose
    private String UpdateDateTime;
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

    public VotesPostEvent(int ID, String dateTimeIN, String dateTimeArchived, String merchantID, String merchantName, String eventID, String eventName, String eventDescription, String eventDateTimeStart, String eventDateTimeEnd, String eventPictureURL, String XMLDetails, int noParticipants, double totalAmountCollected, int totalVotes, String addedBy, String updateDateTime, String status, String extra1, String extra2, String extra3, String extra4, String notes1, String notes2) {
        this.ID = ID;
        DateTimeIN = dateTimeIN;
        DateTimeArchived = dateTimeArchived;
        MerchantID = merchantID;
        MerchantName = merchantName;
        EventID = eventID;
        EventName = eventName;
        EventDescription = eventDescription;
        EventDateTimeStart = eventDateTimeStart;
        EventDateTimeEnd = eventDateTimeEnd;
        EventPictureURL = eventPictureURL;
        this.XMLDetails = XMLDetails;
        NoParticipants = noParticipants;
        TotalAmountCollected = totalAmountCollected;
        TotalVotes = totalVotes;
        AddedBy = addedBy;
        UpdateDateTime = updateDateTime;
        Status = status;
        Extra1 = extra1;
        Extra2 = extra2;
        Extra3 = extra3;
        Extra4 = extra4;
        Notes1 = notes1;
        Notes2 = notes2;
    }

    protected VotesPostEvent(Parcel in) {
        ID = in.readInt();
        DateTimeIN = in.readString();
        DateTimeArchived = in.readString();
        MerchantID = in.readString();
        MerchantName = in.readString();
        EventID = in.readString();
        EventName = in.readString();
        EventDescription = in.readString();
        EventDateTimeStart = in.readString();
        EventDateTimeEnd = in.readString();
        EventPictureURL = in.readString();
        XMLDetails = in.readString();
        NoParticipants = in.readInt();
        TotalAmountCollected = in.readDouble();
        TotalVotes = in.readInt();
        AddedBy = in.readString();
        UpdateDateTime = in.readString();
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
        dest.writeString(DateTimeArchived);
        dest.writeString(MerchantID);
        dest.writeString(MerchantName);
        dest.writeString(EventID);
        dest.writeString(EventName);
        dest.writeString(EventDescription);
        dest.writeString(EventDateTimeStart);
        dest.writeString(EventDateTimeEnd);
        dest.writeString(EventPictureURL);
        dest.writeString(XMLDetails);
        dest.writeInt(NoParticipants);
        dest.writeDouble(TotalAmountCollected);
        dest.writeInt(TotalVotes);
        dest.writeString(AddedBy);
        dest.writeString(UpdateDateTime);
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

    public static final Creator<VotesPostEvent> CREATOR = new Creator<VotesPostEvent>() {
        @Override
        public VotesPostEvent createFromParcel(Parcel in) {
            return new VotesPostEvent(in);
        }

        @Override
        public VotesPostEvent[] newArray(int size) {
            return new VotesPostEvent[size];
        }
    };

    public int getID() {
        return ID;
    }

    public String getDateTimeIN() {
        return DateTimeIN;
    }

    public String getDateTimeArchived() {
        return DateTimeArchived;
    }

    public String getMerchantID() {
        return MerchantID;
    }

    public String getMerchantName() {
        return MerchantName;
    }

    public String getEventID() {
        return EventID;
    }

    public String getEventName() {
        return EventName;
    }

    public String getEventDescription() {
        return EventDescription;
    }

    public String getEventDateTimeStart() {
        return EventDateTimeStart;
    }

    public String getEventDateTimeEnd() {
        return EventDateTimeEnd;
    }

    public String getEventPictureURL() {
        return EventPictureURL;
    }

    public String getXMLDetails() {
        return XMLDetails;
    }

    public int getNoParticipants() {
        return NoParticipants;
    }

    public double getTotalAmountCollected() {
        return TotalAmountCollected;
    }

    public int getTotalVotes() {
        return TotalVotes;
    }

    public String getAddedBy() {
        return AddedBy;
    }

    public String getUpdateDateTime() {
        return UpdateDateTime;
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
