package com.goodkredit.myapplication.database.mdp;

import android.content.ContentValues;
import android.database.Cursor;

import com.goodkredit.myapplication.database.DBUtils;
import com.goodkredit.myapplication.model.SupportConversation;

import java.util.ArrayList;
import java.util.List;

public class MDPSupportConversationDB {

    public static String TABLE_NAME = "MDPSupportConversation";

    public static String DateTimeIN = "DateTimeIN";
    public static String ThreadID = "ThreadID";
    public static String ReplySupportUserID = "ReplySupportUserID";
    public static String ReplySupportName = "ReplySupportName";
    public static String ThreadType = "ThreadType";
    public static String Message = "Message";

    public static String CREATE_STATEMENT = DBUtils.CT_IF_NOT_EXISTS + TABLE_NAME + DBUtils.GENERIC_ID + DBUtils.DATA_TYPE_INTEGER + DBUtils.CONSTRAINT_PRIMARY_KEY + DBUtils.CONSTRAINT_AUTOINCREMENT + DBUtils.COMMA +
            DateTimeIN + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            ThreadID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            ReplySupportUserID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            ReplySupportName + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            ThreadType + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Message + DBUtils.DATA_TYPE_TEXT + DBUtils.GENERIC_STATEMENT_ENDER;

    public static String TRUNCATE_TABLE = DBUtils.DELETE + TABLE_NAME;

    public static ContentValues insert(SupportConversation data){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DateTimeIN, data.getDateTimeIN());
        contentValues.put(ThreadID, data.getThreadID());
        contentValues.put(ReplySupportUserID, data.getReplySupportUserID());
        contentValues.put(ReplySupportName, data.getReplySupportName());
        contentValues.put(ThreadType, data.getThreadType());
        contentValues.put(Message, data.getMessage());
        return contentValues;
    }

    public static List<SupportConversation> getMDPSupportConversation (Cursor cursor){
        List<SupportConversation> mdpsupportconversation = new ArrayList<>();
        if(cursor.getCount() > 0){
            while (cursor.moveToNext()) {
                String datetimein = cursor.getString(cursor.getColumnIndex(DateTimeIN));
                String threadid = cursor.getString(cursor.getColumnIndex(ThreadID));
                String replysupportuserid = cursor.getString(cursor.getColumnIndex(ReplySupportUserID));
                String replysupportname = cursor.getString(cursor.getColumnIndex(ReplySupportName));
                String threadtype = cursor.getString(cursor.getColumnIndex(ThreadType));
                String message = cursor.getString(cursor.getColumnIndex(Message));

                mdpsupportconversation.add(new SupportConversation(
                        datetimein,
                        threadid,
                        replysupportuserid,
                        replysupportname,
                        threadtype,
                        message
                ));
            }
        }
        return mdpsupportconversation;
    }
}
