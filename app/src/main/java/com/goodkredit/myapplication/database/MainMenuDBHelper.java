package com.goodkredit.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.goodkredit.myapplication.bean.GKService;

import net.sqlcipher.database.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MainMenuDBHelper {

    public static String TABLE_NAME = "gkservices_mainmenu";

    public static String ID = "ID";
    public static String ServiceCode = "ServiceCode";
    public static String ServiceName = "ServiceName";
    public static String Type = "Type";
    public static String Logo = "Logo";
    public static String MerchantID = "MerchantID";
    public static String GKStoreID = "GKStoreID";
    public static String ServiceType = "ServiceType";
    public static String GKStoreStatus = "GKStoreStatus";
    public static String Status = "Status";
    public static String GuarantorID = "GuarantorID";
    public static String Category = "Category";
    private static String Color = "Color";
    private static String Rank = "Rank";

    public static String CREATE_STATEMENT = DBUtils.CT_IF_NOT_EXISTS +
            TABLE_NAME +
            DBUtils.GENERIC_ID + DBUtils.DATA_TYPE_INTEGER + DBUtils.CONSTRAINT_PRIMARY_KEY + DBUtils.CONSTRAINT_AUTOINCREMENT + DBUtils.COMMA +
            ID + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            ServiceCode + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            ServiceName + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Type + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Logo + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            MerchantID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            GKStoreID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            ServiceType + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            GKStoreStatus + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            GuarantorID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Category + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Color + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA+
            Rank + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA+
            Status + DBUtils.DATA_TYPE_TEXT + DBUtils.GENERIC_STATEMENT_ENDER;


    public static String TRUNCATE_TABLE = DBUtils.DELETE + TABLE_NAME;



    public static String deleteMainMenuRecord(String condition) {
        return DBUtils.DELETE + TABLE_NAME + " WHERE " + ServiceCode + "= '" + condition + "'";
    }

}
