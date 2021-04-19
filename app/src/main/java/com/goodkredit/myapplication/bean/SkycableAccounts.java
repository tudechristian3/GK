package com.goodkredit.myapplication.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SkycableAccounts {
    @SerializedName("DateTimeIN")
    @Expose
    private String DateTimeIN;
    @SerializedName("SkyCableAccountNo")
    @Expose
    private String SkyCableAccountNo;
    @SerializedName("SkyCableAccountName")
    @Expose
    private String SkyCableAccountName;
    @SerializedName("MobileNo")
    @Expose
    private String MobileNo;
    @SerializedName("Source")
    @Expose
    private String Source;
    @SerializedName("Status")
    @Expose
    private String Status;

    public SkycableAccounts(String dateTimeIN, String skyCableAccountNo, String skyCableAccountName, String mobileNo, String source, String status) {
        DateTimeIN = dateTimeIN;
        SkyCableAccountNo = skyCableAccountNo;
        SkyCableAccountName = skyCableAccountName;
        MobileNo = mobileNo;
        Source = source;
        Status = status;
    }

    public String getDateTimeIN() {
        return DateTimeIN;
    }

    public String getSkyCableAccountNo() {
        return SkyCableAccountNo;
    }

    public String getSkyCableAccountName() {
        return SkyCableAccountName;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public String getSource() {
        return Source;
    }

    public String getStatus() {
        return Status;
    }
}
