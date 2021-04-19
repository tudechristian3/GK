package com.goodkredit.myapplication.database.schoolmini;

import android.content.ContentValues;
import android.database.Cursor;

import com.goodkredit.myapplication.database.DBUtils;
import com.goodkredit.myapplication.model.GenericBulletin;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniDtr;

import java.util.ArrayList;
import java.util.List;

public class SchoolMiniDtrDB {
    //Table Name
    public static final String TABLE_SCHOOL_DTR = "table_school_dtr";


    //Values
    public static final String KEY_ID = "ID";
    public static final String KEY_SCHOOLID = "SchoolID";
    public static final String KEY_STUDENTID = "StudentID";
    public static final String KEY_TYPE = "Type";
    public static final String KEY_DATETIMEIN = "DateTimeIN";
    public static final String KEY_BATCHID = "BatchID";
    public static final String KEY_EXTRA1 = "Extra1";
    public static final String KEY_EXTRA2 = "Extra2";
    public static final String KEY_EXTRA3 = "Extra3";
    public static final String KEY_NOTES1 = "Notes1";
    public static final String KEY_NOTES2 = "Notes2";


    public static String CREATE_TABLE_DTR = DBUtils.CT_IF_NOT_EXISTS + TABLE_SCHOOL_DTR + DBUtils.GENERIC_ID +
            DBUtils.DATA_TYPE_INTEGER + DBUtils.CONSTRAINT_PRIMARY_KEY +
            DBUtils.CONSTRAINT_AUTOINCREMENT + DBUtils.COMMA +
            KEY_ID + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            KEY_SCHOOLID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_STUDENTID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_TYPE + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_DATETIMEIN + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_BATCHID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA1 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA2 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA3 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_NOTES1 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_NOTES2  + DBUtils.DATA_TYPE_TEXT + DBUtils.GENERIC_STATEMENT_ENDER;

    private String TRUNCATE_TABLE = DBUtils.DELETE + TABLE_SCHOOL_DTR;

    public static ContentValues insert(SchoolMiniDtr data) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID, data.getID());
        contentValues.put(KEY_SCHOOLID, data.getSchoolID());
        contentValues.put(KEY_STUDENTID, data.getStudentID());
        contentValues.put(KEY_TYPE, data.getType());
        contentValues.put(KEY_DATETIMEIN, data.getDateTimeIN());
        contentValues.put(KEY_BATCHID, data.getBatchID());
        contentValues.put(KEY_EXTRA1, data.getExtra1());
        contentValues.put(KEY_EXTRA2, data.getExtra2());
        contentValues.put(KEY_EXTRA3, data.getExtra3());
        contentValues.put(KEY_NOTES1, data.getNotes1());
        contentValues.put(KEY_NOTES2, data.getNotes2());

        return contentValues;
    }

    public static List<SchoolMiniDtr> getSchoolMiniDtr(Cursor cursor) {
        List<SchoolMiniDtr> list = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                String schoolid = cursor.getString(cursor.getColumnIndex(KEY_SCHOOLID));
                String studentid = cursor.getString(cursor.getColumnIndex(KEY_STUDENTID));
                String type = cursor.getString(cursor.getColumnIndex(KEY_TYPE));
                String datetimein = cursor.getString(cursor.getColumnIndex(KEY_DATETIMEIN));
                String batchid = cursor.getString(cursor.getColumnIndex(KEY_BATCHID));
                String extra1 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA1));
                String extra2 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA2));
                String extra3 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA3));
                String notes1 = cursor.getString(cursor.getColumnIndex(KEY_NOTES1));
                String notes2 = cursor.getString(cursor.getColumnIndex(KEY_NOTES2));

                list.add(new SchoolMiniDtr(id, schoolid, studentid, type,
                        datetimein, batchid, extra1, extra2, extra3, notes1, notes2
                ));

            }
        }
        return list;
    }
}
