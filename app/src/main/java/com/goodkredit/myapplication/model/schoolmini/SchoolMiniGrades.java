package com.goodkredit.myapplication.model.schoolmini;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SchoolMiniGrades implements Parcelable {
    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("DateTimeIN")
    @Expose
    private String DateTimeIN;
    @SerializedName("StudentID")
    @Expose
    private String StudentID;
    @SerializedName("Course")
    @Expose
    private String Course;
    @SerializedName("YearLevel")
    @Expose
    private String YearLevel;
    @SerializedName("Semester")
    @Expose
    private String Semester;
    @SerializedName("ExamTerm")
    @Expose
    private String ExamTerm;
    @SerializedName("SubjectID")
    @Expose
    private String SubjectID;
    @SerializedName("SubjectDescription")
    @Expose
    private String SubjectDescription;
    @SerializedName("SubjectSchedule")
    @Expose
    private String SubjectSchedule;
    @SerializedName("SubjectRoom")
    @Expose
    private String SubjectRoom;
    @SerializedName("SubjectUnit")
    @Expose
    private String SubjectUnit;
    @SerializedName("Type")
    @Expose
    private String Type;
    @SerializedName("SchoolYear")
    @Expose
    private String SchoolYear;
    @SerializedName("StudentGrade")
    @Expose
    private String StudentGrade;
    @SerializedName("Status")
    @Expose
    private String Status;
    @SerializedName("Extra1")
    @Expose
    private String Extra1;
    @SerializedName("Extra2")
    @Expose
    private String Extra2;
    @SerializedName("Extra3")
    @Expose
    private String Extra3;
    @SerializedName("Extra4")
    @Expose
    private String Extra4;
    @SerializedName("Notes1")
    @Expose
    private String Notes1;
    @SerializedName("Notes2")
    @Expose
    private String Notes2;

    public SchoolMiniGrades(int ID, String dateTimeIN, String studentID, String course, String yearLevel, String semester, String examTerm, String subjectID, String subjectDescription, String subjectSchedule, String subjectRoom, String subjectUnit, String type, String schoolYear, String studentGrade, String status, String extra1, String extra2, String extra3, String extra4, String notes1, String notes2) {
        this.ID = ID;
        DateTimeIN = dateTimeIN;
        StudentID = studentID;
        Course = course;
        YearLevel = yearLevel;
        Semester = semester;
        ExamTerm = examTerm;
        SubjectID = subjectID;
        SubjectDescription = subjectDescription;
        SubjectSchedule = subjectSchedule;
        SubjectRoom = subjectRoom;
        SubjectUnit = subjectUnit;
        Type = type;
        SchoolYear = schoolYear;
        StudentGrade = studentGrade;
        Status = status;
        Extra1 = extra1;
        Extra2 = extra2;
        Extra3 = extra3;
        Extra4 = extra4;
        Notes1 = notes1;
        Notes2 = notes2;
    }

    protected SchoolMiniGrades(Parcel in) {
        ID = in.readInt();
        DateTimeIN = in.readString();
        StudentID = in.readString();
        Course = in.readString();
        YearLevel = in.readString();
        Semester = in.readString();
        ExamTerm = in.readString();
        SubjectID = in.readString();
        SubjectDescription = in.readString();
        SubjectSchedule = in.readString();
        SubjectRoom = in.readString();
        SubjectUnit = in.readString();
        Type = in.readString();
        SchoolYear = in.readString();
        StudentGrade = in.readString();
        Status = in.readString();
        Extra1 = in.readString();
        Extra2 = in.readString();
        Extra3 = in.readString();
        Extra4 = in.readString();
        Notes1 = in.readString();
        Notes2 = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(DateTimeIN);
        dest.writeString(StudentID);
        dest.writeString(Course);
        dest.writeString(YearLevel);
        dest.writeString(Semester);
        dest.writeString(ExamTerm);
        dest.writeString(SubjectID);
        dest.writeString(SubjectDescription);
        dest.writeString(SubjectSchedule);
        dest.writeString(SubjectRoom);
        dest.writeString(SubjectUnit);
        dest.writeString(Type);
        dest.writeString(SchoolYear);
        dest.writeString(StudentGrade);
        dest.writeString(Status);
        dest.writeString(Extra1);
        dest.writeString(Extra2);
        dest.writeString(Extra3);
        dest.writeString(Extra4);
        dest.writeString(Notes1);
        dest.writeString(Notes2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SchoolMiniGrades> CREATOR = new Creator<SchoolMiniGrades>() {
        @Override
        public SchoolMiniGrades createFromParcel(Parcel in) {
            return new SchoolMiniGrades(in);
        }

        @Override
        public SchoolMiniGrades[] newArray(int size) {
            return new SchoolMiniGrades[size];
        }
    };

    public int getID() {
        return ID;
    }

    public String getDateTimeIN() {
        return DateTimeIN;
    }

    public String getStudentID() {
        return StudentID;
    }

    public String getCourse() {
        return Course;
    }

    public String getYearLevel() {
        return YearLevel;
    }

    public String getSemester() {
        return Semester;
    }

    public String getExamTerm() {
        return ExamTerm;
    }

    public String getSubjectID() {
        return SubjectID;
    }

    public String getSubjectDescription() {
        return SubjectDescription;
    }

    public String getSubjectSchedule() {
        return SubjectSchedule;
    }

    public String getSubjectRoom() {
        return SubjectRoom;
    }

    public String getSubjectUnit() {
        return SubjectUnit;
    }

    public String getType() {
        return Type;
    }

    public String getSchoolYear() {
        return SchoolYear;
    }

    public String getStudentGrade() {
        return StudentGrade;
    }

    public String getStatus() {
        return Status;
    }

    public String getExtra1() {
        return Extra1;
    }

    public String getExtra2() {
        return Extra2;
    }

    public String getExtra3() {
        return Extra3;
    }

    public String getExtra4() {
        return Extra4;
    }

    public String getNotes1() {
        return Notes1;
    }

    public String getNotes2() {
        return Notes2;
    }
}
