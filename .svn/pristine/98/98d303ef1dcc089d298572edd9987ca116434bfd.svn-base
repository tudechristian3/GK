package com.goodkredit.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ban_Lenovo on 1/10/2018.
 */

public class CSBRedemptionSendDetails implements Parcelable {

    @SerializedName("num")
    @Expose
    private String num;

    @SerializedName("voucher_info")
    @Expose
    private VoucherInfo voucherInfo;

    protected CSBRedemptionSendDetails(Parcel in) {
        num = in.readString();
        voucherInfo = in.readParcelable(VoucherInfo.class.getClassLoader());
    }

    public static final Creator<CSBRedemptionSendDetails> CREATOR = new Creator<CSBRedemptionSendDetails>() {
        @Override
        public CSBRedemptionSendDetails createFromParcel(Parcel in) {
            return new CSBRedemptionSendDetails(in);
        }

        @Override
        public CSBRedemptionSendDetails[] newArray(int size) {
            return new CSBRedemptionSendDetails[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(num);
        dest.writeParcelable(voucherInfo, flags);
    }

    public String getNum() {
        return num;
    }

    public VoucherInfo getVoucherInfo() {
        return voucherInfo;
    }
}
