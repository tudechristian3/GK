package com.goodkredit.myapplication.database.votes;

import android.content.ContentValues;
import android.database.Cursor;

import com.goodkredit.myapplication.database.DBUtils;
import com.goodkredit.myapplication.model.votes.VotesParticipants;
import com.goodkredit.myapplication.model.votes.VotesPostEvent;

import java.util.ArrayList;
import java.util.List;

public class VotesParticipantsDB {
    //Table Name
    public static final String TABLE_PARTICIPANTS = "table_participants";

    //Values
    public static final String KEY_ID = "ID";
    public static final String KEY_DATETIMEIN = "DateTimeIN";
    public static final String KEY_EVENTID = "EventID";
    public static final String KEY_PARTICIPANTID = "ParticipantID";
    public static final String KEY_FIRSTNAME = "FirstName";
    public static final String KEY_MIDDLENAME = "MiddleName";
    public static final String KEY_LASTNAME = "LastName";
    public static final String KEY_AGE = "Age";
    public static final String KEY_GENDER = "Gender";
    public static final String KEY_ADDRESS = "Address";
    public static final String KEY_OTHERINFO = "OtherInfo";
    public static final String KEY_PARTICIPANTNUMBER = "ParticipantNumber";
    public static final String KEY_CURRENTNOVOTE = "CurrentNoVote";
    public static final String KEY_LASTDATETIMEVOTED = "LastDateTimeVoted";
    public static final String KEY_ADDEDBY = "AddedBy";
    public static final String KEY_PROFILEPICTUREURL = "ProfilePictureURL";
    public static final String KEY_IMAGEXML = "ImageXML";
    public static final String KEY_STATUS = "Status";
    public static final String KEY_EXTRA1 = "Extra1";
    public static final String KEY_EXTRA2 = "Extra2";
    public static final String KEY_EXTRA3 = "Extra3";
    public static final String KEY_EXTRA4 = "Extra4";
    public static final String KEY_NOTES1 = "Notes1";
    public static final String KEY_NOTES2 = "Notes2";


    public static String CREATE_TABLE_VOTESEVENTS = DBUtils.CT_IF_NOT_EXISTS + TABLE_PARTICIPANTS + DBUtils.GENERIC_ID +
            DBUtils.DATA_TYPE_INTEGER + DBUtils.CONSTRAINT_PRIMARY_KEY +
            DBUtils.CONSTRAINT_AUTOINCREMENT + DBUtils.COMMA +
            KEY_ID + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            KEY_DATETIMEIN + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EVENTID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_PARTICIPANTID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_FIRSTNAME + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_MIDDLENAME + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_LASTNAME + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_AGE + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            KEY_GENDER + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_ADDRESS + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_OTHERINFO + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_PARTICIPANTNUMBER + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            KEY_CURRENTNOVOTE + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            KEY_LASTDATETIMEVOTED + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_ADDEDBY + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_PROFILEPICTUREURL + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_IMAGEXML + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_STATUS + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA1 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA2 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA3 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA4 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_NOTES1 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_NOTES2  + DBUtils.DATA_TYPE_TEXT + DBUtils.GENERIC_STATEMENT_ENDER;

    private String TRUNCATE_TABLE = DBUtils.DELETE + TABLE_PARTICIPANTS;

    public static ContentValues insert(VotesParticipants data) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID, data.getID());
        contentValues.put(KEY_DATETIMEIN, data.getDateTimeIN());
        contentValues.put(KEY_EVENTID, data.getEventID());
        contentValues.put(KEY_PARTICIPANTID, data.getParticipantID());
        contentValues.put(KEY_FIRSTNAME, data.getFirstName());
        contentValues.put(KEY_MIDDLENAME, data.getMiddleName());
        contentValues.put(KEY_LASTNAME, data.getLastName());
        contentValues.put(KEY_AGE, data.getAge());
        contentValues.put(KEY_GENDER, data.getGender());
        contentValues.put(KEY_ADDRESS, data.getAddress());
        contentValues.put(KEY_OTHERINFO, data.getOtherInfo());
        contentValues.put(KEY_PARTICIPANTNUMBER, data.getParticipantNumber());
        contentValues.put(KEY_CURRENTNOVOTE, data.getCurrentNoVote());
        contentValues.put(KEY_LASTDATETIMEVOTED, data.getLastDateTimeVoted());
        contentValues.put(KEY_ADDEDBY, data.getAddedBy());
        contentValues.put(KEY_PROFILEPICTUREURL, data.getProfilePictureURL());
        contentValues.put(KEY_IMAGEXML, data.getImageXML());
        contentValues.put(KEY_STATUS, data.getStatus());
        contentValues.put(KEY_EXTRA1, data.getExtra1());
        contentValues.put(KEY_EXTRA2, data.getExtra2());
        contentValues.put(KEY_EXTRA3, data.getExtra3());
        contentValues.put(KEY_EXTRA4, data.getExtra4());
        contentValues.put(KEY_NOTES1, data.getNotes1());
        contentValues.put(KEY_NOTES2, data.getNotes2());

        return contentValues;
    }

    public static List<VotesParticipants> getParticipants (Cursor cursor) {
        List<VotesParticipants> postparticipantslist = new ArrayList<>();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                String datetimein = cursor.getString(cursor.getColumnIndex(KEY_DATETIMEIN));
                String eventid = cursor.getString(cursor.getColumnIndex(KEY_EVENTID));
                String participantid = cursor.getString(cursor.getColumnIndex(KEY_PARTICIPANTID));
                String firstname = cursor.getString(cursor.getColumnIndex(KEY_FIRSTNAME));
                String middlename = cursor.getString(cursor.getColumnIndex(KEY_MIDDLENAME));
                String lastname = cursor.getString(cursor.getColumnIndex(KEY_LASTNAME));
                int age = cursor.getInt(cursor.getColumnIndex(KEY_AGE));
                String gender = cursor.getString(cursor.getColumnIndex(KEY_GENDER));
                String address = cursor.getString(cursor.getColumnIndex(KEY_ADDRESS));
                String otherinfo = cursor.getString(cursor.getColumnIndex(KEY_OTHERINFO));
                int participantnumber = cursor.getInt(cursor.getColumnIndex(KEY_PARTICIPANTNUMBER));
                int currentnovote = cursor.getInt(cursor.getColumnIndex(KEY_CURRENTNOVOTE));
                String lastdatetimevoted = cursor.getString(cursor.getColumnIndex(KEY_LASTDATETIMEVOTED));
                String addedby = cursor.getString(cursor.getColumnIndex(KEY_ADDEDBY));
                String profilepictureurl = cursor.getString(cursor.getColumnIndex(KEY_PROFILEPICTUREURL));
                String imagexml = cursor.getString(cursor.getColumnIndex(KEY_IMAGEXML));
                String status = cursor.getString(cursor.getColumnIndex(KEY_STATUS));
                String extra1 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA1));
                String extra2 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA2));
                String extra3 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA3));
                String extra4 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA4));
                String notes1 = cursor.getString(cursor.getColumnIndex(KEY_NOTES1));
                String notes2 = cursor.getString(cursor.getColumnIndex(KEY_NOTES2));

                postparticipantslist.add(new VotesParticipants(id,
                        datetimein, eventid, participantid, firstname, middlename,
                        lastname, age, gender, address, otherinfo, participantnumber ,
                        currentnovote, lastdatetimevoted, addedby, profilepictureurl, imagexml,
                        status, extra1, extra2, extra3, extra4,
                        notes1, notes2
                ));

            }
        }
        return postparticipantslist;
    }
}
