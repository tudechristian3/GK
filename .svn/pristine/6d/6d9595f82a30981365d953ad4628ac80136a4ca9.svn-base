package com.goodkredit.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by User-PC on 8/1/2017.
 */

public class BillsPaymentLogs implements Parcelable {
    @SerializedName("GKTransactionNo")
    @Expose
    private String GKTransactionNo;
    @SerializedName("BillerTransactionNo")
    @Expose
    private String BillerTransactionNo;
    @SerializedName("BillerName")
    @Expose
    private String BillerName;
    @SerializedName("AccountNo")
    @Expose
    private String AccountNo;
    @SerializedName("AccountName")
    @Expose
    private String AccountName;
    @SerializedName("AmountPaid")
    @Expose
    private double AmountPaid;
    @SerializedName("DateTimeCompleted")
    @Expose
    private String DateTimeCompleted;
    @SerializedName("TxnStatus")
    @Expose
    private String TxnStatus;
    @SerializedName("ServiceProviderBillerCode")
    @Expose
    private String ServiceProviderBillerCode;
    @SerializedName("BillerCode")
    @Expose
    private String BillerCode;
    @SerializedName("ServiceCode")
    @Expose
    private String ServiceCode;
    @SerializedName("BillerInfo")
    @Expose
    private String BillerInfo;
    @SerializedName("CustomerSCClass")
    @Expose
    private String CustomerSCClass;
    @SerializedName("BillDetails")
    @Expose
    private String BillDetails;
    @SerializedName("BillerLogoURL")
    @Expose
    private String BillerLogoURL;
    @SerializedName("OtherCharges")
    @Expose
    private double OtherCharges;
    @SerializedName("CustomerServiceCharge")
    @Expose
    private double CustomerServiceCharge;
    @SerializedName("Extra2")
    @Expose
    private String Extra2;
    @SerializedName("TotalAmountPaid")
    @Expose
    private double TotalAmountPaid;


    public BillsPaymentLogs(String GKTransactionNo, String billerTransactionNo, String billerName, String accountNo, String accountName, double amountPaid, String dateTimeCompleted, String txnStatus, String serviceProviderBillerCode, String billerCode, String serviceCode, String billerInfo, String customerSCClass, String billDetails, String billerLogoURL) {
        this.GKTransactionNo = GKTransactionNo;
        BillerTransactionNo = billerTransactionNo;
        BillerName = billerName;
        AccountNo = accountNo;
        AccountName = accountName;
        AmountPaid = amountPaid;
        DateTimeCompleted = dateTimeCompleted;
        TxnStatus = txnStatus;
        ServiceProviderBillerCode = serviceProviderBillerCode;
        BillerCode = billerCode;
        ServiceCode = serviceCode;
        BillerInfo = billerInfo;
        CustomerSCClass = customerSCClass;
        BillDetails = billDetails;
        BillerLogoURL = billerLogoURL;
    }

    public BillsPaymentLogs(String GKTransactionNo, String billerTransactionNo, String billerName, String accountNo, String accountName, double amountPaid, String dateTimeCompleted, String txnStatus, String serviceProviderBillerCode, String billerCode, String serviceCode, String billerInfo, String customerSCClass, String billDetails, String billerLogoURL, double otherCharges, double customerServiceCharge, String extra2, double totalAmountPaid) {
        this.GKTransactionNo = GKTransactionNo;
        BillerTransactionNo = billerTransactionNo;
        BillerName = billerName;
        AccountNo = accountNo;
        AccountName = accountName;
        AmountPaid = amountPaid;
        DateTimeCompleted = dateTimeCompleted;
        TxnStatus = txnStatus;
        ServiceProviderBillerCode = serviceProviderBillerCode;
        BillerCode = billerCode;
        ServiceCode = serviceCode;
        BillerInfo = billerInfo;
        CustomerSCClass = customerSCClass;
        BillDetails = billDetails;
        BillerLogoURL = billerLogoURL;
        OtherCharges = otherCharges;
        CustomerServiceCharge = customerServiceCharge;
        Extra2 = extra2;
        TotalAmountPaid = totalAmountPaid;
    }

    protected BillsPaymentLogs(Parcel in) {
        GKTransactionNo = in.readString();
        BillerTransactionNo = in.readString();
        BillerName = in.readString();
        AccountNo = in.readString();
        AccountName = in.readString();
        AmountPaid = in.readDouble();
        DateTimeCompleted = in.readString();
        TxnStatus = in.readString();
        ServiceProviderBillerCode = in.readString();
        BillerCode = in.readString();
        ServiceCode = in.readString();
        BillerInfo = in.readString();
        CustomerSCClass = in.readString();
        BillDetails = in.readString();
        BillerLogoURL = in.readString();
    }

    public static final Creator<BillsPaymentLogs> CREATOR = new Creator<BillsPaymentLogs>() {
        @Override
        public BillsPaymentLogs createFromParcel(Parcel in) {
            return new BillsPaymentLogs(in);
        }

        @Override
        public BillsPaymentLogs[] newArray(int size) {
            return new BillsPaymentLogs[size];
        }
    };

    public String getGKTransactionNo() {
        return GKTransactionNo;
    }

    public void setGKTransactionNo(String GKTransactionNo) {
        this.GKTransactionNo = GKTransactionNo;
    }

    public String getBillerTransactionNo() {
        return BillerTransactionNo;
    }

    public void setBillerTransactionNo(String billerTransactionNo) {
        BillerTransactionNo = billerTransactionNo;
    }

    public String getBillerName() {
        return BillerName;
    }

    public void setBillerName(String billerName) {
        BillerName = billerName;
    }

    public String getAccountNo() {
        return AccountNo;
    }

    public void setAccountNo(String accountNo) {
        AccountNo = accountNo;
    }

    public String getAccountName() {
        return AccountName;
    }

    public void setAccountName(String accountName) {
        AccountName = accountName;
    }

    public double getAmountPaid() {
        return AmountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        AmountPaid = amountPaid;
    }

    public String getDateTimeCompleted() {
        return DateTimeCompleted;
    }

    public void setDateTimeCompleted(String dateTimeCompleted) {
        DateTimeCompleted = dateTimeCompleted;
    }

    public String getTxnStatus() {
        return TxnStatus;
    }

    public void setTxnStatus(String txnStatus) {
        TxnStatus = txnStatus;
    }

    public String getServiceProviderBillerCode() {
        return ServiceProviderBillerCode;
    }

    public void setServiceProviderBillerCode(String serviceProviderBillerCode) {
        ServiceProviderBillerCode = serviceProviderBillerCode;
    }

    public String getBillerCode() {
        return BillerCode;
    }

    public void setBillerCode(String billerCode) {
        BillerCode = billerCode;
    }

    public String getServiceCode() {
        return ServiceCode;
    }

    public void setServiceCode(String serviceCode) {
        ServiceCode = serviceCode;
    }

    public String getBillerInfo() {
        return BillerInfo;
    }

    public void setBillerInfo(String billerInfo) {
        BillerInfo = billerInfo;
    }

    public String getCustomerSCClass() {
        return CustomerSCClass;
    }

    public void setCustomerSCClass(String customerSCClass) {
        CustomerSCClass = customerSCClass;
    }

    public String getBillDetails() {
        return BillDetails;
    }

    public void setBillDetails(String billDetails) {
        BillDetails = billDetails;
    }

    public String getBillerLogoURL() {
        return BillerLogoURL;
    }

    public void setBillerLogoURL(String billerLogoURL) {
        BillerLogoURL = billerLogoURL;
    }

    public double getOtherCharges() {
        return OtherCharges;
    }

    public double getCustomerServiceCharge() {
        return CustomerServiceCharge;
    }

    public String getExtra2() {
        return Extra2;
    }

    public double getTotalAmountPaid() {
        return TotalAmountPaid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(GKTransactionNo);
        dest.writeString(BillerTransactionNo);
        dest.writeString(BillerName);
        dest.writeString(AccountNo);
        dest.writeString(AccountName);
        dest.writeDouble(AmountPaid);
        dest.writeString(DateTimeCompleted);
        dest.writeString(TxnStatus);
        dest.writeString(ServiceProviderBillerCode);
        dest.writeString(BillerCode);
        dest.writeString(ServiceCode);
        dest.writeString(BillerInfo);
        dest.writeString(CustomerSCClass);
        dest.writeString(BillDetails);
        dest.writeString(BillerLogoURL);
    }
}
