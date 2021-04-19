package com.goodkredit.myapplication.database.dropoff;

import android.content.ContentValues;
import android.database.Cursor;

import com.goodkredit.myapplication.database.DBUtils;
import com.goodkredit.myapplication.model.dropoff.PaymentRequest;

import java.util.ArrayList;
import java.util.List;

public class PaymentRequestPendingDB {

    public static String TABLE_NAME = "PaymentRequestPending";

    public static String ID = "ID";
    public static String DateTimeIN = "DateTimeIN";
    public static String DateTimeCompleted = "DateTimeCompleted";
    public static String GKTxnNo = "GKTxnNo";
    public static String OrderTxnID = "OrderTxnID";
    public static String MerchantID = "MerchantID";
    public static String MerchantName = "MerchantName";
    public static String MerchantBranchID = "MerchantBranchID";
    public static String MerchantBranchName = "MerchantBranchName";
    public static String BorrowerID = "BorrowerID";
    public static String CustomerMobileNumber = "CustomerMobileNumber";
    public static String CustomerFirstName = "CustomerFirstName";
    public static String CustomerLastName = "CustomerLastName";
    public static String CustomerAddress = "CustomerAddress";
    public static String CustomerEmailAddress = "CustomerEmailAddress";
    public static String OrderDetails = "OrderDetails";
    public static String TotalItems = "TotalItems";
    public static String TotalItemAmount = "TotalItemAmount";
    public static String CustomerServiceCharge = "CustomerServiceCharge";
    public static String OtherCharge = "OtherCharge";
    public static String ResellerDiscount = "ResellerDiscount";
    public static String TotalAmount = "TotalAmount";
    public static String TransactionMedium = "TransactionMedium";
    public static String ProcessID = "ProcessID";
    public static String PaymentReferenceNo = "PaymentReferenceNo";
    public static String PaymentType = "PaymentType";
    public static String BillingID = "BillingID";
    public static String PartnerNetworkID = "PartnerNetworkID";
    public static String PartnerNetworkName = "PartnerNetworkName";
    public static String PartnerOutletID = "PartnerOutletID";
    public static String PartnerOutletName = "PartnerOutletName";
    public static String DateTimePaid = "DateTimePaid";
    public static String PreConsummationSession = "PreConsummationSession";
    public static String Status = "Status";
    public static String Extra1 = "Extra1";
    public static String Extra2 = "Extra2";
    public static String Extra3 = "Extra3";
    public static String Extra4 = "Extra4";
    public static String Notes1 = "Notes1";
    public static String Notes2 = "Notes2";

    public static String CREATE_STATEMENT = DBUtils.CT_IF_NOT_EXISTS + TABLE_NAME + DBUtils.GENERIC_ID + DBUtils.DATA_TYPE_INTEGER + DBUtils.CONSTRAINT_PRIMARY_KEY + DBUtils.CONSTRAINT_AUTOINCREMENT + DBUtils.COMMA +
            ID + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            DateTimeIN + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            DateTimeCompleted + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            GKTxnNo + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            OrderTxnID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            MerchantID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            MerchantName + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            MerchantBranchID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            MerchantBranchName + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            BorrowerID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            CustomerMobileNumber + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            CustomerFirstName + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            CustomerLastName + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            CustomerAddress + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            CustomerEmailAddress + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            OrderDetails + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            TotalItems + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            TotalItemAmount + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            CustomerServiceCharge + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            OtherCharge + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            ResellerDiscount + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            TotalAmount + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            TransactionMedium + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            ProcessID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            PaymentReferenceNo + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            PaymentType + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            BillingID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            PartnerNetworkID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            PartnerNetworkName + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            PartnerOutletID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            PartnerOutletName + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            DateTimePaid + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            PreConsummationSession + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Status + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Extra1+ DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Extra2 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Extra3 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Extra4 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Notes1 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Notes2 + DBUtils.DATA_TYPE_TEXT + DBUtils.GENERIC_STATEMENT_ENDER;

    public static String TRUNCATE_TABLE = DBUtils.DELETE + TABLE_NAME;

    public static ContentValues insert(PaymentRequest data){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, data.getID());
        contentValues.put(DateTimeIN, data.getDateTimeIN());
        contentValues.put(DateTimeCompleted, data.getDateTimeCompleted());
        contentValues.put(GKTxnNo, data.getGKTxnNo());
        contentValues.put(OrderTxnID, data.getOrderTxnID());
        contentValues.put(MerchantID, data.getMerchantID());
        contentValues.put(MerchantName, data.getMerchantName());
        contentValues.put(MerchantBranchID, data.getMerchantBranchID());
        contentValues.put(MerchantBranchName, data.getMerchantBranchName());
        contentValues.put(BorrowerID, data.getBorrowerID());
        contentValues.put(CustomerMobileNumber, data.getCustomerMobileNumber());
        contentValues.put(CustomerFirstName, data.getCustomerFirstName());
        contentValues.put(CustomerLastName, data.getCustomerLastName());
        contentValues.put(CustomerAddress, data.getCustomerAddress());
        contentValues.put(CustomerEmailAddress, data.getCustomerEmailAddress());
        contentValues.put(OrderDetails, data.getOrderDetails());
        contentValues.put(TotalItems, data.getTotalItems());
        contentValues.put(TotalItemAmount, data.getTotalItemAmount());
        contentValues.put(CustomerServiceCharge, data.getCustomerServiceCharge());
        contentValues.put(OtherCharge, data.getOtherCharge());
        contentValues.put(ResellerDiscount, data.getResellerDiscount());
        contentValues.put(TotalAmount, data.getTotalAmount());
        contentValues.put(TransactionMedium, data.getTransactionMedium());
        contentValues.put(ProcessID, data.getProcessID());
        contentValues.put(PaymentReferenceNo, data.getPaymentReferenceNo());
        contentValues.put(PaymentType, data.getPaymentType());
        contentValues.put(BillingID, data.getBillingID());
        contentValues.put(PartnerNetworkID, data.getPartnerNetworkID());
        contentValues.put(PartnerNetworkName, data.getPartnerNetworkName());
        contentValues.put(PartnerOutletID, data.getPartnerOutletID());
        contentValues.put(PartnerOutletName, data.getPartnerOutletName());
        contentValues.put(DateTimePaid, data.getDateTimePaid());
        contentValues.put(PreConsummationSession, data.getPreConsummationSession());
        contentValues.put(Status, data.getStatus());
        contentValues.put(Extra1, data.getExtra1());
        contentValues.put(Extra2, data.getExtra2());
        contentValues.put(Extra3, data.getExtra3());
        contentValues.put(Extra4, data.getExtra4());
        contentValues.put(Notes1, data.getNotes1());
        contentValues.put(Notes2, data.getNotes2());
        return contentValues;
    }

    public static List<PaymentRequest> getPaymentRequestPending(Cursor cursor){
        List<PaymentRequest> pendingrequest = new ArrayList<>();
        if(cursor.getCount() > 0){
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(ID));
                String datetimein = cursor.getString(cursor.getColumnIndex(DateTimeIN));
                String datetimecompleted = cursor.getString(cursor.getColumnIndex(DateTimeCompleted));
                String gktxnno = cursor.getString(cursor.getColumnIndex(GKTxnNo));
                String ordertxnid = cursor.getString(cursor.getColumnIndex(OrderTxnID));
                String merchantid = cursor.getString(cursor.getColumnIndex(MerchantID));
                String merchantname = cursor.getString(cursor.getColumnIndex(MerchantName));
                String merchantbranchid = cursor.getString(cursor.getColumnIndex(MerchantBranchID));
                String merchantbranchname = cursor.getString(cursor.getColumnIndex(MerchantBranchName));
                String borrowerid = cursor.getString(cursor.getColumnIndex(BorrowerID));
                String customermobilenumber = cursor.getString(cursor.getColumnIndex(CustomerMobileNumber));
                String customerfirstname = cursor.getString(cursor.getColumnIndex(CustomerFirstName));
                String customerlastname = cursor.getString(cursor.getColumnIndex(CustomerLastName));
                String customeraddress = cursor.getString(cursor.getColumnIndex(CustomerAddress));
                String customeremailaddress = cursor.getString(cursor.getColumnIndex(CustomerEmailAddress));
                String orderdetails = cursor.getString(cursor.getColumnIndex(OrderDetails));
                int totalitems = cursor.getInt(cursor.getColumnIndex(TotalItems));
                double totalitemamount = cursor.getDouble(cursor.getColumnIndex(TotalItemAmount));
                double customerservicecharge = cursor.getDouble(cursor.getColumnIndex(CustomerServiceCharge));
                double othercharge = cursor.getDouble(cursor.getColumnIndex(OtherCharge));
                double resellerdiscount = cursor.getDouble(cursor.getColumnIndex(ResellerDiscount));
                double totalamount = cursor.getDouble(cursor.getColumnIndex(TotalAmount));
                String transactionmedium = cursor.getString(cursor.getColumnIndex(TransactionMedium));
                String processid = cursor.getString(cursor.getColumnIndex(ProcessID));
                String paymentreferenceno = cursor.getString(cursor.getColumnIndex(PaymentReferenceNo));
                String paymenttype = cursor.getString(cursor.getColumnIndex(PaymentType));
                String billingid = cursor.getString(cursor.getColumnIndex(BillingID));
                String partnernetworkid = cursor.getString(cursor.getColumnIndex(PartnerNetworkID));
                String partnernetworkname = cursor.getString(cursor.getColumnIndex(PartnerNetworkName));
                String partneroutletid = cursor.getString(cursor.getColumnIndex(PartnerOutletID));
                String partneroutletname = cursor.getString(cursor.getColumnIndex(PartnerOutletName));
                String datetimepaid = cursor.getString(cursor.getColumnIndex(DateTimePaid));
                String preconsummationsession = cursor.getString(cursor.getColumnIndex(PreConsummationSession));
                String status = cursor.getString(cursor.getColumnIndex(Status));
                String extra1 = cursor.getString(cursor.getColumnIndex(Extra1));
                String extra2 = cursor.getString(cursor.getColumnIndex(Extra2));
                String extra3 = cursor.getString(cursor.getColumnIndex(Extra3));
                String extra4 = cursor.getString(cursor.getColumnIndex(Extra4));
                String notes1 = cursor.getString(cursor.getColumnIndex(Notes1));
                String note2 = cursor.getString(cursor.getColumnIndex(Notes2));

                pendingrequest.add(new PaymentRequest(
                        id,
                        datetimein,
                        datetimecompleted,
                        gktxnno,
                        ordertxnid,
                        merchantid,
                        merchantname,
                        merchantbranchid,
                        merchantbranchname,
                        borrowerid,
                        customermobilenumber,
                        customerfirstname,
                        customerlastname,
                        customeraddress,
                        customeremailaddress,
                        orderdetails,
                        totalitems,
                        totalitemamount,
                        customerservicecharge,
                        othercharge,
                        resellerdiscount,
                        totalamount,
                        transactionmedium,
                        processid,
                        paymentreferenceno,
                        paymenttype,
                        billingid,
                        partnernetworkid,
                        partnernetworkname,
                        partneroutletid,
                        partneroutletname,
                        datetimepaid,
                        preconsummationsession,
                        status,
                        extra1,
                        extra2,
                        extra3,
                        extra4,
                        notes1,
                        note2
                ));
            }
        }
        return pendingrequest;
    }
}
