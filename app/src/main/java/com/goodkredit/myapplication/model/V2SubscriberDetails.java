package com.goodkredit.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ban on 09/03/2018.
 */

public class V2SubscriberDetails implements Parcelable {

    public static final String KEY_V2SUBSCRIBERDETAILS = "key_v2subscriberdetails";

    @SerializedName("BorrowerName")
    @Expose
    private String BorrowerName;
    @SerializedName("MobileNo")
    @Expose
    private String MobileNo;
    @SerializedName("EmailAddress")
    @Expose
    private String EmailAddress;
    @SerializedName("StreetAddress")
    @Expose
    private String StreetAddress;
    @SerializedName("City")
    @Expose
    private String City;
    @SerializedName("Country")
    @Expose
    private String Country;
    @SerializedName("GuarantorID")
    @Expose
    private String GuarantorID;
    @SerializedName("ProfilePicURL")
    @Expose
    private String ProfilePicURL;
    @SerializedName("FirstName")
    @Expose
    private String FirstName;
    @SerializedName("LastName")
    @Expose
    private String LastName;
    @SerializedName("MiddleName")
    @Expose
    private String MiddleName;
    @SerializedName("Gender")
    @Expose
    private String Gender;
    @SerializedName("ResellerID")
    @Expose
    private String ResellerID;
    @SerializedName("DistributorID")
    @Expose
    private String DistributorID;
    @SerializedName("SyncContact")
    @Expose
    private String SyncContact;
    @SerializedName("Extra1")
    @Expose
    private String Extra1;
    @SerializedName("Notes1")
    @Expose
    private String Notes1;

    public V2SubscriberDetails(String borrowerName, String mobileNo, String emailAddress, String streetAddress, String city, String country, String guarantorID, String profilePicURL, String firstName, String lastName, String middleName, String gender, String syncContact, String resellerID, String distributorID, String extra1, String notes1) {
        BorrowerName = borrowerName;
        MobileNo = mobileNo;
        EmailAddress = emailAddress;
        StreetAddress = streetAddress;
        City = city;
        Country = country;
        GuarantorID = guarantorID;
        ProfilePicURL = profilePicURL;
        FirstName = firstName;
        LastName = lastName;
        MiddleName = middleName;
        Gender = gender;
        SyncContact = syncContact;
        ResellerID = resellerID;
        DistributorID = distributorID;
        Extra1 = extra1;
        Notes1 = notes1;
    }

    protected V2SubscriberDetails(Parcel in) {
        BorrowerName = in.readString();
        MobileNo = in.readString();
        EmailAddress = in.readString();
        StreetAddress = in.readString();
        City = in.readString();
        Country = in.readString();
        GuarantorID = in.readString();
        ProfilePicURL = in.readString();
        FirstName = in.readString();
        LastName = in.readString();
        MiddleName = in.readString();
        Gender = in.readString();
        ResellerID = in.readString();
        DistributorID = in.readString();
        SyncContact = in.readString();
        Extra1 = in.readString();
        Notes1 = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(BorrowerName);
        dest.writeString(MobileNo);
        dest.writeString(EmailAddress);
        dest.writeString(StreetAddress);
        dest.writeString(City);
        dest.writeString(Country);
        dest.writeString(GuarantorID);
        dest.writeString(ProfilePicURL);
        dest.writeString(FirstName);
        dest.writeString(LastName);
        dest.writeString(MiddleName);
        dest.writeString(Gender);
        dest.writeString(ResellerID);
        dest.writeString(DistributorID);
        dest.writeString(SyncContact);
        dest.writeString(Extra1);
        dest.writeString(Notes1);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<V2SubscriberDetails> CREATOR = new Creator<V2SubscriberDetails>() {
        @Override
        public V2SubscriberDetails createFromParcel(Parcel in) {
            return new V2SubscriberDetails(in);
        }

        @Override
        public V2SubscriberDetails[] newArray(int size) {
            return new V2SubscriberDetails[size];
        }
    };

    public String getBorrowerName() {
        return BorrowerName;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public String getStreetAddress() {
        return StreetAddress;
    }

    public String getCity() {
        return City;
    }

    public String getCountry() {
        return Country;
    }

    public String getGuarantorID() {
        return GuarantorID;
    }

    public String getProfilePicURL() {
        return ProfilePicURL;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public String getGender() {
        return Gender;
    }

    public String getResellerID() {
        return ResellerID;
    }

    public String getDistributorID() {
        return DistributorID;
    }

    public String getSyncContact() {
        return SyncContact;
    }

    public String getGuarantorApprovalStatus() {
        return Extra1;
    }

    public String getNotes1() {
        return Notes1;
    }


}
