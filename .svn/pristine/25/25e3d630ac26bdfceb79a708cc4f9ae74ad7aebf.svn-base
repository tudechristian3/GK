package com.goodkredit.myapplication.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.goodkredit.myapplication.model.FetchStoreOrderList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GoodApps on 13/03/2018.
 */

public class GKStoreHistoryDBHelper {

    public static String TABLE_NAME = "gkstorehistory";

    public static String DateTimeIN = "DateTimeIN";
    public static String DateTimeCompleted = "DateTimeCompleted";
    public static String OrderTxnID = "OrderTxnID";
    public static String MerchantID = "MerchantID";
    public static String MerchantName = "MerchantName";
    public static String GKStoreID = "GKStoreID";
    public static String GKStoreName = "GKStoreName";
    public static String BorrowerID = "BorrowerID";
    public static String BorrowerName = "BorrowerName";
    public static String UserID = "UserID";
    public static String CustomerFirstName = "CustomerFirstName";
    public static String CustomerLastName = "CustomerLastName";
    public static String CustomerMobileNumber = "CustomerMobileNumber";
    public static String CustomerEmailAddress = "CustomerEmailAddress";
    public static String CustomerAddress = "CustomerAddress";
    public static String Longitude = "Longitude";
    public static String Latitude = "Latitude";
    public static String CustomerOtherDetails = "CustomerOtherDetails";
    public static String OrderDetails = "OrderDetails";
    public static String TotalItems = "TotalItems";
    public static String TotalItemAmount = "TotalItemAmount";
    public static String CustomerServiceCharge = "CustomerServiceCharge";
    public static String MerchantServiceCharge = "MerchantServiceCharge";
    public static String ResellerDiscount = "ResellerDiscount";
    public static String TotalAmount = "TotalAmount";
    public static String TransactionMedium = "TransactionMedium";
    public static String APICallBackIndicator = "APICallBackIndicator";
    public static String ActedBy = "ActedBy";
    public static String Status = "Status";
    public static String Extra1 = "Extra1";
    public static String Extra2 = "Extra2";
    public static String Extra3 = "Extra3";
    public static String Extra4 = "Extra4";
    public static String Notes1 = "Notes1";
    public static String Notes2 = "Notes2";

    public static String CREATE_STATEMENT = DBUtils.CT_IF_NOT_EXISTS +
            TABLE_NAME +
            DBUtils.GENERIC_ID + DBUtils.DATA_TYPE_INTEGER + DBUtils.CONSTRAINT_PRIMARY_KEY + DBUtils.CONSTRAINT_AUTOINCREMENT + DBUtils.COMMA +
            DateTimeIN + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            DateTimeCompleted + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            OrderTxnID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            MerchantID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            MerchantName + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            GKStoreID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            GKStoreName + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            BorrowerID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            BorrowerName + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            UserID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            CustomerFirstName + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            CustomerLastName + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            CustomerMobileNumber + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            CustomerEmailAddress + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            CustomerAddress + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Longitude + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Latitude + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            CustomerOtherDetails + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            OrderDetails + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            TotalItems + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            TotalItemAmount + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            CustomerServiceCharge + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            MerchantServiceCharge + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            ResellerDiscount + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            TotalAmount + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            TransactionMedium + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            APICallBackIndicator + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            ActedBy + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Status + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Extra1 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Extra2 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Extra3 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Extra4 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Notes1 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Notes2 + DBUtils.DATA_TYPE_TEXT + DBUtils.GENERIC_STATEMENT_ENDER;

    private String TRUNCATE_TABLE = DBUtils.DELETE + TABLE_NAME;

    public static ContentValues insert(FetchStoreOrderList data) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DateTimeIN, data.getDateTimeIN());
        contentValues.put(DateTimeCompleted, data.getDateTimeCompleted());
        contentValues.put(OrderTxnID, data.getOrderTxnID());
        contentValues.put(MerchantID, data.getMerchantID());
        contentValues.put(MerchantName, data.getMerchantName());
        contentValues.put(GKStoreID, data.getGKStoreID());
        contentValues.put(GKStoreName, data.getGKStoreName());
        contentValues.put(BorrowerID, data.getBorrowerID());
        contentValues.put(BorrowerName, data.getBorrowerName());
        contentValues.put(UserID, data.getUserID());
        contentValues.put(CustomerFirstName, data.getCustomerFirstName());
        contentValues.put(CustomerLastName, data.getCustomerLastName());
        contentValues.put(CustomerMobileNumber, data.getCustomerMobileNumber());
        contentValues.put(CustomerEmailAddress, data.getCustomerEmailAddress());
        contentValues.put(CustomerAddress, data.getCustomerAddress());
        contentValues.put(Longitude, data.getLongitude());
        contentValues.put(Latitude, data.getLatitude());
        contentValues.put(CustomerOtherDetails, data.getCustomerOtherDetails());
        contentValues.put(OrderDetails, data.getOrderDetails());
        contentValues.put(TotalItems, data.getTotalItems());
        contentValues.put(TotalItemAmount, data.getTotalItemAmount());
        contentValues.put(CustomerServiceCharge, data.getCustomerServiceCharge());
        contentValues.put(MerchantServiceCharge, data.getMerchantServiceCharge());
        contentValues.put(ResellerDiscount, data.getResellerDiscount());
        contentValues.put(TotalAmount, data.getTotalAmount());
        contentValues.put(TransactionMedium, data.getTransactionMedium());
        contentValues.put(APICallBackIndicator, data.getAPICallBackIndicator());
        contentValues.put(ActedBy, data.getActedBy());
        contentValues.put(Status, data.getStatus());
        contentValues.put(Extra1, data.getExtra1());
        contentValues.put(Extra2, data.getExtra2());
        contentValues.put(Extra3, data.getExtra3());
        contentValues.put(Extra4, data.getExtra4());
        contentValues.put(Notes1, data.getNotes1());
        contentValues.put(Notes2, data.getNotes2());

        return contentValues;
    }

    public static List<FetchStoreOrderList> getAllGKStoreTransactions(Cursor cursor) {
        List<FetchStoreOrderList> fetchstoreorderlist = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String strDateTimeIN = cursor.getString(cursor.getColumnIndex(DateTimeIN));
                String strDateTimeCompleted = cursor.getString(cursor.getColumnIndex(DateTimeCompleted));
                String strOrderTxnID = cursor.getString(cursor.getColumnIndex(OrderTxnID));
                String strMerchantID = cursor.getString(cursor.getColumnIndex(MerchantID));
                String strMerchantName = cursor.getString(cursor.getColumnIndex(MerchantName));
                String strGKStoreID = cursor.getString(cursor.getColumnIndex(GKStoreID));
                String strGKStoreName = cursor.getString(cursor.getColumnIndex(GKStoreName));
                String strBorrowerID = cursor.getString(cursor.getColumnIndex(BorrowerID));
                String strBorrowerName = cursor.getString(cursor.getColumnIndex(BorrowerName));
                String strUserID = cursor.getString(cursor.getColumnIndex(UserID));
                String strCustomerFirstName = cursor.getString(cursor.getColumnIndex(CustomerFirstName));
                String strCustomerLastName = cursor.getString(cursor.getColumnIndex(CustomerLastName));
                String strCustomerMobileNumber = cursor.getString(cursor.getColumnIndex(CustomerMobileNumber));
                String strCustomerEmailAddress = cursor.getString(cursor.getColumnIndex(CustomerEmailAddress));
                String strCustomerAddress = cursor.getString(cursor.getColumnIndex(CustomerAddress));
                String strLongitude = cursor.getString(cursor.getColumnIndex(Longitude));
                String strLatitude= cursor.getString(cursor.getColumnIndex(Latitude));
                String strCustomerOtherDetails = cursor.getString(cursor.getColumnIndex(CustomerOtherDetails));
                String strOrderDetails = cursor.getString(cursor.getColumnIndex(OrderDetails));
                int strTotalItems = cursor.getInt(cursor.getColumnIndex(TotalItems));
                double dblTotalItemAmount= cursor.getDouble(cursor.getColumnIndex(TotalItemAmount));
                double dblCustomerServiceCharge = cursor.getDouble(cursor.getColumnIndex(CustomerServiceCharge));
                double dblMerchantServiceCharge = cursor.getDouble(cursor.getColumnIndex(MerchantServiceCharge));
                double dblResellerDiscount = cursor.getDouble(cursor.getColumnIndex(ResellerDiscount));
                double strTotalAmount = cursor.getDouble(cursor.getColumnIndex(TotalAmount));
                String strTransactionMedium= cursor.getString(cursor.getColumnIndex(TransactionMedium));
                int strAPICallBackIndicator = cursor.getInt(cursor.getColumnIndex(APICallBackIndicator));
                String strActedBy = cursor.getString(cursor.getColumnIndex(ActedBy));
                String strStatus = cursor.getString(cursor.getColumnIndex(Status));
                String strExtra1 = cursor.getString(cursor.getColumnIndex(Extra1));
                String strExtra2 = cursor.getString(cursor.getColumnIndex(Extra2));
                String strExtra3 = cursor.getString(cursor.getColumnIndex(Extra3));
                String strExtra4 = cursor.getString(cursor.getColumnIndex(Extra4));
                String strNotes1 = cursor.getString(cursor.getColumnIndex(Notes1));
                String strNotes2 = cursor.getString(cursor.getColumnIndex(Notes2));


                fetchstoreorderlist.add(new FetchStoreOrderList(
                        strDateTimeIN,
                        strDateTimeCompleted,
                        strOrderTxnID,
                        strMerchantID,
                        strMerchantName,
                        strGKStoreID,
                        strGKStoreName,
                        strBorrowerID,
                        strBorrowerName,
                        strUserID,
                        strCustomerFirstName,
                        strCustomerLastName,
                        strCustomerMobileNumber,
                        strCustomerEmailAddress,
                        strCustomerAddress,
                        strLongitude,
                        strLatitude,
                        strCustomerOtherDetails,
                        strOrderDetails,
                        strTotalItems,
                        dblTotalItemAmount,
                        dblCustomerServiceCharge,
                        dblMerchantServiceCharge,
                        dblResellerDiscount,
                        strTotalAmount,
                        strTransactionMedium,
                        strAPICallBackIndicator,
                        strActedBy,
                        strStatus,
                        strExtra1,
                        strExtra2,
                        strExtra3,
                        strExtra4,
                        strNotes1,
                        strNotes2
                        ));
            }
        }
        cursor.close();
        return fetchstoreorderlist;
    }

}
