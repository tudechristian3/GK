package com.goodkredit.myapplication.model.mdp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MDPSupportHelpTopics implements Parcelable {

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
    @SerializedName("Notes1")
    @Expose
    private String Notes1;
    @SerializedName("Notes2")
    @Expose
    private String Notes2;

    public MDPSupportHelpTopics(int ID, String helpTopic, String category, String priority, String description,
                                String logo, String extra1, String extra2, String extra3, String notes1,
                                String notes2) {
        this.ID = ID;
        HelpTopic = helpTopic;
        Category = category;
        Priority = priority;
        Description = description;
        Logo = logo;
        Extra1 = extra1;
        Extra2 = extra2;
        Extra3 = extra3;
        Notes1 = notes1;
        Notes2 = notes2;
    }

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

    public String getNotes1() {
        return Notes1;
    }

    public String getNotes2() {
        return Notes2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ID);
        dest.writeString(this.HelpTopic);
        dest.writeString(this.Category);
        dest.writeString(this.Priority);
        dest.writeString(this.Description);
        dest.writeString(this.Logo);
        dest.writeString(this.Extra1);
        dest.writeString(this.Extra2);
        dest.writeString(this.Extra3);
        dest.writeString(this.Notes1);
        dest.writeString(this.Notes2);
    }

    protected MDPSupportHelpTopics(Parcel in) {
        this.ID = in.readInt();
        this.HelpTopic = in.readString();
        this.Category = in.readString();
        this.Priority = in.readString();
        this.Description = in.readString();
        this.Logo = in.readString();
        this.Extra1 = in.readString();
        this.Extra2 = in.readString();
        this.Extra3 = in.readString();
        this.Notes1 = in.readString();
        this.Notes2 = in.readString();
    }

    public static final Parcelable.Creator<MDPSupportHelpTopics> CREATOR = new Parcelable.Creator<MDPSupportHelpTopics>() {
        @Override
        public MDPSupportHelpTopics createFromParcel(Parcel source) {
            return new MDPSupportHelpTopics(source);
        }

        @Override
        public MDPSupportHelpTopics[] newArray(int size) {
            return new MDPSupportHelpTopics[size];
        }
    };
}
