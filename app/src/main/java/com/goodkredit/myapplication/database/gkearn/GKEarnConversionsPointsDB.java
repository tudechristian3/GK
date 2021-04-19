package com.goodkredit.myapplication.database.gkearn;

import android.content.ContentValues;
import android.database.Cursor;

import com.goodkredit.myapplication.database.DBUtils;
import com.goodkredit.myapplication.model.gkearn.GKEarnConversionPoints;

import java.util.ArrayList;
import java.util.List;

public class GKEarnConversionsPointsDB {
    //Table Name
    public static final String TABLE_GKEARN_CONVERSIONS = "table_gkearn_conversions";

    //Values
    public static final String KEY_ID = "ID";
    public static final String KEY_DATETIMEIN = "DateTimeIN";
    public static final String KEY_DATETIMECOMPLETED = "DateTimeCompleted";
    public static final String KEY_CAMPAIGNID = "CampaignID";
    public static final String KEY_CONVERSIONTXNNO = "ConversionTxnNo";
    public static final String KEY_VOUCHERPROCESSID = "VoucherProcessID";
    public static final String KEY_RELEASINGTXNNO = "ReleasingTxnNo";
    public static final String KEY_BORROWERID = "BorrowerID";
    public static final String KEY_SPONSORID = "SponsorID";
    public static final String KEY_TOTALNOOFVOUCHERS = "TotalNoOfVouchers";
    public static final String KEY_TOTALVOUCHERAMOUNT = "TotalVoucherAmount";
    public static final String KEY_TOTALSC = "TotalSC";
    public static final String KEY_BORROWERPOINTSDEDUCTED = "BorrowerPointsDeducted";
    public static final String KEY_BORROWERPREPOINTS = "BorrowerPrePoints";
    public static final String KEY_BORROWERPOSTPOINTS = "BorrowerPostPoints";
    public static final String KEY_TRANSACTIONMEDIUM = "TransactionMedium";
    public static final String KEY_STATUS = "Status";
    public static final String KEY_EXTRA1 = "Extra1";
    public static final String KEY_EXTRA2 = "Extra2";
    public static final String KEY_EXTRA3 = "Extra3";
    public static final String KEY_EXTRA4 = "Extra4";
    public static final String KEY_NOTES1 = "Notes1";
    public static final String KEY_NOTES2 = "Notes2";


    public static String CREATE_TABLE_GKEARN_CONVERSIONS = DBUtils.CT_IF_NOT_EXISTS + TABLE_GKEARN_CONVERSIONS + DBUtils.GENERIC_ID +
            DBUtils.DATA_TYPE_INTEGER + DBUtils.CONSTRAINT_PRIMARY_KEY +
            DBUtils.CONSTRAINT_AUTOINCREMENT + DBUtils.COMMA +
            KEY_ID + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            KEY_DATETIMEIN + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_DATETIMECOMPLETED + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_CAMPAIGNID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_CONVERSIONTXNNO + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_VOUCHERPROCESSID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_RELEASINGTXNNO + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_BORROWERID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_SPONSORID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_TOTALNOOFVOUCHERS + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_TOTALVOUCHERAMOUNT + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_TOTALSC + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_BORROWERPOINTSDEDUCTED + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_BORROWERPREPOINTS + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_BORROWERPOSTPOINTS + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_TRANSACTIONMEDIUM + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_STATUS + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA1 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA2 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA3 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA4 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_NOTES1 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_NOTES2  + DBUtils.DATA_TYPE_TEXT + DBUtils.GENERIC_STATEMENT_ENDER;

    private String TRUNCATE_TABLE = DBUtils.DELETE + TABLE_GKEARN_CONVERSIONS;

    public static ContentValues insert(GKEarnConversionPoints data) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID, data.getID());
        contentValues.put(KEY_DATETIMEIN, data.getDateTimeIN());
        contentValues.put(KEY_DATETIMECOMPLETED, data.getDateTimeCompleted());
        contentValues.put(KEY_CAMPAIGNID, data.getCampaignID());
        contentValues.put(KEY_CONVERSIONTXNNO, data.getConversionTxnNo());
        contentValues.put(KEY_VOUCHERPROCESSID, data.getVoucherProcessID());
        contentValues.put(KEY_RELEASINGTXNNO, data.getReleasingTxnNo());
        contentValues.put(KEY_BORROWERID, data.getBorrowerID());
        contentValues.put(KEY_SPONSORID, data.getSponsorID());
        contentValues.put(KEY_TOTALNOOFVOUCHERS, data.getTotalNoOfVouchers());
        contentValues.put(KEY_TOTALVOUCHERAMOUNT, data.getTotalVoucherAmount());
        contentValues.put(KEY_TOTALSC, data.getTotalSC());
        contentValues.put(KEY_BORROWERPOINTSDEDUCTED, data.getBorrowerPointsDeducted());
        contentValues.put(KEY_BORROWERPREPOINTS, data.getBorrowerPrePoints());
        contentValues.put(KEY_BORROWERPOSTPOINTS, data.getBorrowerPostPoints());
        contentValues.put(KEY_TRANSACTIONMEDIUM, data.getTransactionMedium());
        contentValues.put(KEY_STATUS, data.getStatus());
        contentValues.put(KEY_EXTRA1, data.getExtra1());
        contentValues.put(KEY_EXTRA2, data.getExtra2());
        contentValues.put(KEY_EXTRA3, data.getExtra3());
        contentValues.put(KEY_EXTRA4, data.getExtra4());
        contentValues.put(KEY_NOTES1, data.getNotes1());
        contentValues.put(KEY_NOTES2, data.getNotes2());

        return contentValues;
    }

    public static List<GKEarnConversionPoints> getCoopGKEarnConversionPoints(Cursor cursor) {
        List<GKEarnConversionPoints> list = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                String datetimein = cursor.getString(cursor.getColumnIndex(KEY_DATETIMEIN));
                String datetimecompleted = cursor.getString(cursor.getColumnIndex(KEY_DATETIMECOMPLETED));
                String campaignid = cursor.getString(cursor.getColumnIndex(KEY_CAMPAIGNID));
                String conversionTxnNo = cursor.getString(cursor.getColumnIndex(KEY_CONVERSIONTXNNO));
                String voucherprocessid = cursor.getString(cursor.getColumnIndex(KEY_VOUCHERPROCESSID));
                String releasingtxnno = cursor.getString(cursor.getColumnIndex(KEY_RELEASINGTXNNO));
                String borrowerid = cursor.getString(cursor.getColumnIndex(KEY_BORROWERID));
                String sponsorid = cursor.getString(cursor.getColumnIndex(KEY_SPONSORID));
                double totalnoofvouchers = cursor.getDouble(cursor.getColumnIndex(KEY_TOTALNOOFVOUCHERS));
                double totalvoucheramount = cursor.getDouble(cursor.getColumnIndex(KEY_TOTALVOUCHERAMOUNT));
                double totalsc = cursor.getDouble(cursor.getColumnIndex(KEY_TOTALSC));
                double borrowerpointsdeducted = cursor.getDouble(cursor.getColumnIndex(KEY_BORROWERPOINTSDEDUCTED));
                double borrowerprepoints = cursor.getDouble(cursor.getColumnIndex(KEY_BORROWERPREPOINTS));
                double borrowerpostpoints = cursor.getDouble(cursor.getColumnIndex(KEY_BORROWERPOSTPOINTS));
                String transactionmedium = cursor.getString(cursor.getColumnIndex(KEY_TRANSACTIONMEDIUM));
                String status = cursor.getString(cursor.getColumnIndex(KEY_STATUS));
                String extra1 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA1));
                String extra2 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA2));
                String extra3 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA3));
                String extra4 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA4));
                String notes1 = cursor.getString(cursor.getColumnIndex(KEY_NOTES1));
                String notes2 = cursor.getString(cursor.getColumnIndex(KEY_NOTES2));

                list.add(new GKEarnConversionPoints(id,
                        datetimein, datetimecompleted, campaignid, conversionTxnNo, voucherprocessid, releasingtxnno, borrowerid,
                        sponsorid, totalnoofvouchers, totalvoucheramount, totalsc, borrowerpointsdeducted,
                        borrowerprepoints, borrowerpostpoints, transactionmedium,
                        status, extra1, extra2, extra3, extra4, notes1, notes2
                ));

            }
        }
        return list;
    }
}
