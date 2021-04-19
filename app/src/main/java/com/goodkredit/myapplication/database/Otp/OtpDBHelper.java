package com.goodkredit.myapplication.database.Otp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.goodkredit.myapplication.database.DBUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.model.OtpModel.BorrowerOtp;

import java.util.ArrayList;
import java.util.List;


public class OtpDBHelper{
    //Table Name
    public static final String TABLE_OTP = "borrowerotp";
    //Values
    public static final String KEY_USERID = "UserID";
    public static final String KEY_OTP = "Otp";
    public static String CREATE_TABLE_OTP =
            DBUtils.CT_IF_NOT_EXISTS + TABLE_OTP + DBUtils.GENERIC_ID + DBUtils.DATA_TYPE_INTEGER + DBUtils.CONSTRAINT_PRIMARY_KEY + DBUtils.CONSTRAINT_AUTOINCREMENT + DBUtils.COMMA
            + KEY_USERID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA
            + KEY_OTP + DBUtils.DATA_TYPE_TEXT + DBUtils.GENERIC_STATEMENT_ENDER;
    private String TRUNCATE_TABLE = DBUtils.DELETE + TABLE_OTP;

    public static ContentValues insert(BorrowerOtp data) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_USERID, data.getUserID());
        contentValues.put(KEY_OTP, data.getOtp());
        return contentValues;
    }
    public static List<BorrowerOtp> getBorrowersOTP(Cursor cursor) {
        List<BorrowerOtp> sponsorOTP = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String userid = cursor.getString(cursor.getColumnIndex(KEY_USERID));
                String otp = cursor.getString(cursor.getColumnIndex(KEY_OTP));
                sponsorOTP.add( new BorrowerOtp(
                        userid,
                        otp
                ));
            }
        }
        return sponsorOTP;

    }

}
