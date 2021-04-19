package com.goodkredit.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class SkycablePPVSubscription implements Parcelable {

    private String SkyCableAccountNo;
    private String CustomerFirstName;
    private String CustomerLastName;
    private String CustomerMobileNumber;
    private String CustomerEmailAddress;
    private String CustomerAddress;
    private String City;
    private double Amount;
    private String MerchantID;
    private String Longitude;
    private String Latitude;
    private String PPVID;
    private String PPVName;
    private String PPVDescription;
    private String ImageURL;

    public SkycablePPVSubscription(String skyCableAccountNo, String customerFirstName, String customerLastName, String customerMobileNumber, String customerEmailAddress, String customerAddress, String city, double amount, String merchantID, String longitude, String latitude, String PPVID, String PPVName, String PPVDescription, String imageURL) {
        SkyCableAccountNo = skyCableAccountNo;
        CustomerFirstName = customerFirstName;
        CustomerLastName = customerLastName;
        CustomerMobileNumber = customerMobileNumber;
        CustomerEmailAddress = customerEmailAddress;
        CustomerAddress = customerAddress;
        City = city;
        Amount = amount;
        MerchantID = merchantID;
        Longitude = longitude;
        Latitude = latitude;
        this.PPVID = PPVID;
        this.PPVName = PPVName;
        this.PPVDescription = PPVDescription;
        ImageURL = imageURL;
    }

    protected SkycablePPVSubscription(Parcel in) {
        SkyCableAccountNo = in.readString();
        CustomerFirstName = in.readString();
        CustomerLastName = in.readString();
        CustomerMobileNumber = in.readString();
        CustomerEmailAddress = in.readString();
        CustomerAddress = in.readString();
        City = in.readString();
        Amount = in.readDouble();
        MerchantID = in.readString();
        Longitude = in.readString();
        Latitude = in.readString();
        PPVID = in.readString();
        PPVName = in.readString();
        PPVDescription = in.readString();
        ImageURL = in.readString();
    }

    public static final Creator<SkycablePPVSubscription> CREATOR = new Creator<SkycablePPVSubscription>() {
        @Override
        public SkycablePPVSubscription createFromParcel(Parcel in) {
            return new SkycablePPVSubscription(in);
        }

        @Override
        public SkycablePPVSubscription[] newArray(int size) {
            return new SkycablePPVSubscription[size];
        }
    };

    public String getSkyCableAccountNo() {
        return SkyCableAccountNo;
    }

    public String getCustomerFirstName() {
        return CustomerFirstName;
    }

    public String getCustomerLastName() {
        return CustomerLastName;
    }

    public String getCustomerMobileNumber() {
        return CustomerMobileNumber;
    }

    public String getCustomerEmailAddress() {
        return CustomerEmailAddress;
    }

    public String getCustomerAddress() {
        return CustomerAddress;
    }

    public String getCity() {
        return City;
    }

    public double getAmount() {
        return Amount;
    }

    public String getMerchantID() {
        return MerchantID;
    }

    public String getLongitude() {
        return Longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public String getPPVID() {
        return PPVID;
    }

    public String getPPVName() {
        return PPVName;
    }

    public String getPPVDescription() {
        return PPVDescription;
    }

    public String getImageURL() {
        return ImageURL;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(SkyCableAccountNo);
        dest.writeString(CustomerFirstName);
        dest.writeString(CustomerLastName);
        dest.writeString(CustomerMobileNumber);
        dest.writeString(CustomerEmailAddress);
        dest.writeString(CustomerAddress);
        dest.writeString(City);
        dest.writeDouble(Amount);
        dest.writeString(MerchantID);
        dest.writeString(Longitude);
        dest.writeString(Latitude);
        dest.writeString(PPVID);
        dest.writeString(PPVName);
        dest.writeString(PPVDescription);
        dest.writeString(ImageURL);
    }
}
