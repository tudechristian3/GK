package com.goodkredit.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SkycableSupportHelpTopics implements Parcelable {
    @SerializedName("HelpTopic")
    @Expose
    private String HelpTopic;
    @SerializedName("Category")
    @Expose
    private String Category;
    @SerializedName("Priority")
    @Expose
    private String Priority;
    @SerializedName("DeptID")
    @Expose
    private String DeptID;
    @SerializedName("Description")
    @Expose
    private String Description;
    @SerializedName("Logo")
    @Expose
    private String Logo;

    public SkycableSupportHelpTopics(String helpTopic, String category, String priority, String deptID, String description, String logo) {
        HelpTopic = helpTopic;
        Category = category;
        Priority = priority;
        DeptID = deptID;
        Description = description;
        Logo = logo;
    }

    protected SkycableSupportHelpTopics(Parcel in) {
        HelpTopic = in.readString();
        Category = in.readString();
        Priority = in.readString();
        DeptID = in.readString();
        Description = in.readString();
        Logo = in.readString();
    }

    public static final Creator<SkycableSupportHelpTopics> CREATOR = new Creator<SkycableSupportHelpTopics>() {
        @Override
        public SkycableSupportHelpTopics createFromParcel(Parcel in) {
            return new SkycableSupportHelpTopics(in);
        }

        @Override
        public SkycableSupportHelpTopics[] newArray(int size) {
            return new SkycableSupportHelpTopics[size];
        }
    };

    public String getHelpTopic() {
        return HelpTopic;
    }

    public String getCategory() {
        return Category;
    }

    public String getPriority() {
        return Priority;
    }

    public String getDeptID() {
        return DeptID;
    }

    public String getDescription() {
        return Description;
    }

    public String getLogo() {
        return Logo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(HelpTopic);
        dest.writeString(Category);
        dest.writeString(Priority);
        dest.writeString(DeptID);
        dest.writeString(Description);
        dest.writeString(Logo);
    }
}
