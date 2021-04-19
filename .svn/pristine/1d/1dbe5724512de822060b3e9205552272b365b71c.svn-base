package com.goodkredit.myapplication.model.votes;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VotesParticipants implements Parcelable {

    public static final String KEY_VOTESPARTICIPANTS = "key_votesparticipants";

    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("DateTimeIN")
    @Expose
    private String DateTimeIN;
    @SerializedName("EventID")
    @Expose
    private String EventID;
    @SerializedName("ParticipantID")
    @Expose
    private String ParticipantID;
    @SerializedName("FirstName")
    @Expose
    private String FirstName;
    @SerializedName("MiddleName")
    @Expose
    private String MiddleName;
    @SerializedName("LastName")
    @Expose
    private String LastName;
    @SerializedName("Age")
    @Expose
    private int Age;
    @SerializedName("Gender")
    @Expose
    private String Gender;
    @SerializedName("Address")
    @Expose
    private String Address;
    @SerializedName("OtherInfo")
    @Expose
    private String OtherInfo;
    @SerializedName("ParticipantNumber")
    @Expose
    private int ParticipantNumber;
    @SerializedName("CurrentNoVote")
    @Expose
    private int CurrentNoVote;
    @SerializedName("LastDateTimeVoted")
    @Expose
    private String LastDateTimeVoted;
    @SerializedName("AddedBy")
    @Expose
    private String AddedBy;
    @SerializedName("ProfilePictureURL")
    @Expose
    private String ProfilePictureURL;
    @SerializedName("ImageXML")
    @Expose
    private String ImageXML;
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

    public VotesParticipants(int ID, String dateTimeIN, String eventID, String participantID, String firstName, String middleName, String lastName, int age, String gender, String address, String otherInfo, int participantNumber, int currentNoVote, String lastDateTimeVoted, String addedBy, String profilePictureURL, String imageXML, String status, String extra1, String extra2, String extra3, String extra4, String notes1, String notes2) {
        this.ID = ID;
        DateTimeIN = dateTimeIN;
        EventID = eventID;
        ParticipantID = participantID;
        FirstName = firstName;
        MiddleName = middleName;
        LastName = lastName;
        Age = age;
        Gender = gender;
        Address = address;
        OtherInfo = otherInfo;
        ParticipantNumber = participantNumber;
        CurrentNoVote = currentNoVote;
        LastDateTimeVoted = lastDateTimeVoted;
        AddedBy = addedBy;
        ProfilePictureURL = profilePictureURL;
        ImageXML = imageXML;
        Status = status;
        Extra1 = extra1;
        Extra2 = extra2;
        Extra3 = extra3;
        Extra4 = extra4;
        Notes1 = notes1;
        Notes2 = notes2;
    }

    protected VotesParticipants(Parcel in) {
        ID = in.readInt();
        DateTimeIN = in.readString();
        EventID = in.readString();
        ParticipantID = in.readString();
        FirstName = in.readString();
        MiddleName = in.readString();
        LastName = in.readString();
        Age = in.readInt();
        Gender = in.readString();
        Address = in.readString();
        OtherInfo = in.readString();
        ParticipantNumber = in.readInt();
        CurrentNoVote = in.readInt();
        LastDateTimeVoted = in.readString();
        AddedBy = in.readString();
        ProfilePictureURL = in.readString();
        ImageXML = in.readString();
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
        dest.writeString(EventID);
        dest.writeString(ParticipantID);
        dest.writeString(FirstName);
        dest.writeString(MiddleName);
        dest.writeString(LastName);
        dest.writeInt(Age);
        dest.writeString(Gender);
        dest.writeString(Address);
        dest.writeString(OtherInfo);
        dest.writeInt(ParticipantNumber);
        dest.writeInt(CurrentNoVote);
        dest.writeString(LastDateTimeVoted);
        dest.writeString(AddedBy);
        dest.writeString(ProfilePictureURL);
        dest.writeString(ImageXML);
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

    public static final Creator<VotesParticipants> CREATOR = new Creator<VotesParticipants>() {
        @Override
        public VotesParticipants createFromParcel(Parcel in) {
            return new VotesParticipants(in);
        }

        @Override
        public VotesParticipants[] newArray(int size) {
            return new VotesParticipants[size];
        }
    };

    public int getID() {
        return ID;
    }

    public String getDateTimeIN() {
        return DateTimeIN;
    }

    public String getEventID() {
        return EventID;
    }

    public String getParticipantID() {
        return ParticipantID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public String getLastName() {
        return LastName;
    }

    public int getAge() {
        return Age;
    }

    public String getGender() {
        return Gender;
    }

    public String getAddress() {
        return Address;
    }

    public String getOtherInfo() {
        return OtherInfo;
    }

    public int getParticipantNumber() {
        return ParticipantNumber;
    }

    public int getCurrentNoVote() {
        return CurrentNoVote;
    }

    public String getLastDateTimeVoted() {
        return LastDateTimeVoted;
    }

    public String getAddedBy() {
        return AddedBy;
    }

    public String getProfilePictureURL() {
        return ProfilePictureURL;
    }

    public String getImageXML() {
        return ImageXML;
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
