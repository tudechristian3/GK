package com.goodkredit.myapplication.responses.support;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ban_Lenovo on 3/6/2017.
 */

public class GetHelpTopicsResponseData implements Parcelable {

    @SerializedName("ID")
    @Expose
    private String ID;
    @SerializedName("HelpTopic")
    @Expose
    private String HelpTopic;
    @SerializedName("Category")
    @Expose
    private String Category;
    @SerializedName("Priority")
    @Expose
    private String Priority;
    @SerializedName("SupportDepartment")
    @Expose
    private String SupportDepartment;
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
    @SerializedName("Logo")
    @Expose
    private String Logo;

    @SerializedName("Description")
    @Expose
    private String Description;

    public GetHelpTopicsResponseData(String ID, String helpTopic, String category,
                                     String priority, String supportDepartment, String extra1,
                                     String extra2, String extra3, String notes1,
                                     String notes2, String logo, String description) {
        this.ID = ID;
        HelpTopic = helpTopic;
        Category = category;
        Priority = priority;
        SupportDepartment = supportDepartment;
        Extra1 = extra1;
        Extra2 = extra2;
        Extra3 = extra3;
        Notes1 = notes1;
        Notes2 = notes2;
        Logo = logo;
        Description = description;
    }

    public String getID() {
        return ID;
    }

    public String getHelpTopic() {
        return HelpTopic;
    }

    public String getCategory() {
        return Category;
    }

    public String getPriority() {
        return Priority;
    }

    public String getSupportDepartment() {
        return SupportDepartment;
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

    public String getLogo() {
        return Logo;
    }

    public String getDescription() {
        return Description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ID);
        dest.writeString(HelpTopic);
        dest.writeString(Category);
        dest.writeString(Priority);
        dest.writeString(SupportDepartment);
        dest.writeString(Extra1);
        dest.writeString(Extra2);
        dest.writeString(Extra3);
        dest.writeString(Notes1);
        dest.writeString(Notes2);
        dest.writeString(Logo);
        dest.writeString(Description);
    }

    public GetHelpTopicsResponseData(Parcel in) {
        this.ID = in.readString();
        this.HelpTopic = in.readString();
        this.Category = in.readString();
        this.Priority = in.readString();
        this.SupportDepartment = in.readString();
        this.Extra1 = in.readString();
        this.Extra2 = in.readString();
        this.Extra3 = in.readString();
        this.Notes1 = in.readString();
        this.Notes2 = in.readString();
        this.Logo = in.readString();
        this.Description = in.readString();
    }

    public static final Parcelable.Creator<GetHelpTopicsResponseData> CREATOR
            = new Parcelable.Creator<GetHelpTopicsResponseData>() {
        public GetHelpTopicsResponseData createFromParcel(Parcel in) {
            return new GetHelpTopicsResponseData(in);
        }

        public GetHelpTopicsResponseData[] newArray(int size) {
            return new GetHelpTopicsResponseData[size];
        }
    };


}
