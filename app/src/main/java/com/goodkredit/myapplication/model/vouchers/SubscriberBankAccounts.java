package com.goodkredit.myapplication.model.vouchers;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubscriberBankAccounts implements Parcelable {

    public static final String KEY_SUBSCRIBERBANK_BANK = "key_subscriberbank_bank";
    public static final String KEY_SUBSCRIBERBANK_ACCOUNTNO = "key_subscriberbank_accountno";
    public static final String KEY_SUBSCRIBERBANK_ACCOUNTNAME = "key_subscriberbank_accountname";
    public static final String KEY_SUBSCRIBERBANK_REMAININGBALANCE = "key_subscriberbank_chequedenom";

    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("BorrowerID")
    @Expose
    private String BorrowerID;
    @SerializedName("BankCode")
    @Expose
    private String BankCode;
    @SerializedName("Bank")
    @Expose
    private String Bank;
    @SerializedName("AccountNo")
    @Expose
    private String AccountNo;
    @SerializedName("AccountName")
    @Expose
    private String AccountName;
    @SerializedName("isVerified")
    @Expose
    private String isVerified;
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

    public SubscriberBankAccounts(int ID, String borrowerID, String bankCode, String bank, String accountNo, String accountName,
                                  String isVerified, String status, String extra1, String extra2, String extra3, String extra4,
                                  String notes1, String notes2) {
        this.ID = ID;
        BorrowerID = borrowerID;
        BankCode = bankCode;
        Bank = bank;
        AccountNo = accountNo;
        AccountName = accountName;
        this.isVerified = isVerified;
        Status = status;
        Extra1 = extra1;
        Extra2 = extra2;
        Extra3 = extra3;
        Extra4 = extra4;
        Notes1 = notes1;
        Notes2 = notes2;
    }

    public int getID() {
        return ID;
    }

    public String getBorrowerID() {
        return BorrowerID;
    }

    public String getBankCode() {
        return BankCode;
    }

    public String getBank() {
        return Bank;
    }

    public String getAccountNo() {
        return AccountNo;
    }

    public String getAccountName() {
        return AccountName;
    }

    public String getIsVerified() {
        return isVerified;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ID);
        dest.writeString(this.BorrowerID);
        dest.writeString(this.BankCode);
        dest.writeString(this.Bank);
        dest.writeString(this.AccountNo);
        dest.writeString(this.AccountName);
        dest.writeString(this.isVerified);
        dest.writeString(this.Status);
        dest.writeString(this.Extra1);
        dest.writeString(this.Extra2);
        dest.writeString(this.Extra3);
        dest.writeString(this.Extra4);
        dest.writeString(this.Notes1);
        dest.writeString(this.Notes2);
    }

    protected SubscriberBankAccounts(Parcel in) {
        this.ID = in.readInt();
        this.BorrowerID = in.readString();
        this.BankCode = in.readString();
        this.Bank = in.readString();
        this.AccountNo = in.readString();
        this.AccountName = in.readString();
        this.isVerified = in.readString();
        this.Status = in.readString();
        this.Extra1 = in.readString();
        this.Extra2 = in.readString();
        this.Extra3 = in.readString();
        this.Extra4 = in.readString();
        this.Notes1 = in.readString();
        this.Notes2 = in.readString();
    }

    public static final Creator<SubscriberBankAccounts> CREATOR = new Creator<SubscriberBankAccounts>() {
        @Override
        public SubscriberBankAccounts createFromParcel(Parcel source) {
            return new SubscriberBankAccounts(source);
        }

        @Override
        public SubscriberBankAccounts[] newArray(int size) {
            return new SubscriberBankAccounts[size];
        }
    };
}