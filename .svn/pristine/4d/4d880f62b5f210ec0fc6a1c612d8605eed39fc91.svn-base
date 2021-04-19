package com.goodkredit.myapplication.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.goodkredit.myapplication.model.MedPadalaTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ban_Lenovo on 2/27/2018.
 */

public class MedPadalaTransactionsDBHelper {

    public static String TABLE_NAME = "medpadalatransactionstable";

    public static String DateTimeCompleted = "DateTimeCompleted";
    public static String TransactionNo = "TransactionNo";
    public static String MedpadalaTxnNo = "MedpadalaTxnNo";
    public static String BorrowerID = "BorrowerID";
    public static String UserID = "UserID";
    public static String RecipientMobileNo = "RecipientMobileNo";
    public static String Amount = "Amount";
    public static String ServiceCharge = "ServiceCharge";
    public static String OtherCharges = "OtherCharges";
    public static String TotalAmount = "TotalAmount";
    public static String TxnMedium = "TxnMedium";
    public static String PreConsummationSessionID = "PreConsummationSessionID";
    public static String GCCode = "GCCode";
    public static String TxnStatus = "TxnStatus";

    public static String CREATE_STATEMENT = DBUtils.CT_IF_NOT_EXISTS +
            TABLE_NAME +
            DBUtils.GENERIC_ID + DBUtils.DATA_TYPE_INTEGER + DBUtils.CONSTRAINT_PRIMARY_KEY + DBUtils.CONSTRAINT_AUTOINCREMENT + DBUtils.COMMA +
            DateTimeCompleted + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            TransactionNo + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            MedpadalaTxnNo + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            BorrowerID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            UserID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            RecipientMobileNo + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Amount + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            ServiceCharge + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            OtherCharges + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            TotalAmount + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            TxnMedium + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            PreConsummationSessionID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            TxnStatus + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            GCCode + DBUtils.DATA_TYPE_TEXT + DBUtils.GENERIC_STATEMENT_ENDER;

    public static String TRUNCATE_TABLE = DBUtils.DELETE + TABLE_NAME;

    public static ContentValues insert(MedPadalaTransaction data) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DateTimeCompleted, data.getDateTimeCompleted());
        contentValues.put(TransactionNo, data.getTransactionNo());
        contentValues.put(MedpadalaTxnNo, data.getMedpadalaTxnNo());
        contentValues.put(BorrowerID, data.getBorrowerID());
        contentValues.put(UserID, data.getUserID());
        contentValues.put(RecipientMobileNo, data.getRecipientMobileNo());
        contentValues.put(Amount, data.getAmount());
        contentValues.put(ServiceCharge, data.getServiceCharge());
        contentValues.put(OtherCharges, data.getOtherCharges());
        contentValues.put(TotalAmount, data.getTotalAmount());
        contentValues.put(TxnMedium, data.getTxnMedium());
        contentValues.put(PreConsummationSessionID, data.getPreConsummationSessionID());
        contentValues.put(GCCode, data.getGCCode());
        contentValues.put(TxnStatus, data.getTxnStatus());
        return contentValues;
    }

    public static List<MedPadalaTransaction> getAllMedPadalaTransactions(Cursor cursor) {
        List<MedPadalaTransaction> transactions = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String strDateTimeCompleted = cursor.getString(cursor.getColumnIndex(DateTimeCompleted));
                String strTransactionNo = cursor.getString(cursor.getColumnIndex(TransactionNo));
                String strMedpadalaTxnNo = cursor.getString(cursor.getColumnIndex(MedpadalaTxnNo));
                String strBorrowerID = cursor.getString(cursor.getColumnIndex(BorrowerID));
                String strUserID = cursor.getString(cursor.getColumnIndex(UserID));
                String strRecipientMobileNo = cursor.getString(cursor.getColumnIndex(RecipientMobileNo));
                double strAmount = cursor.getDouble(cursor.getColumnIndex(Amount));
                double strServiceCharge = cursor.getDouble(cursor.getColumnIndex(ServiceCharge));
                double strOtherCharges = cursor.getDouble(cursor.getColumnIndex(OtherCharges));
                double strTotalAmount = cursor.getDouble(cursor.getColumnIndex(TotalAmount));
                String strTxnMedium = cursor.getString(cursor.getColumnIndex(TxnMedium));
                String strPreConsummationSessionID = cursor.getString(cursor.getColumnIndex(PreConsummationSessionID));
                String strGCCode = cursor.getString(cursor.getColumnIndex(GCCode));
                String strTxnStatus = cursor.getString(cursor.getColumnIndex(TxnStatus));

                transactions.add(new MedPadalaTransaction(
                        strDateTimeCompleted,
                        strTransactionNo,
                        strMedpadalaTxnNo,
                        strBorrowerID,
                        strUserID,
                        strRecipientMobileNo,
                        strAmount,
                        strServiceCharge,
                        strOtherCharges,
                        strTotalAmount,
                        strTxnMedium,
                        strPreConsummationSessionID,
                        strGCCode,
                        strTxnStatus
                ));
            }
        }
        return transactions;
    }
}
