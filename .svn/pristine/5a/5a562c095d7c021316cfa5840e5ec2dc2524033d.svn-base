package com.goodkredit.myapplication.bean.projectcoop;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GameHistory implements Parcelable {
    @SerializedName("DrawDay")
    @Expose
    private String DrawDay;
    @SerializedName("CutOverDay")
    @Expose
    private String CutOverDay;
    @SerializedName("CutOverTimeFrom")
    @Expose
    private String CutOverTimeFrom;
    @SerializedName("CutOverTimeTo")
    @Expose
    private String CutOverTimeTo;
    @SerializedName("ProductName")
    @Expose
    private String ProductName;
    @SerializedName("ProductDesc")
    @Expose
    private String ProductDesc;
    @SerializedName("Digit")
    @Expose
    private int Digit;
    @SerializedName("DateTimeIN")
    @Expose
    private String DateTimeIN;
    @SerializedName("DateTimeCompleted")
    @Expose
    private String DateTimeCompleted;
    @SerializedName("TransactionNo")
    @Expose
    private String TransactionNo;
    @SerializedName("EntryNumber")
    @Expose
    private String EntryNumber;
    @SerializedName("ProductID")
    @Expose
    private String ProductID;
    @SerializedName("AccountID")
    @Expose
    private String AccountID;
    @SerializedName("BorrowerID")
    @Expose
    private String BorrowerID;
    @SerializedName("UserID")
    @Expose
    private String UserID;
    @SerializedName("Name")
    @Expose
    private String Name;
    @SerializedName("Amount")
    @Expose
    private double Amount;
    @SerializedName("ServiceCharge")
    @Expose
    private double ServiceCharge;
    @SerializedName("AmountPaid")
    @Expose
    private double AmountPaid;
    @SerializedName("CutOverID")
    @Expose
    private String CutOverID;
    @SerializedName("TxnMedium")
    @Expose
    private String TxnMedium;
    @SerializedName("Status")
    @Expose
    private String Status;

    public GameHistory(String drawDay, String cutOverDay, String cutOverTimeFrom, String cutOverTimeTo, String productName, String productDesc, int digit, String dateTimeIN, String dateTimeCompleted, String transactionNo, String entryNumber, String productID, String accountID, String borrowerID, String userID, String name, double amount, double serviceCharge, double amountPaid, String cutOverID, String txnMedium, String status) {
        DrawDay = drawDay;
        CutOverDay = cutOverDay;
        CutOverTimeFrom = cutOverTimeFrom;
        CutOverTimeTo = cutOverTimeTo;
        ProductName = productName;
        ProductDesc = productDesc;
        Digit = digit;
        DateTimeIN = dateTimeIN;
        DateTimeCompleted = dateTimeCompleted;
        TransactionNo = transactionNo;
        EntryNumber = entryNumber;
        ProductID = productID;
        AccountID = accountID;
        BorrowerID = borrowerID;
        UserID = userID;
        Name = name;
        Amount = amount;
        ServiceCharge = serviceCharge;
        AmountPaid = amountPaid;
        CutOverID = cutOverID;
        TxnMedium = txnMedium;
        Status = status;
    }

    protected GameHistory(Parcel in) {
        DrawDay = in.readString();
        CutOverDay = in.readString();
        CutOverTimeFrom = in.readString();
        CutOverTimeTo = in.readString();
        ProductName = in.readString();
        ProductDesc = in.readString();
        Digit = in.readInt();
        DateTimeIN = in.readString();
        DateTimeCompleted = in.readString();
        TransactionNo = in.readString();
        EntryNumber = in.readString();
        ProductID = in.readString();
        AccountID = in.readString();
        BorrowerID = in.readString();
        UserID = in.readString();
        Name = in.readString();
        Amount = in.readDouble();
        ServiceCharge = in.readDouble();
        AmountPaid = in.readDouble();
        CutOverID = in.readString();
        TxnMedium = in.readString();
        Status = in.readString();
    }

    public static final Creator<GameHistory> CREATOR = new Creator<GameHistory>() {
        @Override
        public GameHistory createFromParcel(Parcel in) {
            return new GameHistory(in);
        }

        @Override
        public GameHistory[] newArray(int size) {
            return new GameHistory[size];
        }
    };

    public String getDrawDay() {
        return DrawDay;
    }

    public String getCutOverDay() {
        return CutOverDay;
    }

    public String getCutOverTimeFrom() {
        return CutOverTimeFrom;
    }

    public String getCutOverTimeTo() {
        return CutOverTimeTo;
    }

    public String getProductName() {
        return ProductName;
    }

    public String getProductDesc() {
        return ProductDesc;
    }

    public int getDigit() {
        return Digit;
    }

    public String getDateTimeIN() {
        return DateTimeIN;
    }

    public String getDateTimeCompleted() {
        return DateTimeCompleted;
    }

    public String getTransactionNo() {
        return TransactionNo;
    }

    public String getEntryNumber() {
        return EntryNumber;
    }

    public String getProductID() {
        return ProductID;
    }

    public String getAccountID() {
        return AccountID;
    }

    public String getBorrowerID() {
        return BorrowerID;
    }

    public String getUserID() {
        return UserID;
    }

    public String getName() {
        return Name;
    }

    public double getAmount() {
        return Amount;
    }

    public double getServiceCharge() {
        return ServiceCharge;
    }

    public double getAmountPaid() {
        return AmountPaid;
    }

    public String getCutOverID() {
        return CutOverID;
    }

    public String getTxnMedium() {
        return TxnMedium;
    }

    public String getStatus() {
        return Status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(DrawDay);
        dest.writeString(CutOverDay);
        dest.writeString(CutOverTimeFrom);
        dest.writeString(CutOverTimeTo);
        dest.writeString(ProductName);
        dest.writeString(ProductDesc);
        dest.writeInt(Digit);
        dest.writeString(DateTimeIN);
        dest.writeString(DateTimeCompleted);
        dest.writeString(TransactionNo);
        dest.writeString(EntryNumber);
        dest.writeString(ProductID);
        dest.writeString(AccountID);
        dest.writeString(BorrowerID);
        dest.writeString(UserID);
        dest.writeString(Name);
        dest.writeDouble(Amount);
        dest.writeDouble(ServiceCharge);
        dest.writeDouble(AmountPaid);
        dest.writeString(CutOverID);
        dest.writeString(TxnMedium);
        dest.writeString(Status);
    }
}
