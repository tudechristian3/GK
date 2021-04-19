package com.goodkredit.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ban_Lenovo on 7/31/2017.
 */

public class V2BillPaymentQueue implements Parcelable {

    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("DateTimeIN")
    @Expose
    private String DateTimeIN;
    @SerializedName("DateTimeCompleted")
    @Expose
    private String DateTimeCompleted;
    @SerializedName("GKTransactionNo")
    @Expose
    private String GKTransactionNo;
    @SerializedName("ProviderTransactionNo")
    @Expose
    private String ProviderTransactionNo;
    @SerializedName("BillerTransactionNo")
    @Expose
    private String BillerTransactionNo;
    @SerializedName("ServiceProviderID")
    @Expose
    private String ServiceProviderID;
    @SerializedName("BillerCode")
    @Expose
    private String BillerCode;
    @SerializedName("BillerName")
    @Expose
    private String BillerName;
    @SerializedName("ServiceProviderBillerCode")
    @Expose
    private String ServiceProviderBillerCode;
    @SerializedName("ServiceProviderBillerName")
    @Expose
    private String ServiceProviderBillerName;
    @SerializedName("AccountNo")
    @Expose
    private String AccountNo;
    @SerializedName("AccountName")
    @Expose
    private String AccountName;
    @SerializedName("ServiceCode")
    @Expose
    private String ServiceCode;
    @SerializedName("Category")
    @Expose
    private String Category;
    @SerializedName("BillDetails")
    @Expose
    private String BillDetails;
    @SerializedName("AmountDue")
    @Expose
    private double AmountDue;
    @SerializedName("AmountPaid")
    @Expose
    private double AmountPaid;
    @SerializedName("OtherCharges")
    @Expose
    private double OtherCharges;
    @SerializedName("CustomerServiceCharge")
    @Expose
    private double CustomerServiceCharge;
    @SerializedName("ProviderServiceCharge")
    @Expose
    private double ProviderServiceCharge;
    @SerializedName("TotalAmountPaid")
    @Expose
    private double TotalAmountPaid;
    @SerializedName("TxnStatus")
    @Expose
    private String TxnStatus;
    @SerializedName("TxnMedium")
    @Expose
    private String TxnMedium;
    @SerializedName("GuarantorID")
    @Expose
    private String GuarantorID;
    @SerializedName("BorrowerID")
    @Expose
    private String BorrowerID;
    @SerializedName("UserID")
    @Expose
    private String UserID;
    @SerializedName("ProcessID")
    @Expose
    private String ProcessID;
    @SerializedName("PreConsummationSessionID")
    @Expose
    private String PreConsummationSessionID;
    @SerializedName("IMEI")
    @Expose
    private String IMEI;
    @SerializedName("AuthCode")
    @Expose
    private String AuthCode;
    @SerializedName("RetailerWalletPreBalance")
    @Expose
    private double RetailerWalletPreBalance;
    @SerializedName("RetailerWalletPostBalance")
    @Expose
    private double RetailerWalletPostBalance;
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
    @SerializedName("BillerLogoURL")
    @Expose
    private String BillerLogo;

    public static final Creator<V2BillPaymentQueue> CREATOR = new Creator<V2BillPaymentQueue>() {
        @Override
        public V2BillPaymentQueue createFromParcel(Parcel in) {
            return new V2BillPaymentQueue(in);
        }

        @Override
        public V2BillPaymentQueue[] newArray(int size) {
            return new V2BillPaymentQueue[size];
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
        dest.writeString(DateTimeCompleted);
        dest.writeString(GKTransactionNo);
        dest.writeString(ProviderTransactionNo);
        dest.writeString(BillerTransactionNo);
        dest.writeString(ServiceProviderID);
        dest.writeString(BillerCode);
        dest.writeString(BillerName);
        dest.writeString(ServiceProviderBillerCode);
        dest.writeString(ServiceProviderBillerName);
        dest.writeString(AccountNo);
        dest.writeString(AccountName);
        dest.writeString(ServiceCode);
        dest.writeString(Category);
        dest.writeString(BillDetails);
        dest.writeDouble(AmountDue);
        dest.writeDouble(AmountPaid);
        dest.writeDouble(OtherCharges);
        dest.writeDouble(CustomerServiceCharge);
        dest.writeDouble(ProviderServiceCharge);
        dest.writeDouble(TotalAmountPaid);
        dest.writeString(TxnStatus);
        dest.writeString(TxnMedium);
        dest.writeString(GuarantorID);
        dest.writeString(BorrowerID);
        dest.writeString(UserID);
        dest.writeString(ProcessID);
        dest.writeString(PreConsummationSessionID);
        dest.writeString(IMEI);
        dest.writeString(AuthCode);
        dest.writeDouble(RetailerWalletPreBalance);
        dest.writeDouble(RetailerWalletPostBalance);
        dest.writeString(Extra1);
        dest.writeString(Extra2);
        dest.writeString(Extra3);
        dest.writeString(Extra4);
        dest.writeString(Notes1);
        dest.writeString(Notes2);

        dest.writeString(BillerLogo);
    }

    protected V2BillPaymentQueue(Parcel in) {
        ID = in.readInt();
        DateTimeIN = in.readString();
        DateTimeCompleted = in.readString();
        GKTransactionNo = in.readString();
        ProviderTransactionNo = in.readString();
        BillerTransactionNo = in.readString();
        ServiceProviderID = in.readString();
        BillerCode = in.readString();
        BillerName = in.readString();
        ServiceProviderBillerCode = in.readString();
        ServiceProviderBillerName = in.readString();
        AccountNo = in.readString();
        AccountName = in.readString();
        ServiceCode = in.readString();
        Category = in.readString();
        BillDetails = in.readString();
        AmountDue = in.readDouble();
        AmountPaid = in.readDouble();
        OtherCharges = in.readDouble();
        CustomerServiceCharge = in.readDouble();
        ProviderServiceCharge = in.readDouble();
        TotalAmountPaid = in.readDouble();
        TxnStatus = in.readString();
        TxnMedium = in.readString();
        GuarantorID = in.readString();
        BorrowerID = in.readString();
        UserID = in.readString();
        ProcessID = in.readString();
        PreConsummationSessionID = in.readString();
        IMEI = in.readString();
        AuthCode = in.readString();
        RetailerWalletPreBalance = in.readDouble();
        RetailerWalletPostBalance = in.readDouble();
        Extra1 = in.readString();
        Extra2 = in.readString();
        Extra3 = in.readString();
        Extra4 = in.readString();
        Notes1 = in.readString();
        Notes2 = in.readString();

        BillerLogo = in.readString();
    }

    public V2BillPaymentQueue(int ID, String dateTimeIN, String dateTimeCompleted, String GKTransactionNo,
                              String providerTransactionNo, String billerTransactionNo, String serviceProviderID,
                              String billerCode, String billerName, String serviceProviderBillerCode,
                              String serviceProviderBillerName, String accountNo, String accountName,
                              String serviceCode, String category, String billDetails, double amountDue,
                              double amountPaid, double otherCharges, double customerServiceCharge,
                              double providerServiceCharge, double totalAmountPaid, String txnStatus,
                              String txnMedium, String guarantorID, String borrowerID, String userID,
                              String processID, String preConsummationSessionID, String IMEI, String authCode,
                              double retailerWalletPreBalance, double retailerWalletPostBalance, String extra1,
                              String extra2, String extra3, String extra4, String notes1, String notes2, String billerLogo) {
        this.ID = ID;
        DateTimeIN = dateTimeIN;
        DateTimeCompleted = dateTimeCompleted;
        this.GKTransactionNo = GKTransactionNo;
        ProviderTransactionNo = providerTransactionNo;
        BillerTransactionNo = billerTransactionNo;
        ServiceProviderID = serviceProviderID;
        BillerCode = billerCode;
        BillerName = billerName;
        ServiceProviderBillerCode = serviceProviderBillerCode;
        ServiceProviderBillerName = serviceProviderBillerName;
        AccountNo = accountNo;
        AccountName = accountName;
        ServiceCode = serviceCode;
        Category = category;
        BillDetails = billDetails;
        AmountDue = amountDue;
        AmountPaid = amountPaid;
        OtherCharges = otherCharges;
        CustomerServiceCharge = customerServiceCharge;
        ProviderServiceCharge = providerServiceCharge;
        TotalAmountPaid = totalAmountPaid;
        TxnStatus = txnStatus;
        TxnMedium = txnMedium;
        GuarantorID = guarantorID;
        BorrowerID = borrowerID;
        UserID = userID;
        ProcessID = processID;
        PreConsummationSessionID = preConsummationSessionID;
        this.IMEI = IMEI;
        AuthCode = authCode;
        RetailerWalletPreBalance = retailerWalletPreBalance;
        RetailerWalletPostBalance = retailerWalletPostBalance;
        Extra1 = extra1;
        Extra2 = extra2;
        Extra3 = extra3;
        Extra4 = extra4;
        Notes1 = notes1;
        Notes2 = notes2;
        BillerLogo = billerLogo;
    }

    public int getID() {
        return ID;
    }

    public String getDateTimeIN() {
        return DateTimeIN;
    }

    public String getDateTimeCompleted() {
        return DateTimeCompleted;
    }

    public String getGKTransactionNo() {
        return GKTransactionNo;
    }

    public String getProviderTransactionNo() {
        return ProviderTransactionNo;
    }

    public String getBillerTransactionNo() {
        return BillerTransactionNo;
    }

    public String getServiceProviderID() {
        return ServiceProviderID;
    }

    public String getBillerCode() {
        return BillerCode;
    }

    public String getBillerName() {
        return BillerName;
    }

    public String getServiceProviderBillerCode() {
        return ServiceProviderBillerCode;
    }

    public String getServiceProviderBillerName() {
        return ServiceProviderBillerName;
    }

    public String getAccountNo() {
        return AccountNo;
    }

    public String getAccountName() {
        return AccountName;
    }

    public String getServiceCode() {
        return ServiceCode;
    }

    public String getCategory() {
        return Category;
    }

    public String getBillDetails() {
        return BillDetails;
    }

    public double getAmountDue() {
        return AmountDue;
    }

    public double getAmountPaid() {
        return AmountPaid;
    }

    public double getOtherCharges() {
        return OtherCharges;
    }

    public double getCustomerServiceCharge() {
        return CustomerServiceCharge;
    }

    public double getProviderServiceCharge() {
        return ProviderServiceCharge;
    }

    public double getTotalAmountPaid() {
        return TotalAmountPaid;
    }

    public String getTxnStatus() {
        return TxnStatus;
    }

    public String getTxnMedium() {
        return TxnMedium;
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

    public String getProcessID() {
        return ProcessID;
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

    public double getRetailerWalletPreBalance() {
        return RetailerWalletPreBalance;
    }

    public double getRetailerWalletPostBalance() {
        return RetailerWalletPostBalance;
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

    public String getBillerLogo() {
        return BillerLogo;
    }
}
