package com.goodkredit.myapplication.model.votes;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VotesPending implements Parcelable {

    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("DateTimeIN")
    @Expose
    private String DateTimeIN;
    @SerializedName("PaymentTxnID")
    @Expose
    private String PaymentTxnID;
    @SerializedName("BillingID")
    @Expose
    private String BillingID;
    @SerializedName("EventID")
    @Expose
    private String EventID;
    @SerializedName("MerchantID")
    @Expose
    private String MerchantID;
    @SerializedName("BorrowerID")
    @Expose
    private String BorrowerID;
    @SerializedName("BorrowerName")
    @Expose
    private String BorrowerName;
    @SerializedName("BorrowerMobileNumber")
    @Expose
    private String BorrowerMobileNumber;
    @SerializedName("ParticipantID")
    @Expose
    private String ParticipantID;
    @SerializedName("ParticipantName")
    @Expose
    private String ParticipantName;
    @SerializedName("NumberOfVotes")
    @Expose
    private int NumberOfVotes;
    @SerializedName("Amount")
    @Expose
    private double Amount;
    @SerializedName("CustomerServiceCharge")
    @Expose
    private double CustomerServiceCharge;
    @SerializedName("MerchantServiceCharge")
    @Expose
    private double MerchantServiceCharge;
    @SerializedName("ResellerDiscount")
    @Expose
    private double ResellerDiscount;
    @SerializedName("OtherCharge")
    @Expose
    private double OtherCharge;
    @SerializedName("TotalAmount")
    @Expose
    private double TotalAmount;
    @SerializedName("PaymentDetails")
    @Expose
    private String PaymentDetails;
    @SerializedName("TransactionMedium")
    @Expose
    private String TransactionMedium;
    @SerializedName("ProcessID")
    @Expose
    private String ProcessID;
    @SerializedName("PaymentType")
    @Expose
    private String PaymentType;
    @SerializedName("DateTimePaid")
    @Expose
    private String DateTimePaid;
    @SerializedName("PartnerNetworkID")
    @Expose
    private String PartnerNetworkID;
    @SerializedName("PartnerNetworkName")
    @Expose
    private String PartnerNetworkName;
    @SerializedName("PartnerOutletID")
    @Expose
    private String PartnerOutletID;
    @SerializedName("PartnerOutletName")
    @Expose
    private String PartnerOutletName;
    @SerializedName("PreConsummationSession")
    @Expose
    private String PreConsummationSession;
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

    public VotesPending(int ID, String dateTimeIN, String paymentTxnID, String billingID, String eventID,
                        String merchantID, String borrowerID, String borrowerName, String borrowerMobileNumber,
                        String participantID, String participantName, int numberOfVotes, double amount,
                        double customerServiceCharge, double merchantServiceCharge, double resellerDiscount,
                        double otherCharge, double totalAmount, String paymentDetails, String transactionMedium,
                        String processID, String paymentType, String dateTimePaid, String partnerNetworkID,
                        String partnerNetworkName, String partnerOutletID, String partnerOutletName,
                        String preConsummationSession, String status, String extra1, String extra2, String extra3,
                        String extra4, String notes1, String notes2) {
        this.ID = ID;
        DateTimeIN = dateTimeIN;
        PaymentTxnID = paymentTxnID;
        BillingID = billingID;
        EventID = eventID;
        MerchantID = merchantID;
        BorrowerID = borrowerID;
        BorrowerName = borrowerName;
        BorrowerMobileNumber = borrowerMobileNumber;
        ParticipantID = participantID;
        ParticipantName = participantName;
        NumberOfVotes = numberOfVotes;
        Amount = amount;
        CustomerServiceCharge = customerServiceCharge;
        MerchantServiceCharge = merchantServiceCharge;
        ResellerDiscount = resellerDiscount;
        OtherCharge = otherCharge;
        TotalAmount = totalAmount;
        PaymentDetails = paymentDetails;
        TransactionMedium = transactionMedium;
        ProcessID = processID;
        PaymentType = paymentType;
        DateTimePaid = dateTimePaid;
        PartnerNetworkID = partnerNetworkID;
        PartnerNetworkName = partnerNetworkName;
        PartnerOutletID = partnerOutletID;
        PartnerOutletName = partnerOutletName;
        PreConsummationSession = preConsummationSession;
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

    public String getDateTimeIN() {
        return DateTimeIN;
    }

    public String getPaymentTxnID() {
        return PaymentTxnID;
    }

    public String getBillingID() {
        return BillingID;
    }

    public String getEventID() {
        return EventID;
    }

    public String getMerchantID() {
        return MerchantID;
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

    public String getParticipantID() {
        return ParticipantID;
    }

    public String getParticipantName() {
        return ParticipantName;
    }

    public int getNumberOfVotes() {
        return NumberOfVotes;
    }

    public double getAmount() {
        return Amount;
    }

    public double getCustomerServiceCharge() {
        return CustomerServiceCharge;
    }

    public double getMerchantServiceCharge() {
        return MerchantServiceCharge;
    }

    public double getResellerDiscount() {
        return ResellerDiscount;
    }

    public double getOtherCharge() {
        return OtherCharge;
    }

    public double getTotalAmount() {
        return TotalAmount;
    }

    public String getPaymentDetails() {
        return PaymentDetails;
    }

    public String getTransactionMedium() {
        return TransactionMedium;
    }

    public String getProcessID() {
        return ProcessID;
    }

    public String getPaymentType() {
        return PaymentType;
    }

    public String getDateTimePaid() {
        return DateTimePaid;
    }

    public String getPartnerNetworkID() {
        return PartnerNetworkID;
    }

    public String getPartnerNetworkName() {
        return PartnerNetworkName;
    }

    public String getPartnerOutletID() {
        return PartnerOutletID;
    }

    public String getPartnerOutletName() {
        return PartnerOutletName;
    }

    public String getPreConsummationSession() {
        return PreConsummationSession;
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
        dest.writeString(this.DateTimeIN);
        dest.writeString(this.PaymentTxnID);
        dest.writeString(this.BillingID);
        dest.writeString(this.EventID);
        dest.writeString(this.MerchantID);
        dest.writeString(this.BorrowerID);
        dest.writeString(this.BorrowerName);
        dest.writeString(this.BorrowerMobileNumber);
        dest.writeString(this.ParticipantID);
        dest.writeString(this.ParticipantName);
        dest.writeInt(this.NumberOfVotes);
        dest.writeDouble(this.Amount);
        dest.writeDouble(this.CustomerServiceCharge);
        dest.writeDouble(this.MerchantServiceCharge);
        dest.writeDouble(this.ResellerDiscount);
        dest.writeDouble(this.OtherCharge);
        dest.writeDouble(this.TotalAmount);
        dest.writeString(this.PaymentDetails);
        dest.writeString(this.TransactionMedium);
        dest.writeString(this.ProcessID);
        dest.writeString(this.PaymentType);
        dest.writeString(this.DateTimePaid);
        dest.writeString(this.PartnerNetworkID);
        dest.writeString(this.PartnerNetworkName);
        dest.writeString(this.PartnerOutletID);
        dest.writeString(this.PartnerOutletName);
        dest.writeString(this.PreConsummationSession);
        dest.writeString(this.Status);
        dest.writeString(this.Extra1);
        dest.writeString(this.Extra2);
        dest.writeString(this.Extra3);
        dest.writeString(this.Extra4);
        dest.writeString(this.Notes1);
        dest.writeString(this.Notes2);
    }

    protected VotesPending(Parcel in) {
        this.ID = in.readInt();
        this.DateTimeIN = in.readString();
        this.PaymentTxnID = in.readString();
        this.BillingID = in.readString();
        this.EventID = in.readString();
        this.MerchantID = in.readString();
        this.BorrowerID = in.readString();
        this.BorrowerName = in.readString();
        this.BorrowerMobileNumber = in.readString();
        this.ParticipantID = in.readString();
        this.ParticipantName = in.readString();
        this.NumberOfVotes = in.readInt();
        this.Amount = in.readDouble();
        this.CustomerServiceCharge = in.readDouble();
        this.MerchantServiceCharge = in.readDouble();
        this.ResellerDiscount = in.readDouble();
        this.OtherCharge = in.readDouble();
        this.TotalAmount = in.readDouble();
        this.PaymentDetails = in.readString();
        this.TransactionMedium = in.readString();
        this.ProcessID = in.readString();
        this.PaymentType = in.readString();
        this.DateTimePaid = in.readString();
        this.PartnerNetworkID = in.readString();
        this.PartnerNetworkName = in.readString();
        this.PartnerOutletID = in.readString();
        this.PartnerOutletName = in.readString();
        this.PreConsummationSession = in.readString();
        this.Status = in.readString();
        this.Extra1 = in.readString();
        this.Extra2 = in.readString();
        this.Extra3 = in.readString();
        this.Extra4 = in.readString();
        this.Notes1 = in.readString();
        this.Notes2 = in.readString();
    }

    public static final Parcelable.Creator<VotesPending> CREATOR = new Parcelable.Creator<VotesPending>() {
        @Override
        public VotesPending createFromParcel(Parcel source) {
            return new VotesPending(source);
        }

        @Override
        public VotesPending[] newArray(int size) {
            return new VotesPending[size];
        }
    };
}
