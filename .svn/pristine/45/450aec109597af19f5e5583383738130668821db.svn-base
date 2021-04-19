package com.goodkredit.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SupportHelpTopics implements Parcelable {
    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("HelpTopic")
    @Expose
    private String HelpTopic;
    @SerializedName("Category")
    @Expose
    private String Category;
    @SerializedName("Priority")
    @Expose
    private String Priority;
    @SerializedName("Description")
    @Expose
    private String Description;
    @SerializedName("Logo")
    @Expose
    private String Logo;
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

    public SupportHelpTopics(int ID, String helpTopic, String category, String priority, String description, String logo, String extra1, String extra2, String extra3, String extra4, String notes1, String notes2) {
        this.ID = ID;
        HelpTopic = helpTopic;
        Category = category;
        Priority = priority;
        Description = description;
        Logo = logo;
        Extra1 = extra1;
        Extra2 = extra2;
        Extra3 = extra3;
        Extra4 = extra4;
        Notes1 = notes1;
        Notes2 = notes2;
    }

    protected SupportHelpTopics(Parcel in) {
        ID = in.readInt();
        HelpTopic = in.readString();
        Category = in.readString();
        Priority = in.readString();
        Description = in.readString();
        Logo = in.readString();
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
        dest.writeString(HelpTopic);
        dest.writeString(Category);
        dest.writeString(Priority);
        dest.writeString(Description);
        dest.writeString(Logo);
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

    public static final Creator<SupportHelpTopics> CREATOR = new Creator<SupportHelpTopics>() {
        @Override
        public SupportHelpTopics createFromParcel(Parcel in) {
            return new SupportHelpTopics(in);
        }

        @Override
        public SupportHelpTopics[] newArray(int size) {
            return new SupportHelpTopics[size];
        }
    };

    public int getID() {
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

    public String getDescription() {
        return Description;
    }

    public String getLogo() {
        return Logo;
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
