package com.goodkredit.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ban_Lenovo on 12/28/2016.
 */

public class Voucher implements Parcelable {

    public static final String KEY_MAIN_VOUCHER = "MainVoucherRecyclerView";
    public static final String KEY_PAYOUTONE_VOUCHER = "PayoutOneVoucherRecyclerView";
    public static final String KEY_VOUCHER_OBJECT = "VoucherObject";
    public static final String KEY_VOUCHERSERIALNO = "key_voucherserialno";
    public static final String KEY_VOUCHERCODE = "key_vouchercode";


    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("DateTimeIN")
    @Expose
    private String DateTimeIN;
    @SerializedName("DateTimeRelease")
    @Expose
    private String DateTimeRelease;
    @SerializedName("GuarantorID")
    @Expose
    private String GuarantorID;
    @SerializedName("SubGuarantorID")
    @Expose
    private String SubGuarantorID;
    @SerializedName("OriginalBorrowerID")
    @Expose
    private String OriginalBorrowerID;
    @SerializedName("BorrowerID")
    @Expose
    private String BorrowerID;
    @SerializedName("ProductID")
    @Expose
    private String ProductID;
    @SerializedName("BatchNo")
    @Expose
    private String BatchNo;
    @SerializedName("VoucherSerialNo")
    @Expose
    private String VoucherSerialNo;
    @SerializedName("VoucherCode")
    @Expose
    private String VoucherCode;
    @SerializedName("VoucherPIN")
    @Expose
    private String VoucherPIN;
    @SerializedName("VoucherDenom")
    @Expose
    private double VoucherDenom;
    @SerializedName("isUniversal")
    @Expose
    private int isUniversal;
    @SerializedName("isPartiallyUsed")
    @Expose
    private int isPartiallyUsed;
    @SerializedName("RemainingBalance")
    @Expose
    private double RemainingBalance;
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
    @SerializedName("BarCode")
    @Expose
    private String BarCode;
    @SerializedName("GroupBarCode")
    @Expose
    private String GroupBarCode;
    @SerializedName("GroupName")
    @Expose
    private String GroupName;
    @SerializedName("GroupVoucherCode")
    @Expose
    private String GroupVoucherCode;
    @SerializedName("GroupVoucherPIN")
    @Expose
    private String GroupVoucherPIN;
    @SerializedName("GroupBalance")
    @Expose
    private String GroupBalance;

    @SerializedName("GroupVoucherSession")
    @Expose
    private String GroupVoucherSession;

    @SerializedName("lastupdated")
    @Expose
    private String lastupdated;

    @SerializedName("TransactionNo")
    @Expose
    private String TransactionNo;

    @SerializedName("isTransferable")
    @Expose
    private String isTransferable;

    @SerializedName("PayToTheOrderOf")
    @Expose
    private String PayToTheOrderOf;

    @SerializedName("Particulars")
    @Expose
    private String Particulars;

    @SerializedName("ReleaseTxnNo")
    @Expose
    private String ReleaseTxnNo;

    @SerializedName("IssuedBy")
    @Expose
    private String IssuedBy;

    @SerializedName("ApprovedBy")
    @Expose
    private String ApprovedBy;

    @SerializedName("DateTimeIssued")
    @Expose
    private String DateTimeIssued;

    @SerializedName("DateTimeApproved")
    @Expose
    private String DateTimeApproved;
    @SerializedName("RFIDNumber")
    @Expose
    private String RFIDNumber;
    @SerializedName("RFIDCardNumber")
    @Expose
    private String RFIDCardNumber;
    @SerializedName("RFIDPIN")
    @Expose
    private String RFIDPIN;
    @SerializedName("LastAddedDateTime")
    @Expose
    private String LastAddedDateTime;
    @SerializedName("LastDeductedDateTime")
    @Expose
    private String LastDeductedDateTime;


    public Voucher(String productID, String voucherSerialNo, double voucherDenom, double remainingBalance, String voucherCode,
                   String voucherPIN, String lastupdated, String barCode, String groupBarCode, String groupName, String groupVoucherCode,
                   String groupVoucherPIN, String groupBalance, String groupVoucherSession, String isPrepaid, String isTransferable,
                   String payToTheOrderOf, String particulars, String releaseTxnNo, String issuedBy, String approvedBy, String dateTimeIssued,
                   String dateTimeApproved, String rfidnumber, String rfidcardnumber, String rfidpin, String lastAddedDateTime, String lastDeductedDateTime  ) {
        ProductID = productID;
        VoucherSerialNo = voucherSerialNo;
        VoucherCode = voucherCode;
        VoucherPIN = voucherPIN;
        VoucherDenom = voucherDenom;
        RemainingBalance = remainingBalance;
        BarCode = barCode;
        GroupBarCode = groupBarCode;
        GroupName = groupName;
        GroupVoucherCode = groupVoucherCode;
        GroupVoucherPIN = groupVoucherPIN;
        GroupBalance = groupBalance;
        GroupVoucherSession = groupVoucherSession;
        this.lastupdated = lastupdated;
        Extra3 = isPrepaid;
        this.isTransferable = isTransferable;
        PayToTheOrderOf = payToTheOrderOf;
        Particulars = particulars;
        ReleaseTxnNo = releaseTxnNo;
        IssuedBy = issuedBy;
        ApprovedBy = approvedBy;
        DateTimeIssued = dateTimeIssued;
        DateTimeApproved = dateTimeApproved;
        RFIDNumber = rfidnumber;
        RFIDCardNumber = rfidcardnumber;
        RFIDPIN = rfidpin;
        LastAddedDateTime = lastAddedDateTime;
        LastDeductedDateTime = lastDeductedDateTime;
    }

    public Voucher(String transactionNo, String productID, String voucherCode, double voucherDenom, String serialNumber) {
        TransactionNo = transactionNo;
        ProductID = productID;
        VoucherCode = voucherCode;
        VoucherDenom = voucherDenom;
        VoucherSerialNo = serialNumber;
    }

    public Voucher(String productID, String voucherCode, double voucherDenom, String serialNumber) {
        ProductID = productID;
        VoucherCode = voucherCode;
        VoucherDenom = voucherDenom;
        VoucherSerialNo = serialNumber;
    }

    public Voucher(String productID, String voucherSerialNo, double voucherDenom, double remainingBalance, String voucherCode,
                   String voucherPIN, String lastupdated, String barCode, String groupBarCode, String groupName, String groupVoucherCode,
                   String groupVoucherPIN, String groupBalance, String groupVoucherSession, String isPrepaid, String isTransferable) {
        ProductID = productID;
        VoucherSerialNo = voucherSerialNo;
        VoucherCode = voucherCode;
        VoucherPIN = voucherPIN;
        VoucherDenom = voucherDenom;
        RemainingBalance = remainingBalance;
        BarCode = barCode;
        GroupBarCode = groupBarCode;
        GroupName = groupName;
        GroupVoucherCode = groupVoucherCode;
        GroupVoucherPIN = groupVoucherPIN;
        GroupBalance = groupBalance;
        GroupVoucherSession = groupVoucherSession;
        this.lastupdated = lastupdated;
        Extra3 = isPrepaid;
        this.isTransferable = isTransferable;
    }

    protected Voucher(Parcel in) {
        ID = in.readInt();
        DateTimeIN = in.readString();
        DateTimeRelease = in.readString();
        GuarantorID = in.readString();
        SubGuarantorID = in.readString();
        OriginalBorrowerID = in.readString();
        BorrowerID = in.readString();
        ProductID = in.readString();
        BatchNo = in.readString();
        VoucherSerialNo = in.readString();
        VoucherCode = in.readString();
        VoucherPIN = in.readString();
        VoucherDenom = in.readDouble();
        isUniversal = in.readInt();
        isPartiallyUsed = in.readInt();
        RemainingBalance = in.readDouble();
        Status = in.readString();
        Extra1 = in.readString();
        Extra2 = in.readString();
        Extra3 = in.readString();
        Extra4 = in.readString();
        Notes1 = in.readString();
        Notes2 = in.readString();
        BarCode = in.readString();
        GroupBarCode = in.readString();
        GroupName = in.readString();
        GroupVoucherCode = in.readString();
        GroupVoucherPIN = in.readString();
        GroupBalance = in.readString();
        GroupVoucherSession = in.readString();
        lastupdated = in.readString();
        TransactionNo = in.readString();
        isTransferable = in.readString();
        PayToTheOrderOf = in.readString();
        Particulars = in.readString();
        ReleaseTxnNo = in.readString();
        IssuedBy = in.readString();
        ApprovedBy = in.readString();
        DateTimeIssued = in.readString();
        DateTimeApproved = in.readString();
        RFIDNumber = in.readString();
        RFIDCardNumber = in.readString();
        RFIDPIN = in.readString();
        LastAddedDateTime = in.readString();
        LastDeductedDateTime = in.readString();
    }

    //PARCELABLE
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(DateTimeIN);
        dest.writeString(DateTimeRelease);
        dest.writeString(GuarantorID);
        dest.writeString(SubGuarantorID);
        dest.writeString(OriginalBorrowerID);
        dest.writeString(BorrowerID);
        dest.writeString(ProductID);
        dest.writeString(BatchNo);
        dest.writeString(VoucherSerialNo);
        dest.writeString(VoucherCode);
        dest.writeString(VoucherPIN);
        dest.writeDouble(VoucherDenom);
        dest.writeInt(isUniversal);
        dest.writeInt(isPartiallyUsed);
        dest.writeDouble(RemainingBalance);
        dest.writeString(Status);
        dest.writeString(Extra1);
        dest.writeString(Extra2);
        dest.writeString(Extra3);
        dest.writeString(Extra4);
        dest.writeString(Notes1);
        dest.writeString(Notes2);
        dest.writeString(BarCode);
        dest.writeString(GroupBarCode);
        dest.writeString(GroupName);
        dest.writeString(GroupVoucherCode);
        dest.writeString(GroupVoucherPIN);
        dest.writeString(GroupBalance);
        dest.writeString(GroupVoucherSession);
        dest.writeString(lastupdated);
        dest.writeString(TransactionNo);
        dest.writeString(isTransferable);
        dest.writeString(PayToTheOrderOf);
        dest.writeString(Particulars);
        dest.writeString(ReleaseTxnNo);
        dest.writeString(IssuedBy);
        dest.writeString(ApprovedBy);
        dest.writeString(DateTimeIssued);
        dest.writeString(DateTimeApproved);
        dest.writeString(RFIDNumber);
        dest.writeString(RFIDCardNumber);
        dest.writeString(RFIDPIN);
        dest.writeString(LastAddedDateTime);
        dest.writeString(LastDeductedDateTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Voucher> CREATOR = new Creator<Voucher>() {
        @Override
        public Voucher createFromParcel(Parcel in) {
            return new Voucher(in);
        }

        @Override
        public Voucher[] newArray(int size) {
            return new Voucher[size];
        }
    };

    //SETTERS
    public void setRemainingBalance(double remainingBalance) {
        RemainingBalance = remainingBalance;
    }

    //GETTERS
    public int getID() {
        return ID;
    }

    public String getDateTimeIN() {
        return DateTimeIN;
    }

    public String getDateTimeRelease() {
        return DateTimeRelease;
    }

    public String getGuarantorID() {
        return GuarantorID;
    }

    public String getSubGuarantorID() {
        return SubGuarantorID;
    }

    public String getOriginalBorrowerID() {
        return OriginalBorrowerID;
    }

    public String getBorrowerID() {
        return BorrowerID;
    }

    public String getProductID() {
        return ProductID;
    }

    public String getBatchNo() {
        return BatchNo;
    }

    public String getVoucherSerialNo() {
        return VoucherSerialNo;
    }

    public String getVoucherCode() {
        return VoucherCode;
    }

    public String getVoucherPIN() {
        return VoucherPIN;
    }

    public double getVoucherDenom() {
        return VoucherDenom;
    }

    public int getIsUniversal() {
        return isUniversal;
    }

    public int getIsPartiallyUsed() {
        return isPartiallyUsed;
    }

    public double getRemainingBalance() {
        return RemainingBalance;
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

    public String getBarCode() {
        return BarCode;
    }

    public String getGroupBarCode() {
        return GroupBarCode;
    }

    public String getGroupName() {
        return GroupName;
    }

    public String getGroupVoucherCode() {
        return GroupVoucherCode;
    }

    public String getGroupVoucherPIN() {
        return GroupVoucherPIN;
    }

    public String getGroupBalance() {
        return GroupBalance;
    }

    public String getGroupVoucherSession() {
        return GroupVoucherSession;
    }

    public String getLastupdated() {
        return lastupdated;
    }

    public String getTransactionNo() {
        return TransactionNo;
    }

    public String getIsTransferable() {
        return isTransferable;
    }

    public String getPayToTheOrderOf() {
        return PayToTheOrderOf;
    }

    public String getParticulars() {
        return Particulars;
    }

    public String getReleaseTxnNo() {
        return ReleaseTxnNo;
    }

    public String getIssuedBy() {
        return IssuedBy;
    }

    public String getApprovedBy() {
        return ApprovedBy;
    }

    public String getDateTimeIssued() {
        return DateTimeIssued;
    }

    public String getDateTimeApproved() {
        return DateTimeApproved;
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

    public String getLastAddedDateTime() {
        return LastAddedDateTime;
    }

    public String getLastDeductedDateTime() {
        return LastDeductedDateTime;
    }
}
