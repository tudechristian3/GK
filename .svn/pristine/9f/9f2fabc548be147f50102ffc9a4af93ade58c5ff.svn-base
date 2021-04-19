package com.goodkredit.myapplication.model.vouchers;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccreditedBanks implements Parcelable {

    public static final String KEY_ACCREDITEDBANKS_BANKCODE = "key_accreditedbanks_bankcode";
    public static final String KEY_ACCREDITEDBANKS_BANKNAME = "key_accreditedbanks_bankname";

    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("BankCode")
    @Expose
    private String BankCode;
    @SerializedName("Bank")
    @Expose
    private String Bank;
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

    public AccreditedBanks(int ID, String bankCode, String bank, String extra1, String extra2, String extra3, String extra4,
                           String notes1, String notes2) {
        this.ID = ID;
        BankCode = bankCode;
        Bank = bank;
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

    public String getBankCode() {
        return BankCode;
    }

    public String getBank() {
        return Bank;
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
        dest.writeString(this.BankCode);
        dest.writeString(this.Bank);
        dest.writeString(this.Extra1);
        dest.writeString(this.Extra2);
        dest.writeString(this.Extra3);
        dest.writeString(this.Extra4);
        dest.writeString(this.Notes1);
        dest.writeString(this.Notes2);
    }

    protected AccreditedBanks(Parcel in) {
        this.ID = in.readInt();
        this.BankCode = in.readString();
        this.Bank = in.readString();
        this.Extra1 = in.readString();
        this.Extra2 = in.readString();
        this.Extra3 = in.readString();
        this.Extra4 = in.readString();
        this.Notes1 = in.readString();
        this.Notes2 = in.readString();
    }

    public static final Parcelable.Creator<AccreditedBanks> CREATOR = new Parcelable.Creator<AccreditedBanks>() {
        @Override
        public AccreditedBanks createFromParcel(Parcel source) {
            return new AccreditedBanks(source);
        }

        @Override
        public AccreditedBanks[] newArray(int size) {
            return new AccreditedBanks[size];
        }
    };
}
