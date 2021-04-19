package com.goodkredit.myapplication.database.schoolmini;

import android.content.ContentValues;
import android.database.Cursor;

import com.goodkredit.myapplication.database.DBUtils;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniTuitionFee;

import java.util.ArrayList;
import java.util.List;

public class SchoolMiniTuitionFeeDB {
    //Table Name
    public static final String TABLE_TUITIONFEE = "table_tuitionfee";

    //Values
    public static final String KEY_ID = "ID";
    public static final String KEY_DATETIMEIN = "DateTimeIN";
    public static final String KEY_DATETIMECOMPLETED = "DateTimeCompleted";
    public static final String KEY_SOAID = "SOAID";
    public static final String KEY_BILLINGID = "BillingID";
    public static final String KEY_SCHOOLID = "SchoolID";
    public static final String KEY_SCHOOLNAME = "SchoolName";
    public static final String KEY_MERCHANTID = "MerchantID";
    public static final String KEY_MERCHANTNAME = "MerchantName";
    public static final String KEY_BORROWERID = "BorrowerID";
    public static final String KEY_STUDENTID = "StudentID";
    public static final String KEY_COURSE = "Course";
    public static final String KEY_YEARLEVEL = "YearLevel";
    public static final String KEY_SEMESTER = "Semester";
    public static final String KEY_EXAMTERM = "ExamTerm";
    public static final String KEY_PARTICULARS = "Particulars";
    public static final String KEY_TOTALAMOUNTTOPAY = "TotalAmountToPay";
    public static final String KEY_BALANCE = "Balance";
    public static final String KEY_DATETIMEPAID = "DateTimePaid";
    public static final String KEY_LASTAMOUNTPAID = "LastAmountPaid";
    public static final String KEY_PREVSTUDENTPAYABLES = "PrevStudentPayables";
    public static final String KEY_POSTSTUDENTPAYABLES = "PostStudentPayables";
    public static final String KEY_TYPE = "Type";
    public static final String KEY_SCHOOLYEAR = "SchoolYear";
    public static final String KEY_ARCHIVEID = "ArchiveID";
    public static final String KEY_STATUS = "Status";
    public static final String KEY_EXTRA1 = "Extra1";
    public static final String KEY_EXTRA2 = "Extra2";
    public static final String KEY_EXTRA3 = "Extra3";
    public static final String KEY_EXTRA4 = "Extra4";
    public static final String KEY_NOTES1 = "Notes1";
    public static final String KEY_NOTES2 = "Notes2";


    public static String CREATE_TABLE_TUITIONFEE  = DBUtils.CT_IF_NOT_EXISTS + TABLE_TUITIONFEE + DBUtils.GENERIC_ID +
            DBUtils.DATA_TYPE_INTEGER + DBUtils.CONSTRAINT_PRIMARY_KEY +
            DBUtils.CONSTRAINT_AUTOINCREMENT + DBUtils.COMMA +
            KEY_ID + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            KEY_DATETIMEIN + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_DATETIMECOMPLETED + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_SOAID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_BILLINGID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_SCHOOLID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_SCHOOLNAME + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_MERCHANTID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_MERCHANTNAME + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_BORROWERID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_STUDENTID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_COURSE + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_YEARLEVEL + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_SEMESTER + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXAMTERM + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_PARTICULARS + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_TOTALAMOUNTTOPAY + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_BALANCE + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_DATETIMEPAID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_LASTAMOUNTPAID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_PREVSTUDENTPAYABLES + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_POSTSTUDENTPAYABLES + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_TYPE + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_SCHOOLYEAR + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_ARCHIVEID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_STATUS + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA1 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA2 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA3 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA4 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_NOTES1 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_NOTES2 + DBUtils.DATA_TYPE_TEXT + DBUtils.GENERIC_STATEMENT_ENDER;

    private String TRUNCATE_TABLE = DBUtils.DELETE + TABLE_TUITIONFEE;

    public static ContentValues insert(SchoolMiniTuitionFee data) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID, data.getID());
        contentValues.put(KEY_DATETIMEIN, data.getDateTimeIN());
        contentValues.put(KEY_DATETIMECOMPLETED, data.getDateTimeCompleted());
        contentValues.put(KEY_SOAID, data.getSOAID());
        contentValues.put(KEY_BILLINGID, data.getBillingID());
        contentValues.put(KEY_SCHOOLID, data.getSchoolID());
        contentValues.put(KEY_SCHOOLNAME, data.getSchoolName());
        contentValues.put(KEY_MERCHANTID, data.getMerchantID());
        contentValues.put(KEY_MERCHANTNAME, data.getMerchantName());
        contentValues.put(KEY_BORROWERID, data.getBorrowerID());
        contentValues.put(KEY_STUDENTID, data.getStudentID());
        contentValues.put(KEY_COURSE, data.getCourse());
        contentValues.put(KEY_YEARLEVEL, data.getYearLevel());
        contentValues.put(KEY_SEMESTER, data.getSemester());
        contentValues.put(KEY_EXAMTERM, data.getExamTerm());
        contentValues.put(KEY_PARTICULARS, data.getParticulars());
        contentValues.put(KEY_TOTALAMOUNTTOPAY, data.getTotalAmountToPay());
        contentValues.put(KEY_BALANCE, data.getBalance());
        contentValues.put(KEY_DATETIMEPAID, data.getDateTimePaid());
        contentValues.put(KEY_LASTAMOUNTPAID, data.getLastAmountPaid());
        contentValues.put(KEY_PREVSTUDENTPAYABLES, data.getPrevStudentPayables());
        contentValues.put(KEY_POSTSTUDENTPAYABLES, data.getPostStudentPayables());
        contentValues.put(KEY_TYPE, data.getType());
        contentValues.put(KEY_SCHOOLYEAR, data.getSchoolYear());
        contentValues.put(KEY_ARCHIVEID, data.getArchiveID());
        contentValues.put(KEY_STATUS, data.getStatus());
        contentValues.put(KEY_EXTRA1, data.getExtra1());
        contentValues.put(KEY_EXTRA2, data.getExtra2());
        contentValues.put(KEY_EXTRA3, data.getExtra3());
        contentValues.put(KEY_EXTRA4, data.getExtra4());
        contentValues.put(KEY_NOTES1, data.getNotes1());
        contentValues.put(KEY_NOTES2, data.getNotes2());

        return contentValues;
    }

    public static List<SchoolMiniTuitionFee> getSchoolMiniTuitionFee(Cursor cursor) {
        List<SchoolMiniTuitionFee>  schoolministudentslist = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                String datetimein = cursor.getString(cursor.getColumnIndex(KEY_DATETIMEIN));
                String datetimecompleted = cursor.getString(cursor.getColumnIndex(KEY_DATETIMECOMPLETED));
                String soaid = cursor.getString(cursor.getColumnIndex(KEY_SOAID));
                String billingid = cursor.getString(cursor.getColumnIndex(KEY_BILLINGID));
                String schoolid = cursor.getString(cursor.getColumnIndex(KEY_SCHOOLID));
                String schoolname = cursor.getString(cursor.getColumnIndex(KEY_SCHOOLNAME));
                String merchantid = cursor.getString(cursor.getColumnIndex(KEY_MERCHANTID));
                String merchantname = cursor.getString(cursor.getColumnIndex(KEY_MERCHANTNAME));
                String borrowerid = cursor.getString(cursor.getColumnIndex(KEY_BORROWERID));
                String studentid = cursor.getString(cursor.getColumnIndex(KEY_STUDENTID));
                String course = cursor.getString(cursor.getColumnIndex(KEY_COURSE));
                String yearlevel = cursor.getString(cursor.getColumnIndex(KEY_YEARLEVEL));
                String semester = cursor.getString(cursor.getColumnIndex(KEY_SEMESTER));
                String examterm = cursor.getString(cursor.getColumnIndex(KEY_EXAMTERM));
                String particulars = cursor.getString(cursor.getColumnIndex(KEY_PARTICULARS));
                double totalamounttopay = cursor.getDouble(cursor.getColumnIndex(KEY_TOTALAMOUNTTOPAY));
                double balance = cursor.getDouble(cursor.getColumnIndex(KEY_BALANCE));
                String datetimepaid = cursor.getString(cursor.getColumnIndex(KEY_DATETIMEPAID));
                double lastamountpaid = cursor.getDouble(cursor.getColumnIndex(KEY_LASTAMOUNTPAID));
                double prevstudentpayables = cursor.getDouble(cursor.getColumnIndex(KEY_PREVSTUDENTPAYABLES));
                double poststudentpayables = cursor.getDouble(cursor.getColumnIndex(KEY_POSTSTUDENTPAYABLES));
                String type = cursor.getString(cursor.getColumnIndex(KEY_TYPE));
                String schoolyear = cursor.getString(cursor.getColumnIndex(KEY_SCHOOLYEAR));
                String archiveid = cursor.getString(cursor.getColumnIndex(KEY_ARCHIVEID));
                String status = cursor.getString(cursor.getColumnIndex(KEY_STATUS));
                String extra1 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA1));
                String extra2 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA2));
                String extra3 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA3));
                String extra4 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA4));
                String notes1 = cursor.getString(cursor.getColumnIndex(KEY_NOTES1));
                String notes2 = cursor.getString(cursor.getColumnIndex(KEY_NOTES2));

                schoolministudentslist.add(new SchoolMiniTuitionFee(
                        id, datetimein, datetimecompleted, soaid, billingid, schoolid, schoolname, merchantid, merchantname,
                        borrowerid, studentid, course, yearlevel, semester, examterm,
                        particulars, totalamounttopay, balance, datetimepaid, lastamountpaid, prevstudentpayables, poststudentpayables,
                        type, schoolyear, archiveid, status, extra1, extra2, extra3, extra4, notes1, notes2
                ));

            }
        }
        return schoolministudentslist;
    }
}
