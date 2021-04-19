package com.goodkredit.myapplication.model.schoolmini;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SchoolMiniDtr implements Parcelable {
    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("SchoolID")
    @Expose
    private String SchoolID;
    @SerializedName("StudentID")
    @Expose
    private String StudentID;
    @SerializedName("Type")
    @Expose
    private String Type;
    @SerializedName("DateTimeIN")
    @Expose
    private String DateTimeIN;
    @SerializedName("BatchID")
    @Expose
    private String BatchID;
    @SerializedName("Extra1")
    @Expose
    private String Extra1;
    @SerializedName("Extra2")
    @Expose
    private String Extra2;
    @SerializedName("Extra3")
    @Expose
    private String Extra3;
    @SerializedName("Notes1")
    @Expose
    private String Notes1;
    @SerializedName("Notes2")
    @Expose
    private String Notes2;

    public SchoolMiniDtr(int ID, String schoolID, String studentID, String type, String dateTimeIN, String batchID, String extra1, String extra2, String extra3, String notes1, String notes2) {
        this.ID = ID;
        SchoolID = schoolID;
        StudentID = studentID;
        Type = type;
        DateTimeIN = dateTimeIN;
        BatchID = batchID;
        Extra1 = extra1;
        Extra2 = extra2;
        Extra3 = extra3;
        Notes1 = notes1;
        Notes2 = notes2;
    }

    protected SchoolMiniDtr(Parcel in) {
        ID = in.readInt();
        SchoolID = in.readString();
        StudentID = in.readString();
        Type = in.readString();
        DateTimeIN = in.readString();
        BatchID = in.readString();
        Extra1 = in.readString();
        Extra2 = in.readString();
        Extra3 = in.readString();
        Notes1 = in.readString();
        Notes2 = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(SchoolID);
        dest.writeString(StudentID);
        dest.writeString(Type);
        dest.writeString(DateTimeIN);
        dest.writeString(BatchID);
        dest.writeString(Extra1);
        dest.writeString(Extra2);
        dest.writeString(Extra3);
        dest.writeString(Notes1);
        dest.writeString(Notes2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SchoolMiniDtr> CREATOR = new Creator<SchoolMiniDtr>() {
        @Override
        public SchoolMiniDtr createFromParcel(Parcel in) {
            return new SchoolMiniDtr(in);
        }

        @Override
        public SchoolMiniDtr[] newArray(int size) {
            return new SchoolMiniDtr[size];
        }
    };

    public int getID() {
        return ID;
    }

    public String getSchoolID() {
        return SchoolID;
    }

    public String getStudentID() {
        return StudentID;
    }

    public String getType() {
        return Type;
    }

    public String getDateTimeIN() {
        return DateTimeIN;
    }

    public String getBatchID() {
        return BatchID;
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

    public String getNotes1() {
        return Notes1;
    }

    public String getNotes2() {
        return Notes2;
    }
}
