package com.goodkredit.myapplication.model.dropoff;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentRequestOrderDetails implements Parcelable {

    @SerializedName("itemid")
    @Expose
    private String itemid;
    @SerializedName("itemname")
    @Expose
    private String itemname;
    @SerializedName("itemlogo")
    @Expose
    private String itemlogo;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("itemamount")
    @Expose
    private String itemamount;
    @SerializedName("quantity")
    @Expose
    private String quantity;

    public PaymentRequestOrderDetails(String itemid, String itemname, String itemlogo, String description,
                                      String itemamount, String quantity) {
        this.itemid = itemid;
        this.itemname = itemname;
        this.itemlogo = itemlogo;
        this.description = description;
        this.itemamount = itemamount;
        this.quantity = quantity;
    }

    public String getItemid() {
        return itemid;
    }

    public String getItemname() {
        return itemname;
    }

    public String getItemlogo() {
        return itemlogo;
    }

    public String getDescription() {
        return description;
    }

    public String getItemamount() {
        return itemamount;
    }

    public String getQuantity() {
        return quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.itemid);
        dest.writeString(this.itemname);
        dest.writeString(this.itemlogo);
        dest.writeString(this.description);
        dest.writeString(this.itemamount);
        dest.writeString(this.quantity);
    }

    protected PaymentRequestOrderDetails(Parcel in) {
        this.itemid = in.readString();
        this.itemname = in.readString();
        this.itemlogo = in.readString();
        this.description = in.readString();
        this.itemamount = in.readString();
        this.quantity = in.readString();
    }

    public static final Parcelable.Creator<PaymentRequestOrderDetails> CREATOR = new Parcelable.Creator<PaymentRequestOrderDetails>() {
        @Override
        public PaymentRequestOrderDetails createFromParcel(Parcel source) {
            return new PaymentRequestOrderDetails(source);
        }

        @Override
        public PaymentRequestOrderDetails[] newArray(int size) {
            return new PaymentRequestOrderDetails[size];
        }
    };
}
