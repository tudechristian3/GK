package com.goodkredit.myapplication.database.schoolmini;

import android.content.ContentValues;
import android.database.Cursor;

import com.goodkredit.myapplication.database.DBUtils;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniGrades;

import java.util.ArrayList;
import java.util.List;

public class SchoolMiniGradesDB {
    //Table Name
    public static final String TABLE_GRADES = "table_grades";

    //Values
    public static final String KEY_ID = "ID";
    public static final String KEY_DATETIMEIN = "DateTimeIN";
    public static final String KEY_STUDENTID = "StudentID";
    public static final String KEY_COURSE = "Course";
    public static final String KEY_YEARLEVEL = "YearLevel";
    public static final String KEY_SEMESTER = "Semester";
    public static final String KEY_EXAMTERM = "ExamTerm";
    public static final String KEY_SUBJECTID = "SubjectID";
    public static final String KEY_SUBJECTDESCRIPTION = "SubjectDescription";
    public static final String KEY_SUBJECTSCHEDULE = "SubjectSchedule";
    public static final String KEY_SUBJECTROOM = "SubjectRoom";
    public static final String KEY_SUBJECTUNIT = "SubjectUnit";
    public static final String KEY_TYPE = "Type";
    public static final String KEY_SCHOOLYEAR = "SchoolYear";
    public static final String KEY_STUDENTGRADE = "StudentGrade";
    public static final String KEY_STATUS = "Status";
    public static final String KEY_EXTRA1 = "Extra1";
    public static final String KEY_EXTRA2 = "Extra2";
    public static final String KEY_EXTRA3 = "Extra3";
    public static final String KEY_EXTRA4 = "Extra4";
    public static final String KEY_NOTES1 = "Notes1";
    public static final String KEY_NOTES2 = "Notes2";



    public static String CREATE_TABLE_GRADES = DBUtils.CT_IF_NOT_EXISTS + TABLE_GRADES + DBUtils.GENERIC_ID +
            DBUtils.DATA_TYPE_INTEGER + DBUtils.CONSTRAINT_PRIMARY_KEY +
            DBUtils.CONSTRAINT_AUTOINCREMENT + DBUtils.COMMA +
            KEY_ID + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            KEY_DATETIMEIN + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_STUDENTID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_COURSE + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_YEARLEVEL + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_SEMESTER + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXAMTERM + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_SUBJECTID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_SUBJECTDESCRIPTION + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_SUBJECTSCHEDULE + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_SUBJECTROOM + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_SUBJECTUNIT + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_TYPE + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_SCHOOLYEAR + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_STUDENTGRADE + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_STATUS + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA1 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA2 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA3 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA4 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_NOTES1 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_NOTES2  + DBUtils.DATA_TYPE_TEXT + DBUtils.GENERIC_STATEMENT_ENDER;

    private String TRUNCATE_TABLE = DBUtils.DELETE + TABLE_GRADES;

    public static ContentValues insert(SchoolMiniGrades data) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID, data.getID());
        contentValues.put(KEY_DATETIMEIN, data.getDateTimeIN());
        contentValues.put(KEY_STUDENTID, data.getStudentID());
        contentValues.put(KEY_COURSE, data.getCourse());
        contentValues.put(KEY_YEARLEVEL, data.getYearLevel());
        contentValues.put(KEY_SEMESTER, data.getSemester());
        contentValues.put(KEY_EXAMTERM, data.getExamTerm());
        contentValues.put(KEY_SUBJECTID, data.getSubjectID());
        contentValues.put(KEY_SUBJECTDESCRIPTION, data.getSubjectDescription());
        contentValues.put(KEY_SUBJECTSCHEDULE, data.getSubjectSchedule());
        contentValues.put(KEY_SUBJECTROOM, data.getSubjectRoom());
        contentValues.put(KEY_SUBJECTUNIT, data.getSubjectUnit());
        contentValues.put(KEY_TYPE, data.getType());
        contentValues.put(KEY_SCHOOLYEAR, data.getSchoolYear());
        contentValues.put(KEY_STUDENTGRADE, data.getStudentGrade());
        contentValues.put(KEY_STATUS, data.getStatus());
        contentValues.put(KEY_EXTRA1, data.getExtra1());
        contentValues.put(KEY_EXTRA2, data.getExtra2());
        contentValues.put(KEY_EXTRA3, data.getExtra3());
        contentValues.put(KEY_EXTRA4, data.getExtra4());
        contentValues.put(KEY_NOTES1, data.getNotes1());
        contentValues.put(KEY_NOTES2, data.getNotes2());

        return contentValues;
    }

    public static List<SchoolMiniGrades> getSchoolMiniGrades(Cursor cursor) {
        List<SchoolMiniGrades>  schoolminigradeslist = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                String datetimein = cursor.getString(cursor.getColumnIndex(KEY_DATETIMEIN));
                String studentid = cursor.getString(cursor.getColumnIndex(KEY_STUDENTID));
                String course = cursor.getString(cursor.getColumnIndex(KEY_COURSE));
                String yearlevel = cursor.getString(cursor.getColumnIndex(KEY_YEARLEVEL));
                String semester = cursor.getString(cursor.getColumnIndex(KEY_SEMESTER));
                String examterm = cursor.getString(cursor.getColumnIndex(KEY_EXAMTERM));
                String subjectid = cursor.getString(cursor.getColumnIndex(KEY_SUBJECTID));
                String subjectdescription = cursor.getString(cursor.getColumnIndex(KEY_SUBJECTDESCRIPTION));
                String subjectschedule = cursor.getString(cursor.getColumnIndex(KEY_SUBJECTSCHEDULE));
                String subjectroom = cursor.getString(cursor.getColumnIndex(KEY_SUBJECTROOM));
                String subjectunit = cursor.getString(cursor.getColumnIndex(KEY_SUBJECTUNIT));
                String type = cursor.getString(cursor.getColumnIndex(KEY_TYPE));
                String schoolyear = cursor.getString(cursor.getColumnIndex(KEY_SCHOOLYEAR));
                String studentgrade = cursor.getString(cursor.getColumnIndex(KEY_STUDENTGRADE));
                String status = cursor.getString(cursor.getColumnIndex(KEY_STATUS));
                String extra1 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA1));
                String extra2 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA2));
                String extra3 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA3));
                String extra4 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA4));
                String notes1 = cursor.getString(cursor.getColumnIndex(KEY_NOTES1));
                String notes2 = cursor.getString(cursor.getColumnIndex(KEY_NOTES2));

                schoolminigradeslist.add(new SchoolMiniGrades(id,
                        datetimein, studentid, course, yearlevel, semester, examterm, subjectid,
                        subjectdescription, subjectschedule, subjectroom, subjectunit, type, schoolyear, studentgrade,
                        status, extra1, extra2, extra3, extra4, notes1, notes2
                ));

            }
        }
        return schoolminigradeslist;
    }
}
