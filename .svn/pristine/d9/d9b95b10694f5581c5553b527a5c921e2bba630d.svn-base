package com.goodkredit.myapplication.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.goodkredit.myapplication.model.MedPadalaTransaction;
import com.goodkredit.myapplication.model.MedPadalaVoucherUsed;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ban on 19/03/2018.
 */

public class MedPadalaTransactionsVoucherDetailsDBHelper {

    public static String TABLE_NAME = "medpadalatransactionsvoucherdetailstable";

    public static String TransactionNo = "TransactionNo";
    public static String MerchantTransactionNo = "MerchantTransactionNo";
    public static String ProductID = "ProductID";
    public static String VoucherSeriesNo = "VoucherSeriesNo";
    public static String VoucherCode = "VoucherCode";
    public static String VoucherPIN = "VoucherPIN";
    public static String VoucherDenom = "VoucherDenom";
    public static String AmountConsumed = "AmountConsumed";
    public static String VoucherType = "VoucherType";

    public static String CREATE_STATEMENT = DBUtils.CT_IF_NOT_EXISTS +
            TABLE_NAME +
            DBUtils.GENERIC_ID + DBUtils.DATA_TYPE_INTEGER + DBUtils.CONSTRAINT_PRIMARY_KEY + DBUtils.CONSTRAINT_AUTOINCREMENT + DBUtils.COMMA +
            TransactionNo + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            MerchantTransactionNo + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            ProductID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            VoucherSeriesNo + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            VoucherCode + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            VoucherPIN + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            VoucherDenom + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            AmountConsumed + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            VoucherType + DBUtils.DATA_TYPE_TEXT + DBUtils.GENERIC_STATEMENT_ENDER;

    private String TRUNCATE_TABLE = DBUtils.DELETE + TABLE_NAME;

    public static ContentValues insert(MedPadalaVoucherUsed data) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TransactionNo, data.getTransactionNo());
        contentValues.put(MerchantTransactionNo, data.getMerchantTransactionNo());
        contentValues.put(ProductID, data.getProductID());
        contentValues.put(VoucherSeriesNo, data.getVoucherSeriesNo());
        contentValues.put(VoucherCode, data.getVoucherCode());
        contentValues.put(VoucherPIN, data.getVoucherPIN());
        contentValues.put(VoucherDenom, data.getVoucherDenom());
        contentValues.put(AmountConsumed, data.getAmountConsumed());
        contentValues.put(VoucherType, data.getVoucherType());
        return contentValues;
    }

    public static List<MedPadalaVoucherUsed> getAllMedPadalaTransactionVouchersUsed(Cursor cursor) {
        List<MedPadalaVoucherUsed> transactions = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String strTransactionNo = cursor.getString(cursor.getColumnIndex(TransactionNo));
                String strMerchantTransactionNo = cursor.getString(cursor.getColumnIndex(MerchantTransactionNo));
                String strProductID = cursor.getString(cursor.getColumnIndex(ProductID));
                String strVoucherSeriesNo = cursor.getString(cursor.getColumnIndex(VoucherSeriesNo));
                String strVoucherCode = cursor.getString(cursor.getColumnIndex(VoucherCode));
                String strVoucherPIN = cursor.getString(cursor.getColumnIndex(VoucherPIN));
                String strVoucherDenom = cursor.getString(cursor.getColumnIndex(VoucherDenom));
                double strAmountConsumed = cursor.getDouble(cursor.getColumnIndex(AmountConsumed));
                String strVoucherType = cursor.getString(cursor.getColumnIndex(VoucherType));

                transactions.add(new MedPadalaVoucherUsed(
                        strTransactionNo,
                        strMerchantTransactionNo,
                        strProductID,
                        strVoucherSeriesNo,
                        strVoucherCode,
                        strVoucherPIN,
                        strVoucherDenom,
                        strAmountConsumed,
                        strVoucherType
                ));
            }
        }
        return transactions;
    }

}
