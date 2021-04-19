package com.goodkredit.myapplication.model.coopassistant.member;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoopAssistantMembers implements Parcelable {
    
    public static final String KEY_COOPMEMBERINFORMATION = "CoopMemberInformation";
    
    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("CoopID")
    @Expose
    private String CoopID;
    @SerializedName("BorrowerID")
    @Expose
    private String BorrowerID;
    @SerializedName("MemberID")
    @Expose
    private String MemberID;
    @SerializedName("MobileNumber")
    @Expose
    private String MobileNumber;
    @SerializedName("MembershipType")
    @Expose
    private String MembershipType;
    @SerializedName("FirstName")
    @Expose
    private String FirstName;
    @SerializedName("MiddleName")
    @Expose
    private String MiddleName;
    @SerializedName("LastName")
    @Expose
    private String LastName;
    @SerializedName("EmailAddress")
    @Expose
    private String EmailAddress;
    @SerializedName("Gender")
    @Expose
    private String Gender;
    @SerializedName("Birthdate")
    @Expose
    private String Birthdate;
    @SerializedName("CivilStatus")
    @Expose
    private String CivilStatus;
    @SerializedName("Nationality")
    @Expose
    private String Nationality;
    @SerializedName("HomeTownAddress")
    @Expose
    private String HomeTownAddress;
    @SerializedName("CurrentAddress")
    @Expose
    private String CurrentAddress;
    @SerializedName("Occupation")
    @Expose
    private String Occupation;
    @SerializedName("OtherDetails")
    @Expose
    private String OtherDetails;
    @SerializedName("ReferredBy")
    @Expose
    private String ReferredBy;
    @SerializedName("ReferredByName")
    @Expose
    private String ReferredByName;
    @SerializedName("DateTimeAdded")
    @Expose
    private String DateTimeAdded;
    @SerializedName("DateTimeUploaded")
    @Expose
    private String DateTimeUploaded;
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

    public CoopAssistantMembers(int ID, String coopID, String borrowerID, String memberID, String mobileNumber, String membershipType, String firstName, String middleName, String lastName, String emailAddress, String gender, String birthdate, String civilStatus, String nationality, String homeTownAddress, String currentAddress, String occupation, String otherDetails, String referredBy, String referredByName, String dateTimeAdded, String dateTimeUploaded, String status, String extra1, String extra2, String extra3, String extra4, String notes1, String notes2) {
        this.ID = ID;
        CoopID = coopID;
        BorrowerID = borrowerID;
        MemberID = memberID;
        MobileNumber = mobileNumber;
        MembershipType = membershipType;
        FirstName = firstName;
        MiddleName = middleName;
        LastName = lastName;
        EmailAddress = emailAddress;
        Gender = gender;
        Birthdate = birthdate;
        CivilStatus = civilStatus;
        Nationality = nationality;
        HomeTownAddress = homeTownAddress;
        CurrentAddress = currentAddress;
        Occupation = occupation;
        OtherDetails = otherDetails;
        ReferredBy = referredBy;
        ReferredByName = referredByName;
        DateTimeAdded = dateTimeAdded;
        DateTimeUploaded = dateTimeUploaded;
        Status = status;
        Extra1 = extra1;
        Extra2 = extra2;
        Extra3 = extra3;
        Extra4 = extra4;
        Notes1 = notes1;
        Notes2 = notes2;
    }

    protected CoopAssistantMembers(Parcel in) {
        ID = in.readInt();
        CoopID = in.readString();
        BorrowerID = in.readString();
        MemberID = in.readString();
        MobileNumber = in.readString();
        MembershipType = in.readString();
        FirstName = in.readString();
        MiddleName = in.readString();
        LastName = in.readString();
        EmailAddress = in.readString();
        Gender = in.readString();
        Birthdate = in.readString();
        CivilStatus = in.readString();
        Nationality = in.readString();
        HomeTownAddress = in.readString();
        CurrentAddress = in.readString();
        Occupation = in.readString();
        OtherDetails = in.readString();
        ReferredBy = in.readString();
        ReferredByName = in.readString();
        DateTimeAdded = in.readString();
        DateTimeUploaded = in.readString();
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
        dest.writeString(CoopID);
        dest.writeString(BorrowerID);
        dest.writeString(MemberID);
        dest.writeString(MobileNumber);
        dest.writeString(MembershipType);
        dest.writeString(FirstName);
        dest.writeString(MiddleName);
        dest.writeString(LastName);
        dest.writeString(EmailAddress);
        dest.writeString(Gender);
        dest.writeString(Birthdate);
        dest.writeString(CivilStatus);
        dest.writeString(Nationality);
        dest.writeString(HomeTownAddress);
        dest.writeString(CurrentAddress);
        dest.writeString(Occupation);
        dest.writeString(OtherDetails);
        dest.writeString(ReferredBy);
        dest.writeString(ReferredByName);
        dest.writeString(DateTimeAdded);
        dest.writeString(DateTimeUploaded);
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

    public static final Creator<CoopAssistantMembers> CREATOR = new Creator<CoopAssistantMembers>() {
        @Override
        public CoopAssistantMembers createFromParcel(Parcel in) {
            return new CoopAssistantMembers(in);
        }

        @Override
        public CoopAssistantMembers[] newArray(int size) {
            return new CoopAssistantMembers[size];
        }
    };

    public int getID() {
        return ID;
    }

    public String getCoopID() {
        return CoopID;
    }

    public String getBorrowerID() {
        return BorrowerID;
    }

    public String getMemberID() {
        return MemberID;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public String getMembershipType() {
        return MembershipType;
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

    public String getEmailAddress() {
        return EmailAddress;
    }

    public String getGender() {
        return Gender;
    }

    public String getBirthdate() {
        return Birthdate;
    }

    public String getCivilStatus() {
        return CivilStatus;
    }

    public String getNationality() {
        return Nationality;
    }

    public String getHomeTownAddress() {
        return HomeTownAddress;
    }

    public String getCurrentAddress() {
        return CurrentAddress;
    }

    public String getOccupation() {
        return Occupation;
    }

    public String getOtherDetails() {
        return OtherDetails;
    }

    public String getReferredBy() {
        return ReferredBy;
    }

    public String getReferredByName() {
        return ReferredByName;
    }

    public String getDateTimeAdded() {
        return DateTimeAdded;
    }

    public String getDateTimeUploaded() {
        return DateTimeUploaded;
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
