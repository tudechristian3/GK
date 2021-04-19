package com.goodkredit.myapplication.bean;

/**
 * Created by User-PC on 2/12/2018.
 */

public class Petron {
    private String DateTimeCompleted;
    private String TransactionNo;
    private String PetronTxnNo;
    private String GuarantorID;
    private String BorrowerID;
    private String BorrowerMobileNo;
    private String BorrowerName;
    private String BorrowerEmailAddress;
    private String UserID;
    private String CardNo;
    private String CardName;
    private String CardType;
    private String CurrentPoints;
    private double Amount;
    private double ServiceCharge;
    private double OtherCharge;
    private double TotalAmount;
    private String TxnStatus;
    private String TxnMedium;

    public Petron(String guarantorID, String borrowerID, String borrowerMobileNo, String borrowerName, String borrowerEmailAddress, String userID) {
        GuarantorID = guarantorID;
        BorrowerID = borrowerID;
        BorrowerMobileNo = borrowerMobileNo;
        BorrowerName = borrowerName;
        BorrowerEmailAddress = borrowerEmailAddress;
        UserID = userID;
    }

    public String getDateTimeCompleted() {
        return DateTimeCompleted;
    }

    public String getTransactionNo() {
        return TransactionNo;
    }

    public String getPetronTxnNo() {
        return PetronTxnNo;
    }

    public String getGuarantorID() {
        return GuarantorID;
    }

    public String getBorrowerID() {
        return BorrowerID;
    }

    public String getBorrowerMobileNo() {
        return BorrowerMobileNo;
    }

    public String getBorrowerName() {
        return BorrowerName;
    }

    public String getBorrowerEmailAddress() {
        return BorrowerEmailAddress;
    }

    public String getUserID() {
        return UserID;
    }

    public String getCardNo() {
        return CardNo;
    }

    public String getCardName() {
        return CardName;
    }

    public String getCardType() {
        return CardType;
    }

    public String getCurrentPoints() {
        return CurrentPoints;
    }

    public double getAmount() {
        return Amount;
    }

    public double getServiceCharge() {
        return ServiceCharge;
    }

    public double getOtherCharge() {
        return OtherCharge;
    }

    public double getTotalAmount() {
        return TotalAmount;
    }

    public String getTxnStatus() {
        return TxnStatus;
    }

    public String getTxnMedium() {
        return TxnMedium;
    }
}
