package com.goodkredit.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ban_Lenovo on 7/31/2017.
 */

public class V2PrepaidLoadQueue implements Parcelable {

    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("DateTimeIN")
    @Expose
    private String DateTimeIN;
    @SerializedName("GuarantorID")
    @Expose
    private String GuarantorID;
    @SerializedName("BorrowerID")
    @Expose
    private String BorrowerID;
    @SerializedName("UserID")
    @Expose
    private String UserID;
    @SerializedName("TransactionNo")
    @Expose
    private String TransactionNo;
    @SerializedName("ProviderTxnNo")
    @Expose
    private String ProviderTxnNo;
    @SerializedName("TelcoTxnNo")
    @Expose
    private String TelcoTxnNo;
    @SerializedName("Network")
    @Expose
    private String Network;
    @SerializedName("MobileTarget")
    @Expose
    private String MobileTarget;
    @SerializedName("ProductCode")
    @Expose
    private String ProductCode;
    @SerializedName("DenomType")
    @Expose
    private String DenomType;
    @SerializedName("Amount")
    @Expose
    private String Amount;
    @SerializedName("TxnStatus")
    @Expose
    private String TxnStatus;
    @SerializedName("TxnMedium")
    @Expose
    private String TxnMedium;
    @SerializedName("ProcessID")
    @Expose
    private String ProcessID;
    @SerializedName("SessionID")
    @Expose
    private String SessionID;
    @SerializedName("PreConsummationSessionID")
    @Expose
    private String PreConsummationSessionID;
    @SerializedName("RetailerWalletPreBalance")
    @Expose
    private String RetailerWalletPreBalance;
    @SerializedName("RetailerWalletPostBalance")
    @Expose
    private String RetailerWalletPostBalance;
    @SerializedName("IMEI")
    @Expose
    private String IMEI;
    @SerializedName("AuthCode")
    @Expose
    private String AuthCode;
    @SerializedName("Extra1")
    @Expose
    private String Extra1;
    @SerializedName("Extra2")
    @Expose
    private String Extra2;
    @SerializedName("Notes1")
    @Expose
    private String Notes1;
    @SerializedName("Notes2")
    @Expose
    private String Notes2;

    public V2PrepaidLoadQueue(int ID, String dateTimeIN, String guarantorID, String borrowerID,
                              String userID, String transactionNo, String providerTxnNo, String telcoTxnNo,
                              String network, String mobileTarget, String productCode, String denomType, String amount,
                              String txnStatus, String txnMedium, String processID, String sessionID,
                              String preConsummationSessionID, String retailerWalletPreBalance,
                              String retailerWalletPostBalance, String IMEI, String authCode, String extra1,
                              String extra2, String notes1, String notes2) {
        this.ID = ID;
        DateTimeIN = dateTimeIN;
        GuarantorID = guarantorID;
        BorrowerID = borrowerID;
        UserID = userID;
        TransactionNo = transactionNo;
        ProviderTxnNo = providerTxnNo;
        TelcoTxnNo = telcoTxnNo;
        Network = network;
        MobileTarget = mobileTarget;
        ProductCode = productCode;
        DenomType = denomType;
        Amount = amount;
        TxnStatus = txnStatus;
        TxnMedium = txnMedium;
        ProcessID = processID;
        SessionID = sessionID;
        PreConsummationSessionID = preConsummationSessionID;
        RetailerWalletPreBalance = retailerWalletPreBalance;
        RetailerWalletPostBalance = retailerWalletPostBalance;
        this.IMEI = IMEI;
        AuthCode = authCode;
        Extra1 = extra1;
        Extra2 = extra2;
        Notes1 = notes1;
        Notes2 = notes2;
    }

    public static final Creator<V2PrepaidLoadQueue> CREATOR = new Creator<V2PrepaidLoadQueue>() {
        @Override
        public V2PrepaidLoadQueue createFromParcel(Parcel in) {
            return new V2PrepaidLoadQueue(in);
        }

        @Override
        public V2PrepaidLoadQueue[] newArray(int size) {
            return new V2PrepaidLoadQueue[size];
        }
    };

    public int getID() {
        return ID;
    }

    public String getDateTimeIN() {
        return DateTimeIN;
    }

    public String getGuarantorID() {
        return GuarantorID;
    }

    public String getBorrowerID() {
        return BorrowerID;
    }

    public String getUserID() {
        return UserID;
    }

    public String getTransactionNo() {
        return TransactionNo;
    }

    public String getProviderTxnNo() {
        return ProviderTxnNo;
    }

    public String getTelcoTxnNo() {
        return TelcoTxnNo;
    }

    public String getNetwork() {
        return Network;
    }

    public String getMobileTarget() {
        return MobileTarget;
    }

    public String getProductCode() {
        return ProductCode;
    }

    public String getDenomType() {
        return DenomType;
    }

    public String getAmount() {
        return Amount;
    }

    public String getTxnStatus() {
        return TxnStatus;
    }

    public String getTxnMedium() {
        return TxnMedium;
    }

    public String getProcessID() {
        return ProcessID;
    }

    public String getSessionID() {
        return SessionID;
    }

    public String getPreConsummationSessionID() {
        return PreConsummationSessionID;
    }

    public String getRetailerWalletPreBalance() {
        return RetailerWalletPreBalance;
    }

    public String getRetailerWalletPostBalance() {
        return RetailerWalletPostBalance;
    }

    public String getIMEI() {
        return IMEI;
    }

    public String getAuthCode() {
        return AuthCode;
    }

    public String getExtra1() {
        return Extra1;
    }

    public String getExtra2() {
        return Extra2;
    }

    public String getNotes1() {
        return Notes1;
    }

    public String getNotes2() {
        return Notes2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(DateTimeIN);
        dest.writeString(GuarantorID);
        dest.writeString(BorrowerID);
        dest.writeString(UserID);
        dest.writeString(TransactionNo);
        dest.writeString(ProviderTxnNo);
        dest.writeString(TelcoTxnNo);
        dest.writeString(Network);
        dest.writeString(MobileTarget);
        dest.writeString(ProductCode);
        dest.writeString(DenomType);
        dest.writeString(Amount);
        dest.writeString(TxnStatus);
        dest.writeString(TxnMedium);
        dest.writeString(ProcessID);
        dest.writeString(SessionID);
        dest.writeString(PreConsummationSessionID);
        dest.writeString(RetailerWalletPreBalance);
        dest.writeString(RetailerWalletPostBalance);
        dest.writeString(IMEI);
        dest.writeString(AuthCode);
        dest.writeString(Extra1);
        dest.writeString(Extra2);
        dest.writeString(Notes1);
        dest.writeString(Notes2);
    }

    protected V2PrepaidLoadQueue(Parcel in) {
        ID = in.readInt();
        DateTimeIN = in.readString();
        GuarantorID = in.readString();
        BorrowerID = in.readString();
        UserID = in.readString();
        TransactionNo = in.readString();
        ProviderTxnNo = in.readString();
        TelcoTxnNo = in.readString();
        Network = in.readString();
        MobileTarget = in.readString();
        ProductCode = in.readString();
        DenomType = in.readString();
        Amount = in.readString();
        TxnStatus = in.readString();
        TxnMedium = in.readString();
        ProcessID = in.readString();
        SessionID = in.readString();
        PreConsummationSessionID = in.readString();
        RetailerWalletPreBalance = in.readString();
        RetailerWalletPostBalance = in.readString();
        IMEI = in.readString();
        AuthCode = in.readString();
        Extra1 = in.readString();
        Extra2 = in.readString();
        Notes1 = in.readString();
        Notes2 = in.readString();
    }



}
