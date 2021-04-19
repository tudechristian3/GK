package com.goodkredit.myapplication.database.fairchild;

import android.content.ContentValues;
import android.database.Cursor;

import com.goodkredit.myapplication.database.DBUtils;
import com.goodkredit.myapplication.model.fairchild.FairChildMembers;

import java.util.ArrayList;
import java.util.List;

public class FairChildMembersDB {
    //Table Name
    public static final String TABLE_FAIRCHILDMEMBERS = "table_fairchildmembers";

    //Values
    public static final String KEY_ID = "ID";
    public static final String KEY_PARTICIPANTID = "ParticipantID";
    public static final String KEY_NAME = "Name";
    public static final String KEY_POSITION = "Position";
    public static final String KEY_TOTALVOTES = "TotalVotes";
    public static final String KEY_IMAGEURL = "ImageUrl";
    public static final String KEY_VOTINGPERIODFROM = "VotingPeriodFrom";
    public static final String KEY_VOTINGPERIODTO = "VotingPeriodTo";
    public static final String KEY_STATUS = "Status";
    public static final String KEY_EXTRA1 = "Extra1";
    public static final String KEY_EXTRA2 = "Extra2";
    public static final String KEY_NOTES1 = "Notes1";


    public static String CREATE_TABLE_FAIRCHILDMEMBERS = DBUtils.CT_IF_NOT_EXISTS + TABLE_FAIRCHILDMEMBERS + DBUtils.GENERIC_ID +
            DBUtils.DATA_TYPE_INTEGER + DBUtils.CONSTRAINT_PRIMARY_KEY +
            DBUtils.CONSTRAINT_AUTOINCREMENT + DBUtils.COMMA +
            KEY_ID + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            KEY_PARTICIPANTID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_NAME + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_POSITION + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_TOTALVOTES + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            KEY_IMAGEURL + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_VOTINGPERIODFROM + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_VOTINGPERIODTO + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_STATUS + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA1 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA2 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_NOTES1 + DBUtils.DATA_TYPE_TEXT + DBUtils.GENERIC_STATEMENT_ENDER;

    private String TRUNCATE_TABLE = DBUtils.DELETE + TABLE_FAIRCHILDMEMBERS;

    public static ContentValues insert(FairChildMembers data) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID, data.getID());
        contentValues.put(KEY_PARTICIPANTID, data.getParticipantID());
        contentValues.put(KEY_NAME, data.getName());
        contentValues.put(KEY_POSITION, data.getPosition());
        contentValues.put(KEY_TOTALVOTES, data.getTotalVotes());
        contentValues.put(KEY_IMAGEURL, data.getImageUrl());
        contentValues.put(KEY_VOTINGPERIODFROM, data.getVotingPeriodFrom());
        contentValues.put(KEY_VOTINGPERIODTO, data.getVotingPeriodTo());
        contentValues.put(KEY_STATUS, data.getStatus());
        contentValues.put(KEY_EXTRA1, data.getExtra1());
        contentValues.put(KEY_EXTRA2, data.getExtra2());
        contentValues.put(KEY_NOTES1, data.getNotes1());

        return contentValues;
    }

    public static List<FairChildMembers> getFairChildMembers (Cursor cursor) {
        List<FairChildMembers> fairChildMembersList = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                String participantid = cursor.getString(cursor.getColumnIndex(KEY_PARTICIPANTID));
                String name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
                String position = cursor.getString(cursor.getColumnIndex(KEY_POSITION));
                int totalvotes = cursor.getInt(cursor.getColumnIndex(KEY_TOTALVOTES));
                String imageurl = cursor.getString(cursor.getColumnIndex(KEY_IMAGEURL));
                String votingperiodfrom = cursor.getString(cursor.getColumnIndex(KEY_VOTINGPERIODFROM));
                String votingperiodto = cursor.getString(cursor.getColumnIndex(KEY_VOTINGPERIODTO));
                String status = cursor.getString(cursor.getColumnIndex(KEY_STATUS));
                String extra1 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA1));
                String extra2 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA2));
                String notes1 = cursor.getString(cursor.getColumnIndex(KEY_NOTES1));

                fairChildMembersList.add(new FairChildMembers(id, participantid, name, position,
                         totalvotes, imageurl, votingperiodfrom, votingperiodto,
                         status, extra1, extra2, notes1
                ));
            }
        }
        return fairChildMembersList;
    }
}
