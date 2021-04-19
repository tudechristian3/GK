package com.goodkredit.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ban_Lenovo on 1/10/2018.
 */

public class VoucherInfo implements Parcelable {

    @SerializedName("voucherinfos")
    @Expose
    private List<VoucherInfos> voucherInfos = new ArrayList<>();


    protected VoucherInfo(Parcel in) {
        voucherInfos = in.createTypedArrayList(VoucherInfos.CREATOR);
    }

    public static final Creator<VoucherInfo> CREATOR = new Creator<VoucherInfo>() {
        @Override
        public VoucherInfo createFromParcel(Parcel in) {
            return new VoucherInfo(in);
        }

        @Override
        public VoucherInfo[] newArray(int size) {
            return new VoucherInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(voucherInfos);
    }

    public List<VoucherInfos> getVoucherInfos() {
        return voucherInfos;
    }
}
