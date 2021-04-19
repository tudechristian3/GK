package com.goodkredit.myapplication.database.rfid;

import android.content.ContentValues;
import android.database.Cursor;

import com.goodkredit.myapplication.database.DBUtils;
import com.goodkredit.myapplication.model.rfid.RFIDInfo;

import java.util.ArrayList;
import java.util.List;

public class RFIDDBHelper {

    public static String TABLE_NAME = "DB_RFID";

    public static String ID = "ID";
    public static String DateTimeReleased = "DateTimeReleased";
    public static String GuarantorID = "GuarantorID";
    public static String GuarantorName = "GuarantorName";
    public static String BorrowerID = "BorrowerID";
    public static String BorrowerName = "BorrowerName";
    public static String BorrowerMobileNo = "BorrowerMobileNo";
    public static String RFIDNumber = "RFIDNumber";
    public static String RFIDCardNumber = "RFIDCardNumber";
    public static String RFIDPIN = "RFIDPIN";
    public static String VoucherSerial = "VoucherSerial";
    public static String isWithLimit = "isWithLimit";
    public static String isWithPIN = "isWithPIN";
    public static String PINMinAmount = "PINMinAmount";
    public static String AmountLimitBalance = "AmountLimitBalance";
    public static String LastAddedAmount = "LastAddedAmount";
    public static String LastAddedDateTime = "LastAddedDateTime";
    public static String LastAddedPreBalance = "LastAddedPreBalance";
    public static String LastAddedPostBalance = "LastAddedPostBalance";
    public static String LastDeductedAmount = "LastDeductedAmount";
    public static String LastDeductedDateTime = "LastDeductedDateTime";
    public static String LastDeductedPreBalance = "LastDeductedPreBalance";
    public static String LastDeductedPostBalance = "LastDeductedPostBalance";
    public static String DateTimeUpdated = "DateTimeUpdated";
    public static String Status = "Status";
    public static String Extra1 = "Extra1";
    public static String Extra2 = "Extra2";
    public static String Extra3 = "Extra3";
    public static String Extra4 = "Extra4";
    public static String Notes1 = "Notes1";
    public static String Notes2 = "Notes2";

    public static String CREATE_STATEMENT = DBUtils.CT_IF_NOT_EXISTS + TABLE_NAME + DBUtils.GENERIC_ID + DBUtils.DATA_TYPE_INTEGER + DBUtils.CONSTRAINT_PRIMARY_KEY + DBUtils.CONSTRAINT_AUTOINCREMENT + DBUtils.COMMA +
            ID + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            DateTimeReleased + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            GuarantorID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            GuarantorName + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            BorrowerID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            BorrowerName + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            BorrowerMobileNo + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            RFIDNumber + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            RFIDCardNumber + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            RFIDPIN + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            VoucherSerial + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            isWithLimit + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            isWithPIN + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            PINMinAmount + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            AmountLimitBalance + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            LastAddedAmount + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            LastAddedDateTime + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            LastAddedPreBalance + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            LastAddedPostBalance + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            LastDeductedAmount + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            LastDeductedDateTime + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            LastDeductedPreBalance + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            LastDeductedPostBalance + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            DateTimeUpdated + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            Status + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            Extra1 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Extra2 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Extra3 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Extra4 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Notes1 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Notes2 + DBUtils.DATA_TYPE_TEXT + DBUtils.GENERIC_STATEMENT_ENDER;

    public static String TRUNCATE_TABLE = DBUtils.DELETE + TABLE_NAME;

    public static ContentValues insert (RFIDInfo data){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, data.getID());
        contentValues.put(DateTimeReleased, data.getDateTimeReleased());
        contentValues.put(GuarantorID, data.getGuarantorID());
        contentValues.put(GuarantorName, data.getGuarantorName());
        contentValues.put(BorrowerID, data.getBorrowerID());
        contentValues.put(BorrowerName, data.getBorrowerName());
        contentValues.put(BorrowerMobileNo, data.getBorrowerMobileNo());
        contentValues.put(RFIDNumber, data.getRFIDNumber());
        contentValues.put(RFIDCardNumber, data.getRFIDCardNumber());
        contentValues.put(RFIDPIN, data.getRFIDPIN());
        contentValues.put(VoucherSerial, data.getVoucherSerial());
        contentValues.put(isWithLimit, data.getIsWithLimit());
        contentValues.put(isWithPIN, data.getIsWithPIN());
        contentValues.put(PINMinAmount, data.getPINMinAmount());
        contentValues.put(AmountLimitBalance, data.getAmountLimitBalance());
        contentValues.put(LastAddedAmount, data.getLastAddedAmount());
        contentValues.put(LastAddedDateTime, data.getLastAddedDateTime());
        contentValues.put(LastAddedPreBalance, data.getLastAddedPreBalance());
        contentValues.put(LastAddedPostBalance, data.getLastAddedPostBalance());
        contentValues.put(LastDeductedAmount, data.getLastDeductedAmount());
        contentValues.put(LastDeductedDateTime, data.getLastDeductedDateTime());
        contentValues.put(LastDeductedPreBalance, data.getLastDeductedPreBalance());
        contentValues.put(LastDeductedPostBalance, data.getLastDeductedPostBalance());
        contentValues.put(DateTimeUpdated, data.getDateTimeUpdated());
        contentValues.put(Status, data.getStatus());
        contentValues.put(Extra1, data.getExtra1());
        contentValues.put(Extra2, data.getExtra2());
        contentValues.put(Extra3, data.getExtra3());
        contentValues.put(Extra4, data.getExtra4());
        contentValues.put(Notes1, data.getNotes1());
        contentValues.put(Notes2, data.getNotes2());

        return contentValues;
    }

    public static List<RFIDInfo> getRFID(Cursor cursor){
        List<RFIDInfo> rfid = new ArrayList<>();
        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex(ID));
                String datetimereleased = cursor.getString(cursor.getColumnIndex(DateTimeReleased));
                String guarantorid = cursor.getString(cursor.getColumnIndex(GuarantorID));
                String guarantorname = cursor.getString(cursor.getColumnIndex(GuarantorName));
                String borrowerid = cursor.getString(cursor.getColumnIndex(BorrowerID));
                String borrowername = cursor.getString(cursor.getColumnIndex(BorrowerName));
                String borrowermobileno = cursor.getString(cursor.getColumnIndex(BorrowerMobileNo));
                String rfidnumber = cursor.getString(cursor.getColumnIndex(RFIDNumber));
                String rfidcardno = cursor.getString(cursor.getColumnIndex(RFIDCardNumber));
                String rfidpin = cursor.getString(cursor.getColumnIndex(RFIDPIN));
                String voucherserial = cursor.getString(cursor.getColumnIndex(VoucherSerial));
                int iswithlimit = cursor.getInt(cursor.getColumnIndex(isWithLimit));
                int iswithpin = cursor.getInt(cursor.getColumnIndex(isWithPIN));
                double pinminamount = cursor.getDouble(cursor.getColumnIndex(PINMinAmount));
                double amountlimitbalance = cursor.getDouble(cursor.getColumnIndex(AmountLimitBalance));
                double lastaddedamount = cursor.getDouble(cursor.getColumnIndex(LastAddedAmount));
                String lastaddeddatetime = cursor.getString(cursor.getColumnIndex(LastAddedDateTime));
                double lastaddedprebalance = cursor.getDouble(cursor.getColumnIndex(LastAddedPreBalance));
                double lastaddedpostbalance = cursor.getDouble(cursor.getColumnIndex(LastAddedPostBalance));
                double lastdeductedamount = cursor.getDouble(cursor.getColumnIndex(LastDeductedAmount));
                String lastdeducteddatetime = cursor.getString(cursor.getColumnIndex(LastDeductedDateTime));
                double lastdeductedprebalance = cursor.getDouble(cursor.getColumnIndex(LastDeductedPreBalance));
                double lastdeductedpostbalance = cursor.getDouble(cursor.getColumnIndex(LastDeductedPostBalance));
                String datetimeupdated = cursor.getString(cursor.getColumnIndex(DateTimeUpdated));
                String status = cursor.getString(cursor.getColumnIndex(Status));
                String extra1 = cursor.getString(cursor.getColumnIndex(Extra1));
                String extra2 = cursor.getString(cursor.getColumnIndex(Extra2));
                String extra3 = cursor.getString(cursor.getColumnIndex(Extra3));
                String extra4 = cursor.getString(cursor.getColumnIndex(Extra4));
                String notes1 = cursor.getString(cursor.getColumnIndex(Notes1));
                String notes2 = cursor.getString(cursor.getColumnIndex(Notes2));

                rfid.add(new RFIDInfo(
                        id,
                        datetimereleased,
                        guarantorid,
                        guarantorname,
                        borrowerid,
                        borrowername,
                        borrowermobileno,
                        rfidnumber,
                        rfidcardno,
                        rfidpin,
                        voucherserial,
                        iswithlimit,
                        iswithpin,
                        pinminamount,
                        amountlimitbalance,
                        lastaddedamount,
                        lastaddeddatetime,
                        lastaddedprebalance,
                        lastaddedpostbalance,
                        lastdeductedamount,
                        lastdeducteddatetime,
                        lastdeductedprebalance,
                        lastdeductedpostbalance,
                        datetimeupdated,
                        status,
                        extra1,
                        extra2,
                        extra3,
                        extra4,
                        notes1,
                        notes2
                ));
            }
        }

        return rfid;
    }
}
