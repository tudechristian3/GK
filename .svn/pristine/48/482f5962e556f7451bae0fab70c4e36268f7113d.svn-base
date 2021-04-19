package com.goodkredit.myapplication.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.goodkredit.myapplication.bean.GKServiceBadge;

import java.util.ArrayList;
import java.util.List;

public class GKServicesBadgeDBHelper {

    public static String TABLE_NAME = "gkservicesbadge";

    public static String ID = "ID";
    public static String ServiceCode = "ServiceCode";
    public static String TotalBadge = "TotalBadge";

    public static String CREATE_STATEMENT = DBUtils.CT_IF_NOT_EXISTS +
            TABLE_NAME +
            DBUtils.GENERIC_ID + DBUtils.DATA_TYPE_INTEGER + DBUtils.CONSTRAINT_PRIMARY_KEY + DBUtils.CONSTRAINT_AUTOINCREMENT + DBUtils.COMMA +
            ID + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            ServiceCode + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            TotalBadge + DBUtils.DATA_TYPE_INTEGER + DBUtils.GENERIC_STATEMENT_ENDER;

    public static String TRUNCATE_TABLE = DBUtils.DELETE + TABLE_NAME;

    public static ContentValues insert(GKServiceBadge data) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ServiceCode, data.getServiceCode());
        contentValues.put(TotalBadge, data.getTotalBadge());
        return contentValues;
    }

    public static List<GKServiceBadge> getBorrowerGKSBadges(Cursor cursor) {
        List<GKServiceBadge> badges = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String strServiceCode = cursor.getString(cursor.getColumnIndex(ServiceCode));
                int intTotalBadge = cursor.getInt(cursor.getColumnIndex(TotalBadge));

                badges.add(new GKServiceBadge(
                        strServiceCode,
                        intTotalBadge
                ));
            }
        }
        return badges;
    }
}
