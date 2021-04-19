package com.goodkredit.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SkycableRegistration implements Parcelable {
    @SerializedName("PlanName")
    @Expose
    private String PlanName;
    @SerializedName("PlanType")
    @Expose
    private String PlanType;
    @SerializedName("PlanImgUrl")
    @Expose
    private String PlanImgUrl;
    @SerializedName("PlanStyle")
    @Expose
    private String PlanStyle;
    @SerializedName("Description")
    @Expose
    private String Description;
    @SerializedName("DateTimeIN")
    @Expose
    private String DateTimeIN;
    @SerializedName("DateTimeCompleted")
    @Expose
    private String DateTimeCompleted;
    @SerializedName("RegistrationID")
    @Expose
    private String RegistrationID;
    @SerializedName("BorrowerID")
    @Expose
    private String BorrowerID;
    @SerializedName("FirstName")
    @Expose
    private String FirstName;
    @SerializedName("LastName")
    @Expose
    private String LastName;
    @SerializedName("Gender")
    @Expose
    private String Gender;
    @SerializedName("Birthdate")
    @Expose
    private String Birthdate;
    @SerializedName("Address")
    @Expose
    private String Address;
    @SerializedName("City")
    @Expose
    private String City;
    @SerializedName("Longitude")
    @Expose
    private String Longitude;
    @SerializedName("Latitude")
    @Expose
    private String Latitude;
    @SerializedName("ZIPCode")
    @Expose
    private String ZIPCode;
    @SerializedName("BillingAddress")
    @Expose
    private String BillingAddress;
    @SerializedName("InstallationAddress")
    @Expose
    private String InstallationAddress;
    @SerializedName("EmailAddress")
    @Expose
    private String EmailAddress;
    @SerializedName("MobileNumber")
    @Expose
    private String MobileNumber;
    @SerializedName("TelephoneNumber")
    @Expose
    private String TelephoneNumber;
    @SerializedName("PlanID")
    @Expose
    private String PlanID;
    @SerializedName("DiscountBase")
    @Expose
    private double DiscountBase;
    @SerializedName("DiscountPercentage")
    @Expose
    private double DiscountPercentage;
    @SerializedName("InstallationFee")
    @Expose
    private double InstallationFee;
    @SerializedName("MonthlyFee")
    @Expose
    private double MonthlyFee;
    @SerializedName("InitialCashout")
    @Expose
    private double InitialCashout;
    @SerializedName("ServiceCharge")
    @Expose
    private double ServiceCharge;
    @SerializedName("TotalAmountPaid")
    @Expose
    private double TotalAmountPaid;
    @SerializedName("GKPaymentReferenceNo")
    @Expose
    private String GKPaymentReferenceNo;
    @SerializedName("AccountNo")
    @Expose
    private String AccountNo;
    @SerializedName("MerchantID")
    @Expose
    private String MerchantID;
    @SerializedName("Status")
    @Expose
    private String Status;
    @SerializedName("Notes1")
    @Expose
    private String Notes1;

    public SkycableRegistration(String planName, String planType, String planImgUrl, String planStyle, String description, String dateTimeIN, String dateTimeCompleted, String registrationID, String borrowerID, String firstName, String lastName, String gender, String birthdate, String address, String city, String longitude, String latitude, String ZIPCode, String billingAddress, String installationAddress, String emailAddress, String mobileNumber, String telephoneNumber, String planID, double discountBase, double discountPercentage, double installationFee, double monthlyFee, double initialCashout, double serviceCharge, double totalAmountPaid, String GKPaymentReferenceNo, String accountNo, String merchantID, String status, String notes1) {
        PlanName = planName;
        PlanType = planType;
        PlanImgUrl = planImgUrl;
        PlanStyle = planStyle;
        Description = description;
        DateTimeIN = dateTimeIN;
        DateTimeCompleted = dateTimeCompleted;
        RegistrationID = registrationID;
        BorrowerID = borrowerID;
        FirstName = firstName;
        LastName = lastName;
        Gender = gender;
        Birthdate = birthdate;
        Address = address;
        City = city;
        Longitude = longitude;
        Latitude = latitude;
        this.ZIPCode = ZIPCode;
        BillingAddress = billingAddress;
        InstallationAddress = installationAddress;
        EmailAddress = emailAddress;
        MobileNumber = mobileNumber;
        TelephoneNumber = telephoneNumber;
        PlanID = planID;
        DiscountBase = discountBase;
        DiscountPercentage = discountPercentage;
        InstallationFee = installationFee;
        MonthlyFee = monthlyFee;
        InitialCashout = initialCashout;
        ServiceCharge = serviceCharge;
        TotalAmountPaid = totalAmountPaid;
        this.GKPaymentReferenceNo = GKPaymentReferenceNo;
        AccountNo = accountNo;
        MerchantID = merchantID;
        Status = status;
        Notes1 = notes1;
    }

    protected SkycableRegistration(Parcel in) {
        PlanName = in.readString();
        PlanType = in.readString();
        PlanImgUrl = in.readString();
        PlanStyle = in.readString();
        Description = in.readString();
        DateTimeIN = in.readString();
        DateTimeCompleted = in.readString();
        RegistrationID = in.readString();
        BorrowerID = in.readString();
        FirstName = in.readString();
        LastName = in.readString();
        Gender = in.readString();
        Birthdate = in.readString();
        Address = in.readString();
        City = in.readString();
        Longitude = in.readString();
        Latitude = in.readString();
        ZIPCode = in.readString();
        BillingAddress = in.readString();
        InstallationAddress = in.readString();
        EmailAddress = in.readString();
        MobileNumber = in.readString();
        TelephoneNumber = in.readString();
        PlanID = in.readString();
        DiscountBase = in.readDouble();
        DiscountPercentage = in.readDouble();
        InstallationFee = in.readDouble();
        MonthlyFee = in.readDouble();
        InitialCashout = in.readDouble();
        ServiceCharge = in.readDouble();
        TotalAmountPaid = in.readDouble();
        GKPaymentReferenceNo = in.readString();
        AccountNo = in.readString();
        MerchantID = in.readString();
        Status = in.readString();
        Notes1 = in.readString();
    }

    public static final Creator<SkycableRegistration> CREATOR = new Creator<SkycableRegistration>() {
        @Override
        public SkycableRegistration createFromParcel(Parcel in) {
            return new SkycableRegistration(in);
        }

        @Override
        public SkycableRegistration[] newArray(int size) {
            return new SkycableRegistration[size];
        }
    };

    public String getPlanName() {
        return PlanName;
    }

    public String getPlanType() {
        return PlanType;
    }

    public String getPlanImgUrl() {
        return PlanImgUrl;
    }

    public String getPlanStyle() {
        return PlanStyle;
    }

    public String getDescription() {
        return Description;
    }

    public String getDateTimeIN() {
        return DateTimeIN;
    }

    public String getDateTimeCompleted() {
        return DateTimeCompleted;
    }

    public String getRegistrationID() {
        return RegistrationID;
    }

    public String getBorrowerID() {
        return BorrowerID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getGender() {
        return Gender;
    }

    public String getBirthdate() {
        return Birthdate;
    }

    public String getAddress() {
        return Address;
    }

    public String getCity() {
        return City;
    }

    public String getLongitude() {
        return Longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public String getZIPCode() {
        return ZIPCode;
    }

    public String getBillingAddress() {
        return BillingAddress;
    }

    public String getInstallationAddress() {
        return InstallationAddress;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public String getTelephoneNumber() {
        return TelephoneNumber;
    }

    public String getPlanID() {
        return PlanID;
    }

    public double getDiscountBase() {
        return DiscountBase;
    }

    public double getDiscountPercentage() {
        return DiscountPercentage;
    }

    public double getInstallationFee() {
        return InstallationFee;
    }

    public double getMonthlyFee() {
        return MonthlyFee;
    }

    public double getInitialCashout() {
        return InitialCashout;
    }

    public double getServiceCharge() {
        return ServiceCharge;
    }

    public double getTotalAmountPaid() {
        return TotalAmountPaid;
    }

    public String getGKPaymentReferenceNo() {
        return GKPaymentReferenceNo;
    }

    public String getAccountNo() {
        return AccountNo;
    }

    public String getMerchantID() {
        return MerchantID;
    }

    public String getStatus() {
        return Status;
    }

    public String getNotes1() {
        return Notes1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(PlanName);
        dest.writeString(PlanType);
        dest.writeString(PlanImgUrl);
        dest.writeString(PlanStyle);
        dest.writeString(Description);
        dest.writeString(DateTimeIN);
        dest.writeString(DateTimeCompleted);
        dest.writeString(RegistrationID);
        dest.writeString(BorrowerID);
        dest.writeString(FirstName);
        dest.writeString(LastName);
        dest.writeString(Gender);
        dest.writeString(Birthdate);
        dest.writeString(Address);
        dest.writeString(City);
        dest.writeString(Longitude);
        dest.writeString(Latitude);
        dest.writeString(ZIPCode);
        dest.writeString(BillingAddress);
        dest.writeString(InstallationAddress);
        dest.writeString(EmailAddress);
        dest.writeString(MobileNumber);
        dest.writeString(TelephoneNumber);
        dest.writeString(PlanID);
        dest.writeDouble(DiscountBase);
        dest.writeDouble(DiscountPercentage);
        dest.writeDouble(InstallationFee);
        dest.writeDouble(MonthlyFee);
        dest.writeDouble(InitialCashout);
        dest.writeDouble(ServiceCharge);
        dest.writeDouble(TotalAmountPaid);
        dest.writeString(GKPaymentReferenceNo);
        dest.writeString(AccountNo);
        dest.writeString(MerchantID);
        dest.writeString(Status);
        dest.writeString(Notes1);
    }
}
