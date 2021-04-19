package com.goodkredit.myapplication.model;

import java.io.Serializable;

/**
 * Created by Ban_Lenovo on 8/1/2017.
 */

public class V2Subscriber implements Serializable {

    private String BorrowerName;
    private String MobileNumber;
    private String EmailAddress;
    private String StreetAddress;
    private String City;
    private String Country;
    private String GuarantorID;
    private String ProfilePic;
    private String FirstName;
    private String LastName;
    private String MiddleName;
    private String Gender;
    private String Notes1;

    public V2Subscriber(String borrowerName, String mobileNumber, String emailAddress,
                        String streetAddress, String city, String country,
                        String guarantorid, String profilePic, String firstName, String lastName,
                        String middleName, String gender, String notes1) {
        BorrowerName = borrowerName;
        MobileNumber = mobileNumber;
        EmailAddress = emailAddress;
        StreetAddress = streetAddress;
        City = city;
        Country = country;
        GuarantorID = guarantorid;
        ProfilePic = profilePic;
        FirstName = firstName;
        LastName = lastName;
        MiddleName = middleName;
        Gender = gender;
        Notes1 = notes1;
    }

    public String getGender() {
        return Gender;
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

    public String getProfilePic() {
        return ProfilePic;
    }

    public String getBorrowerName() {
        return BorrowerName;
    }

    public String getMobileNumber() {
        return MobileNumber;
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

    public String getNotes1() {
        return Notes1;
    }

}
