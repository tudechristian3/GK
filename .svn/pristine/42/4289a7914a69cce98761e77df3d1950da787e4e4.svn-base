package com.goodkredit.myapplication.model.egame;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EGameProducts implements Parcelable {

    public static final String KEY_EGAMEPRODUCT_RESULT = "EGAMEPRODUCT_RESULT";

    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("ProductCode")
    @Expose
    private String ProductCode;
    @SerializedName("Amount")
    @Expose
    private double Amount;
    @SerializedName("Description")
    @Expose
    private String Description;

    public EGameProducts(int ID, String productCode, double amount, String description) {
        this.ID = ID;
        ProductCode = productCode;
        Amount = amount;
        Description = description;
    }

    public int getID() {
        return ID;
    }

    public String getProductCode() {
        return ProductCode;
    }

    public double getAmount() {
        return Amount;
    }

    public String getDescription() {
        return Description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ID);
        dest.writeString(this.ProductCode);
        dest.writeDouble(this.Amount);
        dest.writeString(this.Description);
    }

    protected EGameProducts(Parcel in) {
        this.ID = in.readInt();
        this.ProductCode = in.readString();
        this.Amount = in.readDouble();
        this.Description = in.readString();
    }

    public static final Creator<EGameProducts> CREATOR = new Creator<EGameProducts>() {
        @Override
        public EGameProducts createFromParcel(Parcel source) {
            return new EGameProducts(source);
        }

        @Override
        public EGameProducts[] newArray(int size) {
            return new EGameProducts[size];
        }
    };
}
