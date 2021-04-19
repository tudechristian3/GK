package com.goodkredit.myapplication.model.schoolmini;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SchoolMiniStudents implements Parcelable {
    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("SchoolID")
    @Expose
    private String SchoolID;
    @SerializedName("BorrowerID")
    @Expose
    private String BorrowerID;
    @SerializedName("StudentID")
    @Expose
    private String StudentID;
    @SerializedName("MobileNumber")
    @Expose
    private String MobileNumber;
    @SerializedName("FirstName")
    @Expose
    private String FirstName;
    @SerializedName("MiddleName")
    @Expose
    private String MiddleName;
    @SerializedName("LastName")
    @Expose
    private String LastName;
    @SerializedName("Course")
    @Expose
    private String Course;
    @SerializedName("YearLevel")
    @Expose
    private String YearLevel;
    @SerializedName("EmailAddress")
    @Expose
    private String EmailAddress;
    @SerializedName("Gender")
    @Expose
    private String Gender;
    @SerializedName("Birthdate")
    @Expose
    private String Birthdate;
    @SerializedName("StreetAddress")
    @Expose
    private String StreetAddress;
    @SerializedName("Province")
    @Expose
    private String Province;
    @SerializedName("City")
    @Expose
    private String City;
    @SerializedName("Country")
    @Expose
    private String Country;
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

    public SchoolMiniStudents(int ID, String schoolID, String borrowerID, String studentID, String mobileNumber, String firstName, String middleName, String lastName, String course, String yearLevel, String emailAddress, String gender, String birthdate, String streetAddress, String province, String city, String country, String dateTimeUploaded, String status, String extra1, String extra2, String extra3, String extra4, String notes1, String notes2) {
        this.ID = ID;
        SchoolID = schoolID;
        BorrowerID = borrowerID;
        StudentID = studentID;
        MobileNumber = mobileNumber;
        FirstName = firstName;
        MiddleName = middleName;
        LastName = lastName;
        Course = course;
        YearLevel = yearLevel;
        EmailAddress = emailAddress;
        Gender = gender;
        Birthdate = birthdate;
        StreetAddress = streetAddress;
        Province = province;
        City = city;
        Country = country;
        DateTimeUploaded = dateTimeUploaded;
        Status = status;
        Extra1 = extra1;
        Extra2 = extra2;
        Extra3 = extra3;
        Extra4 = extra4;
        Notes1 = notes1;
        Notes2 = notes2;
    }

    protected SchoolMiniStudents(Parcel in) {
        ID = in.readInt();
        SchoolID = in.readString();
        BorrowerID = in.readString();
        StudentID = in.readString();
        MobileNumber = in.readString();
        FirstName = in.readString();
        MiddleName = in.readString();
        LastName = in.readString();
        Course = in.readString();
        YearLevel = in.readString();
        EmailAddress = in.readString();
        Gender = in.readString();
        Birthdate = in.readString();
        StreetAddress = in.readString();
        Province = in.readString();
        City = in.readString();
        Country = in.readString();
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
        dest.writeString(SchoolID);
        dest.writeString(BorrowerID);
        dest.writeString(StudentID);
        dest.writeString(MobileNumber);
        dest.writeString(FirstName);
        dest.writeString(MiddleName);
        dest.writeString(LastName);
        dest.writeString(Course);
        dest.writeString(YearLevel);
        dest.writeString(EmailAddress);
        dest.writeString(Gender);
        dest.writeString(Birthdate);
        dest.writeString(StreetAddress);
        dest.writeString(Province);
        dest.writeString(City);
        dest.writeString(Country);
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

    public static final Creator<SchoolMiniStudents> CREATOR = new Creator<SchoolMiniStudents>() {
        @Override
        public SchoolMiniStudents createFromParcel(Parcel in) {
            return new SchoolMiniStudents(in);
        }

        @Override
        public SchoolMiniStudents[] newArray(int size) {
            return new SchoolMiniStudents[size];
        }
    };

    public int getID() {
        return ID;
    }

    public String getSchoolID() {
        return SchoolID;
    }

    public String getBorrowerID() {
        return BorrowerID;
    }

    public String getStudentID() {
        return StudentID;
    }

    public String getMobileNumber() {
        return MobileNumber;
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

    public String getCourse() {
        return Course;
    }

    public String getYearLevel() {
        return YearLevel;
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

    public String getStreetAddress() {
        return StreetAddress;
    }

    public String getProvince() {
        return Province;
    }

    public String getCity() {
        return City;
    }

    public String getCountry() {
        return Country;
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
