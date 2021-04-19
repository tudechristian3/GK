package com.goodkredit.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ban_Lenovo on 8/23/2017.
 */

public class V2SmartRetailWalletQueue implements Parcelable {

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

    protected V2SmartRetailWalletQueue(Parcel in) {
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
        IMEI = in.readString();
        AuthCode = in.readString();
        Extra1 = in.readString();
        Extra2 = in.readString();
        Notes1 = in.readString();
        Notes2 = in.readString();
    }

    public static final Creator<V2SmartRetailWalletQueue> CREATOR = new Creator<V2SmartRetailWalletQueue>() {
        @Override
        public V2SmartRetailWalletQueue createFromParcel(Parcel in) {
            return new V2SmartRetailWalletQueue(in);
        }

        @Override
        public V2SmartRetailWalletQueue[] newArray(int size) {
            return new V2SmartRetailWalletQueue[size];
        }
    };

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
        dest.writeString(IMEI);
        dest.writeString(AuthCode);
        dest.writeString(Extra1);
        dest.writeString(Extra2);
        dest.writeString(Notes1);
        dest.writeString(Notes2);
    }
}
