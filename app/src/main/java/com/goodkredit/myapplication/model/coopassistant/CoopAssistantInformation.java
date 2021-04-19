package com.goodkredit.myapplication.model.coopassistant;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoopAssistantInformation implements Parcelable {

    public static final String KEY_COOPPMES = "COOP_PMES";
    public static final String KEY_COOPTERMS = "COOP_TERMSANDCONDITIONS";
    public static final String KEY_COOPINFORMATION = "CoopInformation";

    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("CoopID")
    @Expose
    private String CoopID;
    @SerializedName("CoopName")
    @Expose
    private String CoopName;
    @SerializedName("StreetAddress")
    @Expose
    private String StreetAddress;
    @SerializedName("City")
    @Expose
    private String City;
    @SerializedName("Province")
    @Expose
    private String Province;
    @SerializedName("Country")
    @Expose
    private String Country;
    @SerializedName("AuthorizedPersonel")
    @Expose
    private String AuthorizedPersonel;
    @SerializedName("AuthorizedMobileNumber")
    @Expose
    private String AuthorizedMobileNumber;
    @SerializedName("AuthorizedEmailAddress")
    @Expose
    private String AuthorizedEmailAddress;
    @SerializedName("Logo")
    @Expose
    private String Logo;
    @SerializedName("AboutUs")
    @Expose
    private String AboutUs;
    @SerializedName("TermsAndConditions")
    @Expose
    private String TermsAndConditions;
    @SerializedName("PMES")
    @Expose
    private String PMES;
    @SerializedName("XMLDetails")
    @Expose
    private String XMLDetails;
    @SerializedName("DateTimeAdded")
    @Expose
    private String DateTimeAdded;
    @SerializedName("AddedBy")
    @Expose
    private String AddedBy;
    @SerializedName("LastDateTimeUpdated")
    @Expose
    private String LastDateTimeUpdated;
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

    public CoopAssistantInformation(int ID, String coopID, String coopName, String streetAddress, String city, String province,
                                    String country, String authorizedPersonel, String authorizedMobileNumber,
                                    String authorizedEmailAddress, String logo, String aboutUs, String termsAndConditions,
                                    String pmes, String XMLDetails, String dateTimeAdded, String addedBy, String lastDateTimeUpdated,
                                    String status, String extra1, String extra2, String extra3, String extra4, String notes1,
                                    String notes2) {
        this.ID = ID;
        CoopID = coopID;
        CoopName = coopName;
        StreetAddress = streetAddress;
        City = city;
        Province = province;
        Country = country;
        AuthorizedPersonel = authorizedPersonel;
        AuthorizedMobileNumber = authorizedMobileNumber;
        AuthorizedEmailAddress = authorizedEmailAddress;
        Logo = logo;
        AboutUs = aboutUs;
        TermsAndConditions = termsAndConditions;
        PMES = pmes;
        this.XMLDetails = XMLDetails;
        DateTimeAdded = dateTimeAdded;
        AddedBy = addedBy;
        LastDateTimeUpdated = lastDateTimeUpdated;
        Status = status;
        Extra1 = extra1;
        Extra2 = extra2;
        Extra3 = extra3;
        Extra4 = extra4;
        Notes1 = notes1;
        Notes2 = notes2;
    }

    protected CoopAssistantInformation(Parcel in) {
        ID = in.readInt();
        CoopID = in.readString();
        CoopName = in.readString();
        StreetAddress = in.readString();
        City = in.readString();
        Province = in.readString();
        Country = in.readString();
        AuthorizedPersonel = in.readString();
        AuthorizedMobileNumber = in.readString();
        AuthorizedEmailAddress = in.readString();
        Logo = in.readString();
        AboutUs = in.readString();
        TermsAndConditions = in.readString();
        PMES = in.readString();
        XMLDetails = in.readString();
        DateTimeAdded = in.readString();
        AddedBy = in.readString();
        LastDateTimeUpdated = in.readString();
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
        dest.writeString(CoopName);
        dest.writeString(StreetAddress);
        dest.writeString(City);
        dest.writeString(Province);
        dest.writeString(Country);
        dest.writeString(AuthorizedPersonel);
        dest.writeString(AuthorizedMobileNumber);
        dest.writeString(AuthorizedEmailAddress);
        dest.writeString(Logo);
        dest.writeString(AboutUs);
        dest.writeString(TermsAndConditions);
        dest.writeString(PMES);
        dest.writeString(XMLDetails);
        dest.writeString(DateTimeAdded);
        dest.writeString(AddedBy);
        dest.writeString(LastDateTimeUpdated);
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

    public static final Creator<CoopAssistantInformation> CREATOR = new Creator<CoopAssistantInformation>() {
        @Override
        public CoopAssistantInformation createFromParcel(Parcel in) {
            return new CoopAssistantInformation(in);
        }

        @Override
        public CoopAssistantInformation[] newArray(int size) {
            return new CoopAssistantInformation[size];
        }
    };

    public int getID() {
        return ID;
    }

    public String getCoopID() {
        return CoopID;
    }

    public String getCoopName() {
        return CoopName;
    }

    public String getStreetAddress() {
        return StreetAddress;
    }

    public String getCity() {
        return City;
    }

    public String getProvince() {
        return Province;
    }

    public String getCountry() {
        return Country;
    }

    public String getAuthorizedPersonel() {
        return AuthorizedPersonel;
    }

    public String getAuthorizedMobileNumber() {
        return AuthorizedMobileNumber;
    }

    public String getAuthorizedEmailAddress() {
        return AuthorizedEmailAddress;
    }

    public String getLogo() {
        return Logo;
    }

    public String getAboutUs() {
        return AboutUs;
    }

    public String getTermsAndConditions() {
        return TermsAndConditions;
    }

    public String getPMES() {
        return PMES;
    }

    public String getXMLDetails() {
        return XMLDetails;
    }

    public String getDateTimeAdded() {
        return DateTimeAdded;
    }

    public String getAddedBy() {
        return AddedBy;
    }

    public String getLastDateTimeUpdated() {
        return LastDateTimeUpdated;
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
