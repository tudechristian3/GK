package com.goodkredit.myapplication.model.schoolmini;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SchoolMiniParticulars implements Parcelable {
    @SerializedName("SubjectID")
    @Expose
    private String SubjectID;
    @SerializedName("SubjectDescription")
    @Expose
    private String SubjectDescription;
    @SerializedName("Charges")
    @Expose
    private double Charges;
    @SerializedName("Payments")
    @Expose
    private double Payments;
    @SerializedName("Balance")
    @Expose
    private double Balance;

    public SchoolMiniParticulars(String subjectID, String subjectDescription, double charges, double payments, double balance) {
        SubjectID = subjectID;
        SubjectDescription = subjectDescription;
        Charges = charges;
        Payments = payments;
        Balance = balance;
    }

    protected SchoolMiniParticulars(Parcel in) {
        SubjectID = in.readString();
        SubjectDescription = in.readString();
        Charges = in.readDouble();
        Payments = in.readDouble();
        Balance = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(SubjectID);
        dest.writeString(SubjectDescription);
        dest.writeDouble(Charges);
        dest.writeDouble(Payments);
        dest.writeDouble(Balance);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SchoolMiniParticulars> CREATOR = new Creator<SchoolMiniParticulars>() {
        @Override
        public SchoolMiniParticulars createFromParcel(Parcel in) {
            return new SchoolMiniParticulars(in);
        }

        @Override
        public SchoolMiniParticulars[] newArray(int size) {
            return new SchoolMiniParticulars[size];
        }
    };

    public String getSubjectID() {
        return SubjectID;
    }

    public String getSubjectDescription() {
        return SubjectDescription;
    }

    public double getCharges() {
        return Charges;
    }

    public double getPayments() {
        return Payments;
    }

    public double getBalance() {
        return Balance;
    }
}
