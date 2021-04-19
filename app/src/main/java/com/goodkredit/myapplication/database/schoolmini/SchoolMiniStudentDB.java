package com.goodkredit.myapplication.database.schoolmini;

import android.content.ContentValues;
import android.database.Cursor;

import com.goodkredit.myapplication.database.DBUtils;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniStudents;

import java.util.ArrayList;

public class SchoolMiniStudentDB {
    //Table Name
    public static final String TABLE_STUDENTS = "table_students";

    //Values
    public static final String KEY_ID = "ID";
    public static final String KEY_SCHOOLID = "SchoolID";
    public static final String KEY_BORROWERID = "BorrowerID";
    public static final String KEY_STUDENTID = "StudentID";
    public static final String KEY_MOBILENUMBER = "MobileNumber";
    public static final String KEY_FIRSTNAME = "FirstName";
    public static final String KEY_MIDDLENAME = "MiddleName";
    public static final String KEY_LASTNAME = "LastName";
    public static final String KEY_COURSE = "Course";
    public static final String KEY_YEARLEVEL = "YearLevel";
    public static final String KEY_EMAILADDRESS = "EmailAddress";
    public static final String KEY_GENDER = "Gender";
    public static final String KEY_BIRTHDATE = "Birthdate";
    public static final String KEY_STREETADDRESS = "StreetAddress";
    public static final String KEY_PROVINCE = "Province";
    public static final String KEY_CITY = "City";
    public static final String KEY_COUNTRY = "Country";
    public static final String KEY_DATETIMEUPLOADED = "DateTimeUploaded";
    public static final String KEY_STATUS = "Status";
    public static final String KEY_EXTRA1 = "Extra1";
    public static final String KEY_EXTRA2 = "Extra2";
    public static final String KEY_EXTRA3 = "Extra3";
    public static final String KEY_EXTRA4 = "Extra4";
    public static final String KEY_NOTES1 = "Notes1";
    public static final String KEY_NOTES2 = "Notes2";


    public static String CREATE_TABLE_STUDENTS = DBUtils.CT_IF_NOT_EXISTS + TABLE_STUDENTS + DBUtils.GENERIC_ID +
            DBUtils.DATA_TYPE_INTEGER + DBUtils.CONSTRAINT_PRIMARY_KEY +
            DBUtils.CONSTRAINT_AUTOINCREMENT + DBUtils.COMMA +
            KEY_ID + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            KEY_SCHOOLID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_BORROWERID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_STUDENTID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_MOBILENUMBER + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_FIRSTNAME + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_MIDDLENAME + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_LASTNAME + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_COURSE + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_YEARLEVEL + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EMAILADDRESS + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_GENDER + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_BIRTHDATE + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_STREETADDRESS + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_PROVINCE + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_CITY + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_COUNTRY + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_DATETIMEUPLOADED + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_STATUS + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA1 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA2 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA3 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA4 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_NOTES1 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_NOTES2 + DBUtils.DATA_TYPE_TEXT + DBUtils.GENERIC_STATEMENT_ENDER;

    private String TRUNCATE_TABLE = DBUtils.DELETE + TABLE_STUDENTS;

    public static ContentValues insert(SchoolMiniStudents data) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID, data.getID());
        contentValues.put(KEY_SCHOOLID, data.getSchoolID());
        contentValues.put(KEY_BORROWERID, data.getBorrowerID());
        contentValues.put(KEY_STUDENTID, data.getStudentID());
        contentValues.put(KEY_MOBILENUMBER, data.getMobileNumber());
        contentValues.put(KEY_FIRSTNAME, data.getFirstName());
        contentValues.put(KEY_MIDDLENAME, data.getMiddleName());
        contentValues.put(KEY_LASTNAME, data.getLastName());
        contentValues.put(KEY_COURSE, data.getCourse());
        contentValues.put(KEY_YEARLEVEL, data.getYearLevel());
        contentValues.put(KEY_EMAILADDRESS, data.getEmailAddress());
        contentValues.put(KEY_GENDER, data.getGender());
        contentValues.put(KEY_BIRTHDATE, data.getBirthdate());
        contentValues.put(KEY_STREETADDRESS, data.getStreetAddress());
        contentValues.put(KEY_PROVINCE, data.getProvince());
        contentValues.put(KEY_CITY, data.getCity());
        contentValues.put(KEY_COUNTRY, data.getCountry());
        contentValues.put(KEY_DATETIMEUPLOADED, data.getDateTimeUploaded());
        contentValues.put(KEY_STATUS, data.getStatus());
        contentValues.put(KEY_EXTRA1, data.getExtra1());
        contentValues.put(KEY_EXTRA2, data.getExtra2());
        contentValues.put(KEY_EXTRA3, data.getExtra3());
        contentValues.put(KEY_EXTRA4, data.getExtra4());
        contentValues.put(KEY_NOTES1, data.getNotes1());
        contentValues.put(KEY_NOTES2, data.getNotes2());

        return contentValues;
    }

    public static ArrayList<SchoolMiniStudents> getSchoolMiniStudents(Cursor cursor) {
        ArrayList<SchoolMiniStudents>  schoolministudentslist = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                String schoolid = cursor.getString(cursor.getColumnIndex(KEY_SCHOOLID));
                String borrowerid = cursor.getString(cursor.getColumnIndex(KEY_BORROWERID));
                String studentid = cursor.getString(cursor.getColumnIndex(KEY_STUDENTID));
                String mobilenumber = cursor.getString(cursor.getColumnIndex(KEY_MOBILENUMBER));
                String firstname = cursor.getString(cursor.getColumnIndex(KEY_FIRSTNAME));
                String middlename = cursor.getString(cursor.getColumnIndex(KEY_MIDDLENAME));
                String lastname = cursor.getString(cursor.getColumnIndex(KEY_LASTNAME));
                String course = cursor.getString(cursor.getColumnIndex(KEY_COURSE));
                String yearlevel = cursor.getString(cursor.getColumnIndex(KEY_YEARLEVEL));
                String emailaddress = cursor.getString(cursor.getColumnIndex(KEY_EMAILADDRESS));
                String gender = cursor.getString(cursor.getColumnIndex(KEY_GENDER));
                String birthdate = cursor.getString(cursor.getColumnIndex(KEY_BIRTHDATE));
                String streetaddress = cursor.getString(cursor.getColumnIndex(KEY_STREETADDRESS));
                String province = cursor.getString(cursor.getColumnIndex(KEY_PROVINCE));
                String city = cursor.getString(cursor.getColumnIndex(KEY_CITY));
                String country = cursor.getString(cursor.getColumnIndex(KEY_COUNTRY));
                String datetimeuploaded = cursor.getString(cursor.getColumnIndex(KEY_DATETIMEUPLOADED));
                String status = cursor.getString(cursor.getColumnIndex(KEY_STATUS));
                String extra1 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA1));
                String extra2 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA2));
                String extra3 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA3));
                String extra4 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA4));
                String notes1 = cursor.getString(cursor.getColumnIndex(KEY_NOTES1));
                String notes2 = cursor.getString(cursor.getColumnIndex(KEY_NOTES2));


                schoolministudentslist.add(new SchoolMiniStudents(
                        id, schoolid, borrowerid, studentid, mobilenumber, firstname, middlename, lastname, course,
                        yearlevel, emailaddress, gender, birthdate, streetaddress, province, city, country, datetimeuploaded,
                        status, extra1, extra2, extra3, extra4, notes1, notes2
                ));

            }
        }
        return schoolministudentslist;
    }
}
