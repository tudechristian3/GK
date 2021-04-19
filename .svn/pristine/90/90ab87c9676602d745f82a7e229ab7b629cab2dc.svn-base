package com.goodkredit.myapplication.model.rfid;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RFIDInfo implements Parcelable {

    public static final String KEY_RFIDINFO = "key_RFIDInfo";
    public static final String KEY_RFIDFROM = "key_RFIDFrom";
    public static final String KEY_RFIDPOSITION = "key_RFIDPosition";
    public static final String KEY_LIMITPOSITION = "key_LimitPosition";
    public static final String KEY_ACTIVEBALANCE = "key_ActiveBalance";
    public static final String KEY_ACTIVESPIN = "key_ActiveSPIN";
    public static final String KEY_POSITION = "key_position";

    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("DateTimeReleased")
    @Expose
    private String DateTimeReleased;
    @SerializedName("GuarantorID")
    @Expose
    private String GuarantorID;
    @SerializedName("GuarantorName")
    @Expose
    private String GuarantorName;
    @SerializedName("BorrowerID")
    @Expose
    private String BorrowerID;
    @SerializedName("BorrowerName")
    @Expose
    private String BorrowerName;
    @SerializedName("BorrowerMobileNo")
    @Expose
    private String BorrowerMobileNo;
    @SerializedName("RFIDNumber")
    @Expose
    private String RFIDNumber;
    @SerializedName("RFIDCardNumber")
    @Expose
    private String RFIDCardNumber;
    @SerializedName("RFIDPIN")
    @Expose
    private String RFIDPIN;
    @SerializedName("VoucherSerial")
    @Expose
    private String VoucherSerial;
    @SerializedName("isWithLimit")
    @Expose
    private int isWithLimit;
    @SerializedName("isWithPIN")
    @Expose
    private int isWithPIN;
    @SerializedName("PINMinAmount")
    @Expose
    private double PINMinAmount;
    @SerializedName("AmountLimitBalance")
    @Expose
    private double AmountLimitBalance;
    @SerializedName("LastAddedAmount")
    @Expose
    private double LastAddedAmount;
    @SerializedName("LastAddedDateTime")
    @Expose
    private String LastAddedDateTime;
    @SerializedName("LastAddedPreBalance")
    @Expose
    private double LastAddedPreBalance;
    @SerializedName("LastAddedPostBalance")
    @Expose
    private double LastAddedPostBalance;
    @SerializedName("LastDeductedAmount")
    @Expose
    private double LastDeductedAmount;
    @SerializedName("LastDeductedDateTime")
    @Expose
    private String LastDeductedDateTime;
    @SerializedName("LastDeductedPreBalance")
    @Expose
    private double LastDeductedPreBalance;
    @SerializedName("LastDeductedPostBalance")
    @Expose
    private double LastDeductedPostBalance;
    @SerializedName("DateTimeUpdated")
    @Expose
    private String DateTimeUpdated;
    @SerializedName("Status")
    @Expose
    private String Status;
    @SerializedName("Extra1")
    @Expose
    private String Extra1;
    @SerializedName("Extra2")
    @Expose
    private String Extra2;
    @SerializedName("Extra3")
    @Expose
    private String Extra3;
    @SerializedName("Extra4")
    @Expose
    private String Extra4;
    @SerializedName("Notes1")
    @Expose
    private String Notes1;
    @SerializedName("Notes2")
    @Expose
    private String Notes2;

    public RFIDInfo(int ID, String dateTimeReleased, String guarantorID, String guarantorName, String borrowerID, String borrowerName, String borrowerMobileNo, String RFIDNumber, String RFIDCardNumber, String RFIDPIN, String voucherSerial, int isWithLimit, int isWithPIN, double PINMinAmount, double amountLimitBalance, double lastAddedAmount, String lastAddedDateTime, double lastAddedPreBalance, double lastAddedPostBalance, double lastDeductedAmount, String lastDeductedDateTime, double lastDeductedPreBalance, double lastDeductedPostBalance, String dateTimeUpdated, String status, String extra1, String extra2, String extra3, String extra4, String notes1, String notes2) {
        this.ID = ID;
        DateTimeReleased = dateTimeReleased;
        GuarantorID = guarantorID;
        GuarantorName = guarantorName;
        BorrowerID = borrowerID;
        BorrowerName = borrowerName;
        BorrowerMobileNo = borrowerMobileNo;
        this.RFIDNumber = RFIDNumber;
        this.RFIDCardNumber = RFIDCardNumber;
        this.RFIDPIN = RFIDPIN;
        VoucherSerial = voucherSerial;
        this.isWithLimit = isWithLimit;
        this.isWithPIN = isWithPIN;
        this.PINMinAmount = PINMinAmount;
        AmountLimitBalance = amountLimitBalance;
        LastAddedAmount = lastAddedAmount;
        LastAddedDateTime = lastAddedDateTime;
        LastAddedPreBalance = lastAddedPreBalance;
        LastAddedPostBalance = lastAddedPostBalance;
        LastDeductedAmount = lastDeductedAmount;
        LastDeductedDateTime = lastDeductedDateTime;
        LastDeductedPreBalance = lastDeductedPreBalance;
        LastDeductedPostBalance = lastDeductedPostBalance;
        DateTimeUpdated = dateTimeUpdated;
        Status = status;
        Extra1 = extra1;
        Extra2 = extra2;
        Extra3 = extra3;
        Extra4 = extra4;
        Notes1 = notes1;
        Notes2 = notes2;
    }

    protected RFIDInfo(Parcel in) {
        ID = in.readInt();
        DateTimeReleased = in.readString();
        GuarantorID = in.readString();
        GuarantorName = in.readString();
        BorrowerID = in.readString();
        BorrowerName = in.readString();
        BorrowerMobileNo = in.readString();
        RFIDNumber = in.readString();
        RFIDCardNumber = in.readString();
        RFIDPIN = in.readString();
        VoucherSerial = in.readString();
        isWithLimit = in.readInt();
        isWithPIN = in.readInt();
        PINMinAmount = in.readDouble();
        AmountLimitBalance = in.readDouble();
        LastAddedAmount = in.readDouble();
        LastAddedDateTime = in.readString();
        LastAddedPreBalance = in.readDouble();
        LastAddedPostBalance = in.readDouble();
        LastDeductedAmount = in.readDouble();
        LastDeductedDateTime = in.readString();
        LastDeductedPreBalance = in.readDouble();
        LastDeductedPostBalance = in.readDouble();
        DateTimeUpdated = in.readString();
        Status = in.readString();
        Extra1 = in.readString();
        Extra2 = in.readString();
        Extra3 = in.readString();
        Extra4 = in.readString();
        Notes1 = in.readString();
        Notes2 = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(DateTimeReleased);
        dest.writeString(GuarantorID);
        dest.writeString(GuarantorName);
        dest.writeString(BorrowerID);
        dest.writeString(BorrowerName);
        dest.writeString(BorrowerMobileNo);
        dest.writeString(RFIDNumber);
        dest.writeString(RFIDCardNumber);
        dest.writeString(RFIDPIN);
        dest.writeString(VoucherSerial);
        dest.writeInt(isWithLimit);
        dest.writeInt(isWithPIN);
        dest.writeDouble(PINMinAmount);
        dest.writeDouble(AmountLimitBalance);
        dest.writeDouble(LastAddedAmount);
        dest.writeString(LastAddedDateTime);
        dest.writeDouble(LastAddedPreBalance);
        dest.writeDouble(LastAddedPostBalance);
        dest.writeDouble(LastDeductedAmount);
        dest.writeString(LastDeductedDateTime);
        dest.writeDouble(LastDeductedPreBalance);
        dest.writeDouble(LastDeductedPostBalance);
        dest.writeString(DateTimeUpdated);
        dest.writeString(Status);
        dest.writeString(Extra1);
        dest.writeString(Extra2);
        dest.writeString(Extra3);
        dest.writeString(Extra4);
        dest.writeString(Notes1);
        dest.writeString(Notes2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RFIDInfo> CREATOR = new Creator<RFIDInfo>() {
        @Override
        public RFIDInfo createFromParcel(Parcel in) {
            return new RFIDInfo(in);
        }

        @Override
        public RFIDInfo[] newArray(int size) {
            return new RFIDInfo[size];
        }
    };

    public int getID() {
        return ID;
    }

    public String getDateTimeReleased() {
        return DateTimeReleased;
    }

    public String getGuarantorID() {
        return GuarantorID;
    }

    public String getGuarantorName() {
        return GuarantorName;
    }

    public String getBorrowerID() {
        return BorrowerID;
    }

    public String getBorrowerName() {
        return BorrowerName;
    }

    public String getBorrowerMobileNo() {
        return BorrowerMobileNo;
    }

    public String getRFIDNumber() {
        return RFIDNumber;
    }

    public String getRFIDCardNumber() {
        return RFIDCardNumber;
    }

    public String getRFIDPIN() {
        return RFIDPIN;
    }

    public String getVoucherSerial() {
        return VoucherSerial;
    }

    public int getIsWithLimit() {
        return isWithLimit;
    }

    public int getIsWithPIN() {
        return isWithPIN;
    }

    public double getPINMinAmount() {
        return PINMinAmount;
    }

    public double getAmountLimitBalance() {
        return AmountLimitBalance;
    }

    public double getLastAddedAmount() {
        return LastAddedAmount;
    }

    public String getLastAddedDateTime() {
        return LastAddedDateTime;
    }

    public double getLastAddedPreBalance() {
        return LastAddedPreBalance;
    }

    public double getLastAddedPostBalance() {
        return LastAddedPostBalance;
    }

    public double getLastDeductedAmount() {
        return LastDeductedAmount;
    }

    public String getLastDeductedDateTime() {
        return LastDeductedDateTime;
    }

    public double getLastDeductedPreBalance() {
        return LastDeductedPreBalance;
    }

    public double getLastDeductedPostBalance() {
        return LastDeductedPostBalance;
    }

    public String getDateTimeUpdated() {
        return DateTimeUpdated;
    }

    public String getStatus() {
        return Status;
    }

    public String getExtra1() {
        return Extra1;
    }

    public String getExtra2() {
        return Extra2;
    }

    public String getExtra3() {
        return Extra3;
    }

    public String getExtra4() {
        return Extra4;
    }

    public String getNotes1() {
        return Notes1;
    }

    public String getNotes2() {
        return Notes2;
    }
}



