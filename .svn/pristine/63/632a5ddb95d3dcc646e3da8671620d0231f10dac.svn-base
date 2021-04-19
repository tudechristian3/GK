package com.goodkredit.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ban_Lenovo on 1/10/2018.
 */

public class CSBRedemptionReceiveDetails implements Parcelable {

    @SerializedName("status_code")
    @Expose
    private String status_code;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("remaining_points")
    @Expose
    private double remaining_points;
    @SerializedName("tran_no")
    @Expose
    private String tran_no;

    protected CSBRedemptionReceiveDetails(Parcel in) {
        status_code = in.readString();
        status = in.readString();
        remaining_points = in.readDouble();
        tran_no = in.readString();
    }

    public static final Creator<CSBRedemptionReceiveDetails> CREATOR = new Creator<CSBRedemptionReceiveDetails>() {
        @Override
        public CSBRedemptionReceiveDetails createFromParcel(Parcel in) {
            return new CSBRedemptionReceiveDetails(in);
        }

        @Override
        public CSBRedemptionReceiveDetails[] newArray(int size) {
            return new CSBRedemptionReceiveDetails[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status_code);
        dest.writeString(status);
        dest.writeDouble(remaining_points);
        dest.writeString(tran_no);
    }

    public String getStatus_code() {
        return status_code;
    }

    public String getStatus() {
        return status;
    }

    public double getRemaining_points() {
        return remaining_points;
    }

    public String getTran_no() {
        String trans = ".";
        try {
            if (!tran_no.isEmpty())
                trans = tran_no;
        } catch (Exception e) {
            e.printStackTrace();
            trans = ".";
        }
        return trans;
    }
}
