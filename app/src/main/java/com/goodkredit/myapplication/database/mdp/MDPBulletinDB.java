package com.goodkredit.myapplication.database.mdp;

import android.content.ContentValues;
import android.database.Cursor;

import com.goodkredit.myapplication.database.DBUtils;
import com.goodkredit.myapplication.model.mdp.MDPBulletin;

import java.util.ArrayList;
import java.util.List;

public class MDPBulletinDB {

    public static String TABLE_NAME = "MDPBulletin";

    public static String ID = "ID";
    public static String DateTimeIN = "DateTimeIN";
    public static String Title = "Title";
    public static String Description = "Description";
    public static String ImageURL = "ImageURL";
    public static String PeriodStart = "PeriodStart";
    public static String PeriodEnd = "PeriodEnd";
    public static String Addedby = "Addedby";
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
            Title + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Description + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            ImageURL + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            PeriodStart + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            PeriodEnd + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Addedby + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Status + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Extra1+ DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Extra2 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Extra3 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Extra4 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Notes1 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Notes2 + DBUtils.DATA_TYPE_TEXT + DBUtils.GENERIC_STATEMENT_ENDER;

    public static String TRUNCATE_TABLE = DBUtils.DELETE + TABLE_NAME;

    public static ContentValues insert(MDPBulletin data){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, data.getID());
        contentValues.put(DateTimeIN, data.getDateTimeIN());
        contentValues.put(Title, data.getTitle());
        contentValues.put(Description, data.getDescription());
        contentValues.put(ImageURL, data.getImageURL());
        contentValues.put(PeriodStart, data.getPeriodStart());
        contentValues.put(PeriodEnd, data.getPeriodEnd());
        contentValues.put(Addedby, data.getAddedby());
        contentValues.put(Status, data.getStatus());
        contentValues.put(Extra1, data.getExtra1());
        contentValues.put(Extra2, data.getExtra2());
        contentValues.put(Extra3, data.getExtra3());
        contentValues.put(Extra4, data.getExtra4());
        contentValues.put(Notes1, data.getNotes1());
        contentValues.put(Notes2, data.getNotes2());
        return contentValues;
    }

    public static List<MDPBulletin> getMDPBulletin (Cursor cursor){
        List<MDPBulletin> mdpbullein = new ArrayList<>();
        if(cursor.getCount() > 0){
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(ID));
                String datetimein = cursor.getString(cursor.getColumnIndex(DateTimeIN));
                String title = cursor.getString(cursor.getColumnIndex(Title));
                String description = cursor.getString(cursor.getColumnIndex(Description));
                String imageurl = cursor.getString(cursor.getColumnIndex(ImageURL));
                String periodstart = cursor.getString(cursor.getColumnIndex(PeriodStart));
                String periodend = cursor.getString(cursor.getColumnIndex(PeriodEnd));
                String addedby = cursor.getString(cursor.getColumnIndex(Addedby));
                String status = cursor.getString(cursor.getColumnIndex(Status));
                String extra1 = cursor.getString(cursor.getColumnIndex(Extra1));
                String extra2 = cursor.getString(cursor.getColumnIndex(Extra2));
                String extra3 = cursor.getString(cursor.getColumnIndex(Extra3));
                String extra4 = cursor.getString(cursor.getColumnIndex(Extra4));
                String notes1 = cursor.getString(cursor.getColumnIndex(Notes1));
                String note2 = cursor.getString(cursor.getColumnIndex(Notes2));

                mdpbullein.add(new MDPBulletin(
                        id,
                        datetimein,
                        title,
                        description,
                        imageurl,
                        periodstart,
                        periodend,
                        addedby,
                        status,
                        extra1,
                        extra2,
                        extra3,
                        extra4,
                        notes1,
                        note2
                ));
            }
        }
        return mdpbullein;
    }

}
