package com.goodkredit.myapplication.database.votes;

import android.content.ContentValues;
import android.database.Cursor;

import com.goodkredit.myapplication.database.DBUtils;
import com.goodkredit.myapplication.model.votes.VotesPostEvent;

import java.util.ArrayList;
import java.util.List;

public class VotesPostEventDB {
    //Table Name
    public static final String TABLE_VOTESEVENTS = "table_votesevents";

    //Values
    public static final String KEY_ID = "ID";
    public static final String KEY_DATETIMEIN = "DateTimeIN";
    public static final String KEY_DATETIMEARCHIVED = "DateTimeArchived";
    public static final String KEY_MERCHANTID = "MerchantID";
    public static final String KEY_MERCHANTNAME = "MerchantName";
    public static final String KEY_EVENTID = "EventID";
    public static final String KEY_EVENTNAME = "EventName";
    public static final String KEY_EVENTDESCRIPTION = "EventDescription";
    public static final String KEY_EVENTDATETIMESTART = "EventDateTimeStart";
    public static final String KEY_EVENTDATETIMEEND = "EventDateTimeEnd";
    public static final String KEY_EVENTPICTUREURL = "EventPictureURL";
    public static final String KEY_XMLDETAILS = "XMLDetails";
    public static final String KEY_NOPARTICIPANTS = "NoParticipants";
    public static final String KEY_TOTALAMOUNTCOLLECTED = "TotalAmountCollected";
    public static final String KEY_TOTALVOTES = "TotalVotes";
    public static final String KEY_ADDEDBY = "AddedBy";
    public static final String KEY_UPDATEDATETIME = "UpdateDateTime";
    public static final String KEY_STATUS = "Status";
    public static final String KEY_EXTRA1 = "Extra1";
    public static final String KEY_EXTRA2 = "Extra2";
    public static final String KEY_EXTRA3 = "Extra3";
    public static final String KEY_EXTRA4 = "Extra4";
    public static final String KEY_NOTES1 = "Notes1";
    public static final String KEY_NOTES2 = "Notes2";


    public static String CREATE_TABLE_VOTESEVENTS = DBUtils.CT_IF_NOT_EXISTS + TABLE_VOTESEVENTS + DBUtils.GENERIC_ID +
            DBUtils.DATA_TYPE_INTEGER + DBUtils.CONSTRAINT_PRIMARY_KEY +
            DBUtils.CONSTRAINT_AUTOINCREMENT + DBUtils.COMMA +
            KEY_ID + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            KEY_DATETIMEIN + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_DATETIMEARCHIVED + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_MERCHANTID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_MERCHANTNAME + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EVENTID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EVENTNAME + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EVENTDESCRIPTION + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EVENTDATETIMESTART + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EVENTDATETIMEEND + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EVENTPICTUREURL + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_XMLDETAILS + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_NOPARTICIPANTS + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_TOTALAMOUNTCOLLECTED + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_TOTALVOTES + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_ADDEDBY + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_UPDATEDATETIME + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_STATUS + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA1 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA2 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA3 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA4 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_NOTES1 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_NOTES2  + DBUtils.DATA_TYPE_TEXT + DBUtils.GENERIC_STATEMENT_ENDER;

    private String TRUNCATE_TABLE = DBUtils.DELETE + TABLE_VOTESEVENTS;

    public static ContentValues insert(VotesPostEvent data) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID, data.getID());
        contentValues.put(KEY_DATETIMEIN, data.getDateTimeIN());
        contentValues.put(KEY_DATETIMEARCHIVED, data.getDateTimeArchived());
        contentValues.put(KEY_MERCHANTID, data.getMerchantID());
        contentValues.put(KEY_MERCHANTNAME, data.getMerchantName());
        contentValues.put(KEY_EVENTID, data.getEventID());
        contentValues.put(KEY_EVENTNAME, data.getEventName());
        contentValues.put(KEY_EVENTDESCRIPTION, data.getEventDescription());
        contentValues.put(KEY_EVENTDATETIMESTART, data.getEventDateTimeStart());
        contentValues.put(KEY_EVENTDATETIMEEND, data.getEventDateTimeEnd());
        contentValues.put(KEY_EVENTPICTUREURL, data.getEventPictureURL());
        contentValues.put(KEY_XMLDETAILS, data.getXMLDetails());
        contentValues.put(KEY_NOPARTICIPANTS, data.getNoParticipants());
        contentValues.put(KEY_TOTALAMOUNTCOLLECTED, data.getTotalAmountCollected());
        contentValues.put(KEY_TOTALVOTES, data.getTotalVotes());
        contentValues.put(KEY_ADDEDBY, data.getAddedBy());
        contentValues.put(KEY_UPDATEDATETIME, data.getUpdateDateTime());
        contentValues.put(KEY_STATUS, data.getStatus());
        contentValues.put(KEY_EXTRA1, data.getExtra1());
        contentValues.put(KEY_EXTRA2, data.getExtra2());
        contentValues.put(KEY_EXTRA3, data.getExtra3());
        contentValues.put(KEY_EXTRA4, data.getExtra4());
        contentValues.put(KEY_NOTES1, data.getNotes1());
        contentValues.put(KEY_NOTES2, data.getNotes2());

        return contentValues;
    }

    public static List<VotesPostEvent> getPostVotesEvent (Cursor cursor) {
        List<VotesPostEvent> postvostseventlist = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                String datetimein = cursor.getString(cursor.getColumnIndex(KEY_DATETIMEIN));
                String datetimearchived = cursor.getString(cursor.getColumnIndex(KEY_DATETIMEARCHIVED));
                String merchantid = cursor.getString(cursor.getColumnIndex(KEY_MERCHANTID));
                String merchantname = cursor.getString(cursor.getColumnIndex(KEY_MERCHANTNAME));
                String eventid = cursor.getString(cursor.getColumnIndex(KEY_EVENTID));
                String eventname = cursor.getString(cursor.getColumnIndex(KEY_EVENTNAME));
                String eventdescription = cursor.getString(cursor.getColumnIndex(KEY_EVENTDESCRIPTION));
                String eventdatetimestart = cursor.getString(cursor.getColumnIndex(KEY_EVENTDATETIMESTART));
                String eventdatetimeend = cursor.getString(cursor.getColumnIndex(KEY_EVENTDATETIMEEND));
                String eventpictureurl = cursor.getString(cursor.getColumnIndex(KEY_EVENTPICTUREURL));
                String xmldetails = cursor.getString(cursor.getColumnIndex(KEY_XMLDETAILS));
                int noparticipants = cursor.getInt(cursor.getColumnIndex(KEY_NOPARTICIPANTS));
                double totalamountcollected = cursor.getDouble(cursor.getColumnIndex(KEY_TOTALAMOUNTCOLLECTED));
                int totalvotes = cursor.getInt(cursor.getColumnIndex(KEY_TOTALVOTES));
                String addedby = cursor.getString(cursor.getColumnIndex(KEY_ADDEDBY));
                String updatedatetime = cursor.getString(cursor.getColumnIndex(KEY_UPDATEDATETIME));
                String status = cursor.getString(cursor.getColumnIndex(KEY_STATUS));
                String extra1 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA1));
                String extra2 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA2));
                String extra3 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA3));
                String extra4 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA4));
                String notes1 = cursor.getString(cursor.getColumnIndex(KEY_NOTES1));
                String notes2 = cursor.getString(cursor.getColumnIndex(KEY_NOTES2));

                postvostseventlist.add(new VotesPostEvent(id,
                        datetimein, datetimearchived, merchantid, merchantname, eventid,
                        eventname, eventdescription, eventdatetimestart, eventdatetimeend,
                        eventpictureurl, xmldetails, noparticipants, totalamountcollected, totalvotes,
                        addedby, updatedatetime, status, extra1, extra2,
                        extra3, extra4, notes1, notes2
                ));

            }
        }
        return postvostseventlist;
    }
}
