package com.goodkredit.myapplication.database.schoolmini;

import android.content.ContentValues;
import android.database.Cursor;

import com.goodkredit.myapplication.database.DBUtils;
import com.goodkredit.myapplication.model.SupportConversation;
import com.goodkredit.myapplication.model.SupportThread;

import java.util.ArrayList;
import java.util.List;

public class SchoolMiniSupportConversationDB {
    //Table Name
    public static final String TABLE_SC_SUPPORT_CONVERSATIONS = "table_sc_support_conversations";

    //Values
    public static final String KEY_ID = "ID";
    public static final String KEY_DATETIMEIN = "DateTimeIN";
    public static final String KEY_THREADID = "ThreadID";
    public static final String KEY_REPLYSUPPORTUSERID = "ReplySupportUserID";
    public static final String KEY_REPLYSUPPORTNAME = "ReplySupportName";
    public static final String KEY_THREADTYPE = "ThreadType";
    public static final String KEY_MESSAGE = "Message";
    public static final String KEY_EXTRA1 = "Extra1";
    public static final String KEY_EXTRA2 = "Extra2";
    public static final String KEY_EXTRA3 = "Extra3";
    public static final String KEY_EXTRA4 = "Extra4";
    public static final String KEY_NOTES1 = "Notes1";
    public static final String KEY_NOTES2 = "Notes2";


    public static String CREATE_TABLE_SC_SUPPORT_CONVERSATIONS = DBUtils.CT_IF_NOT_EXISTS + TABLE_SC_SUPPORT_CONVERSATIONS + DBUtils.GENERIC_ID +
            DBUtils.DATA_TYPE_INTEGER + DBUtils.CONSTRAINT_PRIMARY_KEY +
            DBUtils.CONSTRAINT_AUTOINCREMENT + DBUtils.COMMA +
            KEY_ID + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            KEY_DATETIMEIN + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_THREADID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_REPLYSUPPORTUSERID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_REPLYSUPPORTNAME + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_THREADTYPE + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_MESSAGE + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA1 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA2 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA3 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA4 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_NOTES1 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_NOTES2 + DBUtils.DATA_TYPE_TEXT + DBUtils.GENERIC_STATEMENT_ENDER;

    private String TRUNCATE_TABLE = DBUtils.DELETE + TABLE_SC_SUPPORT_CONVERSATIONS;

    public static ContentValues insert(SupportConversation data) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_DATETIMEIN, data.getDateTimeIN());
        contentValues.put(KEY_THREADID, data.getThreadID());
        contentValues.put(KEY_REPLYSUPPORTUSERID, data.getReplySupportUserID());
        contentValues.put(KEY_REPLYSUPPORTNAME, data.getReplySupportName());
        contentValues.put(KEY_THREADTYPE, data.getThreadType());
        contentValues.put(KEY_MESSAGE, data.getMessage());
        return contentValues;
    }

    public static List<SupportConversation> getSupportConversation(Cursor cursor) {
        List<SupportConversation> supportconversationlist = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                String datetimein = cursor.getString(cursor.getColumnIndex(KEY_DATETIMEIN));
                String threadid = cursor.getString(cursor.getColumnIndex(KEY_THREADID));
                String replysupportuserid = cursor.getString(cursor.getColumnIndex(KEY_REPLYSUPPORTUSERID));
                String replysupportname = cursor.getString(cursor.getColumnIndex(KEY_REPLYSUPPORTNAME));
                String threadtype = cursor.getString(cursor.getColumnIndex(KEY_THREADTYPE));
                String message = cursor.getString(cursor.getColumnIndex(KEY_MESSAGE));
                String extra1 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA1));
                String extra2 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA2));
                String extra3 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA3));
                String notes1 = cursor.getString(cursor.getColumnIndex(KEY_NOTES1));
                String notes2 = cursor.getString(cursor.getColumnIndex(KEY_NOTES2));

                supportconversationlist.add(new SupportConversation(
                        datetimein, threadid, replysupportuserid, replysupportname, threadtype, message
                ));

            }
        }
        return supportconversationlist;
    }
}
