package com.goodkredit.myapplication.database;

import android.content.ContentValues;
import android.database.Cursor;

import androidx.cardview.widget.CardView;

import com.goodkredit.myapplication.bean.GKService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ban on 22/03/2018.
 */

public class GKSRecentlyUsedDBHelper {

    public static String TABLE_NAME = "gksrecentlyusedtable";

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
            Rank + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA+
            Status + DBUtils.DATA_TYPE_TEXT + DBUtils.GENERIC_STATEMENT_ENDER;


    public static String TRUNCATE_TABLE = DBUtils.DELETE + TABLE_NAME;


    public static ContentValues insert(GKService data) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, data.getID());
        contentValues.put(ServiceCode, data.getServiceCode());
        contentValues.put(ServiceName, data.getServiceName());
        contentValues.put(Type, data.getServiceType());
        contentValues.put(Logo, data.getLogo());
        contentValues.put(MerchantID, data.getMerchantID());
        contentValues.put(GKStoreID, data.getGKStoreID());
        contentValues.put(ServiceType, data.getServiceType());
        contentValues.put(GKStoreStatus, data.getGKStoreStatus());
        contentValues.put(Status, data.getStatus());
        contentValues.put(GuarantorID, data.getGuarantorID());
        contentValues.put(Category,data.getCategory());
        contentValues.put(Color,data.getColor());
        contentValues.put(Rank,data.getRank());
        return contentValues;
    }

    public static String deleteRecord(String condition) {
        return DBUtils.DELETE + TABLE_NAME + " WHERE " + ServiceCode + "= '" + condition + "'";
    }

    public static List<GKService> getRecentlyUsedGKS(Cursor cursor) {
        List<GKService> services = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int intID = cursor.getInt(cursor.getColumnIndex(ID));
                String strServiceCode = cursor.getString(cursor.getColumnIndex(ServiceCode));
                String strServiceName = cursor.getString(cursor.getColumnIndex(ServiceName));
                String strType = cursor.getString(cursor.getColumnIndex(Type));
                String strLogo = cursor.getString(cursor.getColumnIndex(Logo));
                String strMerchantID = cursor.getString(cursor.getColumnIndex(MerchantID));
                String strGKStoreID = cursor.getString(cursor.getColumnIndex(GKStoreID));
                String strServiceType = cursor.getString(cursor.getColumnIndex(ServiceType));
                String strGKStoreStatus = cursor.getString(cursor.getColumnIndex(GKStoreStatus));
                String strStatus = cursor.getString(cursor.getColumnIndex(Status));
                String strGuarantorID = cursor.getString(cursor.getColumnIndex(GuarantorID));
                String strCategory = cursor.getString(cursor.getColumnIndex(Category));
                String strColor = cursor.getString(cursor.getColumnIndex(Color));
                int strRank = cursor.getInt(cursor.getColumnIndex(Rank));
                services.add(new GKService(
                        intID,
                        strServiceCode,
                        strServiceName,
                        strType,
                        strStatus,
                        strLogo,
                        strMerchantID,
                        strGKStoreID,
                        strServiceType,
                        strGKStoreStatus,
                        strGuarantorID,
                        strCategory,
                        strColor,
                        strRank
                ));
            }
        }
        if (services.size() > 10)
            services.subList(0, 10);

        return services;
    }

}
