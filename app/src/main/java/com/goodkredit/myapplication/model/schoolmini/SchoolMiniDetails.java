package com.goodkredit.myapplication.model.schoolmini;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SchoolMiniDetails implements Parcelable {
    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("SchoolID")
    @Expose
    private String SchoolID;
    @SerializedName("SchoolName")
    @Expose
    private String SchoolName;
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
    @SerializedName("SchoolLogo")
    @Expose
    private String SchoolLogo;
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

    public SchoolMiniDetails(int ID, String schoolID, String schoolName, String streetAddress, String city, String province, String country, String authorizedPersonel, String authorizedMobileNumber, String authorizedEmailAddress, String schoolLogo, String XMLDetails, String dateTimeAdded, String addedBy, String lastDateTimeUpdated, String status, String extra1, String extra2, String extra3, String extra4, String notes1, String notes2) {
        this.ID = ID;
        SchoolID = schoolID;
        SchoolName = schoolName;
        StreetAddress = streetAddress;
        City = city;
        Province = province;
        Country = country;
        AuthorizedPersonel = authorizedPersonel;
        AuthorizedMobileNumber = authorizedMobileNumber;
        AuthorizedEmailAddress = authorizedEmailAddress;
        SchoolLogo = schoolLogo;
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

    protected SchoolMiniDetails(Parcel in) {
        ID = in.readInt();
        SchoolID = in.readString();
        SchoolName = in.readString();
        StreetAddress = in.readString();
        City = in.readString();
        Province = in.readString();
        Country = in.readString();
        AuthorizedPersonel = in.readString();
        AuthorizedMobileNumber = in.readString();
        AuthorizedEmailAddress = in.readString();
        SchoolLogo = in.readString();
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
        dest.writeString(SchoolID);
        dest.writeString(SchoolName);
        dest.writeString(StreetAddress);
        dest.writeString(City);
        dest.writeString(Province);
        dest.writeString(Country);
        dest.writeString(AuthorizedPersonel);
        dest.writeString(AuthorizedMobileNumber);
        dest.writeString(AuthorizedEmailAddress);
        dest.writeString(SchoolLogo);
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

    public static final Creator<SchoolMiniDetails> CREATOR = new Creator<SchoolMiniDetails>() {
        @Override
        public SchoolMiniDetails createFromParcel(Parcel in) {
            return new SchoolMiniDetails(in);
        }

        @Override
        public SchoolMiniDetails[] newArray(int size) {
            return new SchoolMiniDetails[size];
        }
    };

    public int getID() {
        return ID;
    }

    public String getSchoolID() {
        return SchoolID;
    }

    public String getSchoolName() {
        return SchoolName;
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

    public String getSchoolLogo() {
        return SchoolLogo;
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
