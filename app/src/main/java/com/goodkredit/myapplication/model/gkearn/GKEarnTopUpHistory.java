package com.goodkredit.myapplication.model.gkearn;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class GKEarnTopUpHistory implements Parcelable {
    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("DateTimeIN")
    @Expose
    private String DateTimeIN;
    @SerializedName("DateTimeCompleted")
    @Expose
    private String DateTimeCompleted;
    @SerializedName("TopupTransactionNo")
    @Expose
    private String TopupTransactionNo;
    @SerializedName("BorrowerID")
    @Expose
    private String BorrowerID;
    @SerializedName("Name")
    @Expose
    private String Name;
    @SerializedName("MobileNumber")
    @Expose
    private String MobileNumber;
    @SerializedName("Amount")
    @Expose
    private double Amount;
    @SerializedName("ServiceCharge")
    @Expose
    private double ServiceCharge;
    @SerializedName("TotalAmount")
    @Expose
    private double TotalAmount;
    @SerializedName("PointsPreBalance")
    @Expose
    private double PointsPreBalance;
    @SerializedName("PointsPostBalance")
    @Expose
    private double PointsPostBalance;
    @SerializedName("GKPaymentReferenceNo")
    @Expose
    private String GKPaymentReferenceNo;
    @SerializedName("PaymentType")
    @Expose
    private String PaymentType;
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
    @SerializedName("DateTimePartnerPaid")
    @Expose
    private String DateTimePartnerPaid;
    @SerializedName("PreConsummationSession")
    @Expose
    private String PreConsummationSession;
    @SerializedName("Status")
    @Expose
    private String Status;
    public GKEarnTopUpHistory(int ID, String dateTimeIN, String dateTimeCompleted, String topupTransactionNo, String borrowerID,
                              String name, String mobileNumber, double amount, double serviceCharge, double totalAmount,
                              double pointsPreBalance, double pointsPostBalance, String GKPaymentReferenceNo, String paymentType,
                              String partnerNetworkID, String partnerNetworkName, String partnerOutletID, String partnerOutletName,
                              String dateTimePartnerPaid, String preConsummationSession, String status) {
        this.ID = ID;
        DateTimeIN = dateTimeIN;
        DateTimeCompleted = dateTimeCompleted;
        TopupTransactionNo = topupTransactionNo;
        BorrowerID = borrowerID;
        Name = name;
        MobileNumber = mobileNumber;
        Amount = amount;
        ServiceCharge = serviceCharge;
        TotalAmount = totalAmount;
        PointsPreBalance = pointsPreBalance;
        PointsPostBalance = pointsPostBalance;
        this.GKPaymentReferenceNo = GKPaymentReferenceNo;
        PaymentType = paymentType;
        PartnerNetworkID = partnerNetworkID;
        PartnerNetworkName = partnerNetworkName;
        PartnerOutletID = partnerOutletID;
        PartnerOutletName = partnerOutletName;
        DateTimePartnerPaid = dateTimePartnerPaid;
        PreConsummationSession = preConsummationSession;
        Status = status;
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
    public String getTopupTransactionNo() {
        return TopupTransactionNo;
    }
    public String getBorrowerID() {
        return BorrowerID;
    }
    public String getName() {
        return Name;
    }
    public String getMobileNumber() {
        return MobileNumber;
    }
    public double getAmount() {
        return Amount;
    }
    public double getServiceCharge() {
        return ServiceCharge;
    }
    public double getTotalAmount() {
        return TotalAmount;
    }
    public double getPointsPreBalance() {
        return PointsPreBalance;
    }
    public double getPointsPostBalance() {
        return PointsPostBalance;
    }
    public String getGKPaymentReferenceNo() {
        return GKPaymentReferenceNo;
    }
    public String getPaymentType() {
        return PaymentType;
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
    public String getDateTimePartnerPaid() {
        return DateTimePartnerPaid;
    }
    public String getPreConsummationSession() {
        return PreConsummationSession;
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
        dest.writeInt(this.ID);
        dest.writeString(this.DateTimeIN);
        dest.writeString(this.DateTimeCompleted);
        dest.writeString(this.TopupTransactionNo);
        dest.writeString(this.BorrowerID);
        dest.writeString(this.Name);
        dest.writeString(this.MobileNumber);
        dest.writeDouble(this.Amount);
        dest.writeDouble(this.ServiceCharge);
        dest.writeDouble(this.TotalAmount);
        dest.writeDouble(this.PointsPreBalance);
        dest.writeDouble(this.PointsPostBalance);
        dest.writeString(this.GKPaymentReferenceNo);
        dest.writeString(this.PaymentType);
        dest.writeString(this.PartnerNetworkID);
        dest.writeString(this.PartnerNetworkName);
        dest.writeString(this.PartnerOutletID);
        dest.writeString(this.PartnerOutletName);
        dest.writeString(this.DateTimePartnerPaid);
        dest.writeString(this.PreConsummationSession);
        dest.writeString(this.Status);
    }
    protected GKEarnTopUpHistory(Parcel in) {
        this.ID = in.readInt();
        this.DateTimeIN = in.readString();
        this.DateTimeCompleted = in.readString();
        this.TopupTransactionNo = in.readString();
        this.BorrowerID = in.readString();
        this.Name = in.readString();
        this.MobileNumber = in.readString();
        this.Amount = in.readDouble();
        this.ServiceCharge = in.readDouble();
        this.TotalAmount = in.readDouble();
        this.PointsPreBalance = in.readDouble();
        this.PointsPostBalance = in.readDouble();
        this.GKPaymentReferenceNo = in.readString();
        this.PaymentType = in.readString();
        this.PartnerNetworkID = in.readString();
        this.PartnerNetworkName = in.readString();
        this.PartnerOutletID = in.readString();
        this.PartnerOutletName = in.readString();
        this.DateTimePartnerPaid = in.readString();
        this.PreConsummationSession = in.readString();
        this.Status = in.readString();
    }
    public static final Creator<GKEarnTopUpHistory> CREATOR = new Creator<GKEarnTopUpHistory>() {
        @Override
        public GKEarnTopUpHistory createFromParcel(Parcel source) {
            return new GKEarnTopUpHistory(source);
        }
        @Override
        public GKEarnTopUpHistory[] newArray(int size) {
            return new GKEarnTopUpHistory[size];
        }
    };
}