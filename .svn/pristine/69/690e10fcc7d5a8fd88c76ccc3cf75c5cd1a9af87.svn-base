package com.goodkredit.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.goodkredit.myapplication.utilities.Logger;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ban_Lenovo on 1/10/2018.
 */

public class CSBRedemption implements Parcelable {

    @SerializedName("SendDetails")
    @Expose
    private String sendDetails;
    @SerializedName("ReceiveDetails")
    @Expose
    private String receiveDetails;
    @SerializedName("ResultCode")
    @Expose
    private String resultCode;
    @SerializedName("ResultDescription")
    @Expose
    private String resultDescription;
    @SerializedName("DateTimeCompleted")
    @Expose
    private String DateTimeCompleted;

    public CSBRedemption(String sendDetails, String receiveDetails, String resultCode, String resultDescription, String dateTimeCompleted) {
        this.sendDetails = sendDetails;
        this.receiveDetails = receiveDetails;
        this.resultCode = resultCode;
        this.resultDescription = resultDescription;
        DateTimeCompleted = dateTimeCompleted;
    }

    protected CSBRedemption(Parcel in) {
        sendDetails = in.readString();
        receiveDetails = in.readString();
        resultCode = in.readString();
        resultDescription = in.readString();
        DateTimeCompleted = in.readString();
    }

    public static final Creator<CSBRedemption> CREATOR = new Creator<CSBRedemption>() {
        @Override
        public CSBRedemption createFromParcel(Parcel in) {
            return new CSBRedemption(in);
        }

        @Override
        public CSBRedemption[] newArray(int size) {
            return new CSBRedemption[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sendDetails);
        dest.writeString(receiveDetails);
        dest.writeString(resultCode);
        dest.writeString(resultDescription);
        dest.writeString(DateTimeCompleted);
    }

    public CSBRedemptionSendDetails getSendDetails() {
        Gson gson = new Gson();
        return gson.fromJson(sendDetails, CSBRedemptionSendDetails.class);
    }

    public CSBRedemptionReceiveDetails getReceiveDetails() {
        Gson gson = new Gson();
        CSBRedemptionReceiveDetails receive = null;
        Logger.debug("getReceiveDetails", receiveDetails);
        try {
            if (!receiveDetails.isEmpty() && !receiveDetails.equals("."))
                receive = gson.fromJson(receiveDetails, CSBRedemptionReceiveDetails.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return receive;
    }

    public String getStrReceiveDetails() {
        return receiveDetails;
    }

    public String getStrSendDetails() {
        return sendDetails;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public String getDateTimeCompleted() {
        return DateTimeCompleted;
    }
}
