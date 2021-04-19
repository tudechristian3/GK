package com.goodkredit.myapplication.database.coopassistant;

import android.content.ContentValues;
import android.database.Cursor;

import com.goodkredit.myapplication.database.DBUtils;
import com.goodkredit.myapplication.model.GenericBulletin;

import java.util.ArrayList;
import java.util.List;

public class CoopMiniBulletinDB {
    //Table Name
    public static final String TABLE_COOP_BULLETIN = "table_coop_bulletin";

    //Values
    public static final String KEY_ID = "ID";
    public static final String KEY_DATETIMEIN = "DateTimeIN";
    public static final String KEY_TITLE = "Title";
    public static final String KEY_DESCRIPTION = "Description";
    public static final String KEY_IMAGEURL = "ImageURL";
    public static final String KEY_PERIODSTART = "PeriodStart";
    public static final String KEY_PERIODEND = "PeriodEnd";
    public static final String KEY_ISPRIVATE = "isPrivate";
    public static final String KEY_ADDEDBY = "Addedby";
    public static final String KEY_STATUS = "Status";
    public static final String KEY_EXTRA1 = "Extra1";
    public static final String KEY_EXTRA2 = "Extra2";
    public static final String KEY_EXTRA3 = "Extra3";
    public static final String KEY_EXTRA4 = "Extra4";
    public static final String KEY_NOTES1 = "Notes1";
    public static final String KEY_NOTES2 = "Notes2";


    public static String CREATE_TABLE_BULLETIN = DBUtils.CT_IF_NOT_EXISTS + TABLE_COOP_BULLETIN + DBUtils.GENERIC_ID +
            DBUtils.DATA_TYPE_INTEGER + DBUtils.CONSTRAINT_PRIMARY_KEY +
            DBUtils.CONSTRAINT_AUTOINCREMENT + DBUtils.COMMA +
            KEY_ID + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            KEY_DATETIMEIN + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_TITLE + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_DESCRIPTION + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_IMAGEURL + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_PERIODSTART + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_PERIODEND + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_ISPRIVATE + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            KEY_ADDEDBY + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_STATUS + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA1 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA2 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA3 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA4 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_NOTES1 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_NOTES2  + DBUtils.DATA_TYPE_TEXT + DBUtils.GENERIC_STATEMENT_ENDER;

    private String TRUNCATE_TABLE = DBUtils.DELETE + TABLE_COOP_BULLETIN;

    public static ContentValues insert(GenericBulletin data) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID, data.getID());
        contentValues.put(KEY_DATETIMEIN, data.getDateTimeIN());
        contentValues.put(KEY_TITLE, data.getTitle());
        contentValues.put(KEY_DESCRIPTION, data.getDescription());
        contentValues.put(KEY_IMAGEURL, data.getImageURL());
        contentValues.put(KEY_PERIODSTART, data.getPeriodStart());
        contentValues.put(KEY_PERIODEND, data.getPeriodEnd());
        contentValues.put(KEY_ISPRIVATE, data.getIsPrivate());
        contentValues.put(KEY_ADDEDBY, data.getAddedby());
        contentValues.put(KEY_STATUS, data.getStatus());
        contentValues.put(KEY_EXTRA1, data.getExtra1());
        contentValues.put(KEY_EXTRA2, data.getExtra2());
        contentValues.put(KEY_EXTRA3, data.getExtra3());
        contentValues.put(KEY_EXTRA4, data.getExtra4());
        contentValues.put(KEY_NOTES1, data.getNotes1());
        contentValues.put(KEY_NOTES2, data.getNotes2());

        return contentValues;
    }

    public static List<GenericBulletin> getCoopMiniBulletin(Cursor cursor) {
        List<GenericBulletin> schoolminibulletinlist = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                String datetimein = cursor.getString(cursor.getColumnIndex(KEY_DATETIMEIN));
                String title = cursor.getString(cursor.getColumnIndex(KEY_TITLE));
                String description = cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION));
                String imageurl = cursor.getString(cursor.getColumnIndex(KEY_IMAGEURL));
                String periodstart = cursor.getString(cursor.getColumnIndex(KEY_PERIODSTART));
                String periodend = cursor.getString(cursor.getColumnIndex(KEY_PERIODEND));
                int isprivate = cursor.getInt(cursor.getColumnIndex(KEY_ISPRIVATE));
                String addedby = cursor.getString(cursor.getColumnIndex(KEY_ADDEDBY));
                String status = cursor.getString(cursor.getColumnIndex(KEY_STATUS));
                String extra1 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA1));
                String extra2 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA2));
                String extra3 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA3));
                String extra4 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA4));
                String notes1 = cursor.getString(cursor.getColumnIndex(KEY_NOTES1));
                String notes2 = cursor.getString(cursor.getColumnIndex(KEY_NOTES2));

                schoolminibulletinlist.add(new GenericBulletin(id,
                        datetimein, title, description, imageurl, periodstart, periodend, isprivate, addedby,
                        status, extra1, extra2, extra3, extra4, notes1, notes2
                ));

            }
        }
        return schoolminibulletinlist;
    }
}
