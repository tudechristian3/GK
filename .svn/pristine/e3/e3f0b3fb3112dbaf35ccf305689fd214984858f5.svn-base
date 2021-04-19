package com.goodkredit.myapplication.database.freesms;

import android.content.ContentValues;
import android.database.Cursor;

import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.database.DBUtils;
import com.goodkredit.myapplication.model.freesms.FreeSMSHistory;

import java.util.ArrayList;
import java.util.List;

public class FreeSMSHistoryDBHelper {

    public static String TABLE_NAME = "FreeSMSHistory";

    public static String ID = "ID";
    public static String DateTimeIN = "DateTimeIN";
    public static String DateTimeCompleted = "DateTimeCompleted";
    public static String TemplateType = "TemplateType";
    public static String TemplateID = "TemplateID";
    public static String Recipient = "Recipient";
    public static String XMLDetails = "XMLDetails";
    public static String Sender = "Sender";
    public static String Message = "Message";
    public static String SendContent = "SendContent";
    public static String Status = "Status";
    public static String Extra1 = "Extra1";
    public static String Extra2 = "Extra2";
    public static String Extra3 = "Extra3";
    public static String Extra4 = "Extra4";
    public static String Notes1 = "Notes1";
    public static String Notes2 = "Notes2";

    public static String CREATE_STATEMENT = DBUtils.CT_IF_NOT_EXISTS + TABLE_NAME + DBUtils.GENERIC_ID + DBUtils.DATA_TYPE_INTEGER + DBUtils.CONSTRAINT_PRIMARY_KEY + DBUtils.CONSTRAINT_AUTOINCREMENT + DBUtils.COMMA +
            ID + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            DateTimeIN + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            DateTimeCompleted + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            TemplateType + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            TemplateID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Recipient + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            XMLDetails + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Sender + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Message + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            SendContent + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Status + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Extra1+ DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Extra2 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Extra3 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Extra4 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Notes1 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Notes2 + DBUtils.DATA_TYPE_TEXT + DBUtils.GENERIC_STATEMENT_ENDER;

    public static String TRUNCATE_TABLE = DBUtils.DELETE + TABLE_NAME;

    public static ContentValues insert (FreeSMSHistory data){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, data.getID());
        contentValues.put(DateTimeIN, data.getDateTimeIN());
        contentValues.put(DateTimeCompleted, data.getDateTimeCompleted());
        contentValues.put(TemplateType, data.getTemplateType());
        contentValues.put(TemplateID, data.getTemplateID());
        contentValues.put(Recipient, data.getRecipient());
        contentValues.put(XMLDetails, data.getXMLDetails());
        contentValues.put(Sender, data.getSender());
        contentValues.put(Message, data.getMessage());
        contentValues.put(SendContent, data.getSendContent());
        contentValues.put(Status, data.getStatus());
        contentValues.put(Extra1, data.getExtra1());
        contentValues.put(Extra2, data.getExtra2());
        contentValues.put(Extra3, data.getExtra3());
        contentValues.put(Extra4, data.getExtra4());
        contentValues.put(Notes1, data.getNotes1());
        contentValues.put(Notes2, data.getNotes2());

        return contentValues;
    }

    public static List<FreeSMSHistory> getFreeSMSHistory(Cursor cursor){
        List<FreeSMSHistory> freesmshistory = new ArrayList<>();
        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex(ID));
                String datetimein = cursor.getString(cursor.getColumnIndex(DateTimeIN));
                String datetimecompleted = cursor.getString(cursor.getColumnIndex(DateTimeCompleted));
                String templatetype = cursor.getString(cursor.getColumnIndex(TemplateType));
                String templateid = cursor.getString(cursor.getColumnIndex(TemplateID));
                String recipient = cursor.getString(cursor.getColumnIndex(Recipient));
                String xmldetails = cursor.getString(cursor.getColumnIndex(XMLDetails));
                String sender = CommonFunctions.parseXML(xmldetails, "sender");
                String message = CommonFunctions.parseXML(xmldetails, "message");
                String sendcontent = cursor.getString(cursor.getColumnIndex(SendContent));
                String status = cursor.getString(cursor.getColumnIndex(Status));
                String extra1 = cursor.getString(cursor.getColumnIndex(Extra1));
                String extra2 = cursor.getString(cursor.getColumnIndex(Extra2));
                String extra3 = cursor.getString(cursor.getColumnIndex(Extra3));
                String extra4 = cursor.getString(cursor.getColumnIndex(Extra4));
                String notes1 = cursor.getString(cursor.getColumnIndex(Notes1));
                String notes2 = cursor.getString(cursor.getColumnIndex(Notes2));

                freesmshistory.add(new FreeSMSHistory(
                        id,
                        datetimein,
                        datetimecompleted,
                        templatetype,
                        templateid,
                        recipient,
                        xmldetails,
                        sender,
                        message,
                        sendcontent,
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

        return freesmshistory;
    }
}
