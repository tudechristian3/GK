package com.goodkredit.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by GoodApps on 13/03/2018.
 */

public class FetchStoreOrderList implements Parcelable {
    @SerializedName("DateTimeIN")
    @Expose
    private String DateTimeIN;
    @SerializedName("DateTimeCompleted")
    @Expose
    private String DateTimeCompleted;
    @SerializedName("OrderTxnID")
    @Expose
    private String OrderTxnID;
    @SerializedName("MerchantID")
    @Expose
    private String MerchantID;
    @SerializedName("MerchantName")
    @Expose
    private String MerchantName;
    @SerializedName("GKStoreID")
    @Expose
    private String GKStoreID;
    @SerializedName("GKStoreName")
    @Expose
    private String GKStoreName;
    @SerializedName("BorrowerID")
    @Expose
    private String BorrowerID;
    @SerializedName("BorrowerName")
    @Expose
    private String BorrowerName;
    @SerializedName("UserID")
    @Expose
    private String UserID;
    @SerializedName("CustomerFirstName")
    @Expose
    private String CustomerFirstName;
    @SerializedName("CustomerLastName")
    @Expose
    private String CustomerLastName;
    @SerializedName("CustomerMobileNumber")
    @Expose
    private String CustomerMobileNumber;
    @SerializedName("CustomerEmailAddress")
    @Expose
    private String CustomerEmailAddress;
    @SerializedName("CustomerAddress")
    @Expose
    private String CustomerAddress;
    @SerializedName("Longitude")
    @Expose
    private String Longitude;
    @SerializedName("Latitude")
    @Expose
    private String Latitude;
    @SerializedName("CustomerOtherDetails")
    @Expose
    private String CustomerOtherDetails;
    @SerializedName("OrderDetails")
    @Expose
    private String OrderDetails;
    @SerializedName("TotalItems")
    @Expose
    private int TotalItems;
    @SerializedName("TotalItemAmount")
    @Expose
    private double TotalItemAmount;
    @SerializedName("CustomerServiceCharge")
    @Expose
    private double CustomerServiceCharge;
    @SerializedName("MerchantServiceCharge")
    @Expose
    private double MerchantServiceCharge;
    @SerializedName("ResellerDiscount")
    @Expose
    private double ResellerDiscount;
    @SerializedName("TotalAmount")
    @Expose
    private double TotalAmount;
    @SerializedName("TransactionMedium")
    @Expose
    private String TransactionMedium;
    @SerializedName("APICallBackIndicator")
    @Expose
    private int APICallBackIndicator;
    @SerializedName("ActedBy")
    @Expose
    private String ActedBy;
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

    public FetchStoreOrderList(String dateTimeIN, String dateTimeCompleted, String orderTxnID, String merchantID,
                               String merchantName, String gkStoreID, String gkStoreName, String borrowerID,
                               String borrowerName, String userID, String customerFirstName, String customerLastName,
                               String customerMobileNumber, String customerEmailAddress, String customerAddress,
                               String longitude, String latitude, String customerOtherDetails, String orderDetails,
                               int totalItems, double totalItemAmount, double customerServiceCharge,
                               double merchantServiceCharge, double resellerDiscount, double totalAmount,
                               String transactionMedium, int APICallBackIndicator, String actedBy, String status,
                               String extra1, String extra2, String extra3, String extra4, String notes1, String notes2) {
        DateTimeIN = dateTimeIN;
        DateTimeCompleted = dateTimeCompleted;
        OrderTxnID = orderTxnID;
        MerchantID = merchantID;
        MerchantName = merchantName;
        GKStoreID = gkStoreID;
        GKStoreName = gkStoreName;
        BorrowerID = borrowerID;
        BorrowerName = borrowerName;
        UserID = userID;
        CustomerFirstName = customerFirstName;
        CustomerLastName = customerLastName;
        CustomerMobileNumber = customerMobileNumber;
        CustomerEmailAddress = customerEmailAddress;
        CustomerAddress = customerAddress;
        Longitude = longitude;
        Latitude = latitude;
        CustomerOtherDetails = customerOtherDetails;
        OrderDetails = orderDetails;
        TotalItems = totalItems;
        TotalItemAmount = totalItemAmount;
        CustomerServiceCharge = customerServiceCharge;
        MerchantServiceCharge = merchantServiceCharge;
        ResellerDiscount = resellerDiscount;
        TotalAmount = totalAmount;
        TransactionMedium = transactionMedium;
        this.APICallBackIndicator = APICallBackIndicator;
        ActedBy = actedBy;
        Status = status;
        Extra1 = extra1;
        Extra2 = extra2;
        Extra3 = extra3;
        Extra4 = extra4;
        Notes1 = notes1;
        Notes2 = notes2;
    }

    protected FetchStoreOrderList(Parcel in) {
        DateTimeIN = in.readString();
        DateTimeCompleted = in.readString();
        OrderTxnID = in.readString();
        MerchantID = in.readString();
        MerchantName = in.readString();
        GKStoreID = in.readString();
        GKStoreName = in.readString();
        BorrowerID = in.readString();
        BorrowerName = in.readString();
        UserID = in.readString();
        CustomerFirstName = in.readString();
        CustomerLastName = in.readString();
        CustomerMobileNumber = in.readString();
        CustomerEmailAddress = in.readString();
        CustomerAddress = in.readString();
        Longitude = in.readString();
        Latitude = in.readString();
        CustomerOtherDetails = in.readString();
        OrderDetails = in.readString();
        TotalItems = in.readInt();
        TotalItemAmount = in.readDouble();
        CustomerServiceCharge = in.readDouble();
        MerchantServiceCharge = in.readDouble();
        ResellerDiscount = in.readDouble();
        TotalAmount = in.readDouble();
        TransactionMedium = in.readString();
        APICallBackIndicator = in.readInt();
        ActedBy = in.readString();
        Status = in.readString();
        Extra1 = in.readString();
        Extra2 = in.readString();
        Extra3 = in.readString();
        Extra4 = in.readString();
        Notes1 = in.readString();
        Notes2 = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(DateTimeIN);
        dest.writeString(DateTimeCompleted);
        dest.writeString(OrderTxnID);
        dest.writeString(MerchantID);
        dest.writeString(MerchantName);
        dest.writeString(GKStoreID);
        dest.writeString(GKStoreName);
        dest.writeString(BorrowerID);
        dest.writeString(BorrowerName);
        dest.writeString(UserID);
        dest.writeString(CustomerFirstName);
        dest.writeString(CustomerLastName);
        dest.writeString(CustomerMobileNumber);
        dest.writeString(CustomerEmailAddress);
        dest.writeString(CustomerAddress);
        dest.writeString(Longitude);
        dest.writeString(Latitude);
        dest.writeString(CustomerOtherDetails);
        dest.writeString(OrderDetails);
        dest.writeInt(TotalItems);
        dest.writeDouble(TotalItemAmount);
        dest.writeDouble(CustomerServiceCharge);
        dest.writeDouble(MerchantServiceCharge);
        dest.writeDouble(ResellerDiscount);
        dest.writeDouble(TotalAmount);
        dest.writeString(TransactionMedium);
        dest.writeInt(APICallBackIndicator);
        dest.writeString(ActedBy);
        dest.writeString(Status);
        dest.writeString(Extra1);
        dest.writeString(Extra2);
        dest.writeString(Extra3);
        dest.writeString(Extra4);
        dest.writeString(Notes1);
        dest.writeString(Notes2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FetchStoreOrderList> CREATOR = new Creator<FetchStoreOrderList>() {
        @Override
        public FetchStoreOrderList createFromParcel(Parcel in) {
            return new FetchStoreOrderList(in);
        }

        @Override
        public FetchStoreOrderList[] newArray(int size) {
            return new FetchStoreOrderList[size];
        }
    };

    public String getDateTimeIN() {
        return DateTimeIN;
    }

    public String getDateTimeCompleted() {
        return DateTimeCompleted;
    }

    public String getOrderTxnID() {
        return OrderTxnID;
    }

    public String getMerchantID() {
        return MerchantID;
    }

    public String getMerchantName() {
        return MerchantName;
    }

    public String getGKStoreID() {
        return GKStoreID;
    }

    public String getGKStoreName() {
        return GKStoreName;
    }

    public String getBorrowerID() {
        return BorrowerID;
    }

    public String getBorrowerName() {
        return BorrowerName;
    }

    public String getUserID() {
        return UserID;
    }

    public String getCustomerFirstName() {
        return CustomerFirstName;
    }

    public String getCustomerLastName() {
        return CustomerLastName;
    }

    public String getCustomerMobileNumber() {
        return CustomerMobileNumber;
    }

    public String getCustomerEmailAddress() {
        return CustomerEmailAddress;
    }

    public String getCustomerAddress() {
        return CustomerAddress;
    }

    public String getLongitude() {
        return Longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public String getCustomerOtherDetails() {
        return CustomerOtherDetails;
    }

    public String getOrderDetails() {
        return OrderDetails;
    }

    public int getTotalItems() {
        return TotalItems;
    }

    public double getTotalItemAmount() {
        return TotalItemAmount;
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

    public double getTotalAmount() {
        return TotalAmount;
    }

    public String getTransactionMedium() {
        return TransactionMedium;
    }

    public int getAPICallBackIndicator() {
        return APICallBackIndicator;
    }

    public String getActedBy() {
        return ActedBy;
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
}

