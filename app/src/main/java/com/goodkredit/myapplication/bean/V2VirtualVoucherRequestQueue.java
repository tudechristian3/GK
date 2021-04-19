package com.goodkredit.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by User-PC on 11/3/2017.
 */

public class V2VirtualVoucherRequestQueue implements Parcelable {
    @SerializedName("DateTimeRequested")
    @Expose
    private String DateTimeRequested;
    @SerializedName("DateTimeCompleted")
    @Expose
    private String DateTimeCompleted;
    @SerializedName("VoucherGenerationID")
    @Expose
    private String VoucherGenerationID;
    @SerializedName("PaymentTxnNo")
    @Expose
    private String PaymentTxnNo;
    @SerializedName("PartnerReferenceNo")
    @Expose
    private String PartnerReferenceNo;
    @SerializedName("BorrowingTxnNo")
    @Expose
    private String BorrowingTxnNo;
    @SerializedName("ProcessType")
    @Expose
    private String ProcessType;
    @SerializedName("GuarantorID")
    @Expose
    private String GuarantorID;
    @SerializedName("GuarantorName")
    @Expose
    private String GuarantorName;
    @SerializedName("GuarantorPrefix")
    @Expose
    private String GuarantorPrefix;
    @SerializedName("SubGuarantorID")
    @Expose
    private String SubGuarantorID;
    @SerializedName("SubGuarantorName")
    @Expose
    private String SubGuarantorName;
    @SerializedName("BorrowerID")
    @Expose
    private String BorrowerID;
    @SerializedName("BorrowerName")
    @Expose
    private String BorrowerName;
    @SerializedName("BorrowerMobileNumber")
    @Expose
    private String BorrowerMobileNumber;
    @SerializedName("TotalAmount")
    @Expose
    private double TotalAmount;
    @SerializedName("TotalNoOfVouchers")
    @Expose
    private int TotalNoOfVouchers;
    @SerializedName("XMLDetails")
    @Expose
    private String XMLDetails;
    @SerializedName("Medium")
    @Expose
    private String Medium;
    @SerializedName("IMEI")
    @Expose
    private String IMEI;
    @SerializedName("PartnerNetworkID")
    @Expose
    private String PartnerNetworkID;
    @SerializedName("PartnerOutletID")
    @Expose
    private String PartnerOutletID;
    @SerializedName("DateTimePartnerPaid")
    @Expose
    private String DateTimePartnerPaid;
    @SerializedName("Status")
    @Expose
    private String Status;

    public V2VirtualVoucherRequestQueue(String dateTimeRequested, String dateTimeCompleted, String voucherGenerationID, String paymentTxnNo, String partnerReferenceNo, String borrowingTxnNo, String processType, String guarantorID, String guarantorName, String guarantorPrefix, String subGuarantorID, String subGuarantorName, String borrowerID, String borrowerName, String borrowerMobileNumber, double totalAmount, int totalNoOfVouchers, String XMLDetails, String medium, String IMEI, String partnerNetworkID, String partnerOutletID, String dateTimePartnerPaid, String status) {
        DateTimeRequested = dateTimeRequested;
        DateTimeCompleted = dateTimeCompleted;
        VoucherGenerationID = voucherGenerationID;
        PaymentTxnNo = paymentTxnNo;
        PartnerReferenceNo = partnerReferenceNo;
        BorrowingTxnNo = borrowingTxnNo;
        ProcessType = processType;
        GuarantorID = guarantorID;
        GuarantorName = guarantorName;
        GuarantorPrefix = guarantorPrefix;
        SubGuarantorID = subGuarantorID;
        SubGuarantorName = subGuarantorName;
        BorrowerID = borrowerID;
        BorrowerName = borrowerName;
        BorrowerMobileNumber = borrowerMobileNumber;
        TotalAmount = totalAmount;
        TotalNoOfVouchers = totalNoOfVouchers;
        this.XMLDetails = XMLDetails;
        Medium = medium;
        this.IMEI = IMEI;
        PartnerNetworkID = partnerNetworkID;
        PartnerOutletID = partnerOutletID;
        DateTimePartnerPaid = dateTimePartnerPaid;
        Status = status;
    }

    protected V2VirtualVoucherRequestQueue(Parcel in) {
        DateTimeRequested = in.readString();
        DateTimeCompleted = in.readString();
        VoucherGenerationID = in.readString();
        PaymentTxnNo = in.readString();
        PartnerReferenceNo = in.readString();
        BorrowingTxnNo = in.readString();
        ProcessType = in.readString();
        GuarantorID = in.readString();
        GuarantorName = in.readString();
        GuarantorPrefix = in.readString();
        SubGuarantorID = in.readString();
        SubGuarantorName = in.readString();
        BorrowerID = in.readString();
        BorrowerName = in.readString();
        BorrowerMobileNumber = in.readString();
        TotalAmount = in.readDouble();
        TotalNoOfVouchers = in.readInt();
        XMLDetails = in.readString();
        Medium = in.readString();
        IMEI = in.readString();
        PartnerNetworkID = in.readString();
        PartnerOutletID = in.readString();
        DateTimePartnerPaid = in.readString();
        Status = in.readString();
    }

    public static final Creator<V2VirtualVoucherRequestQueue> CREATOR = new Creator<V2VirtualVoucherRequestQueue>() {
        @Override
        public V2VirtualVoucherRequestQueue createFromParcel(Parcel in) {
            return new V2VirtualVoucherRequestQueue(in);
        }

        @Override
        public V2VirtualVoucherRequestQueue[] newArray(int size) {
            return new V2VirtualVoucherRequestQueue[size];
        }
    };

    public String getDateTimeRequested() {
        return DateTimeRequested;
    }

    public String getDateTimeCompleted() {
        return DateTimeCompleted;
    }

    public String getVoucherGenerationID() {
        return VoucherGenerationID;
    }

    public String getPaymentTxnNo() {
        return PaymentTxnNo;
    }

    public String getPartnerReferenceNo() {
        return PartnerReferenceNo;
    }

    public String getBorrowingTxnNo() {
        return BorrowingTxnNo;
    }

    public String getProcessType() {
        return ProcessType;
    }

    public String getGuarantorID() {
        return GuarantorID;
    }

    public String getGuarantorName() {
        return GuarantorName;
    }

    public String getGuarantorPrefix() {
        return GuarantorPrefix;
    }

    public String getSubGuarantorID() {
        return SubGuarantorID;
    }

    public String getSubGuarantorName() {
        return SubGuarantorName;
    }

    public String getBorrowerID() {
        return BorrowerID;
    }

    public String getBorrowerName() {
        return BorrowerName;
    }

    public String getBorrowerMobileNumber() {
        return BorrowerMobileNumber;
    }

    public double getTotalAmount() {
        return TotalAmount;
    }

    public int getTotalNoOfVouchers() {
        return TotalNoOfVouchers;
    }

    public String getXMLDetails() {
        return XMLDetails;
    }

    public String getMedium() {
        return Medium;
    }

    public String getIMEI() {
        return IMEI;
    }

    public String getPartnerNetworkID() {
        return PartnerNetworkID;
    }

    public String getPartnerOutletID() {
        return PartnerOutletID;
    }

    public String getDateTimePartnerPaid() {
        return DateTimePartnerPaid;
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
        dest.writeString(DateTimeRequested);
        dest.writeString(DateTimeCompleted);
        dest.writeString(VoucherGenerationID);
        dest.writeString(PaymentTxnNo);
        dest.writeString(PartnerReferenceNo);
        dest.writeString(BorrowingTxnNo);
        dest.writeString(ProcessType);
        dest.writeString(GuarantorID);
        dest.writeString(GuarantorName);
        dest.writeString(GuarantorPrefix);
        dest.writeString(SubGuarantorID);
        dest.writeString(SubGuarantorName);
        dest.writeString(BorrowerID);
        dest.writeString(BorrowerName);
        dest.writeString(BorrowerMobileNumber);
        dest.writeDouble(TotalAmount);
        dest.writeInt(TotalNoOfVouchers);
        dest.writeString(XMLDetails);
        dest.writeString(Medium);
        dest.writeString(IMEI);
        dest.writeString(PartnerNetworkID);
        dest.writeString(PartnerOutletID);
        dest.writeString(DateTimePartnerPaid);
        dest.writeString(Status);
    }
}
