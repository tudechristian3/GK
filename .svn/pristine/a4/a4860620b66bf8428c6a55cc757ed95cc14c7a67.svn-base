package com.goodkredit.myapplication.model.mdp;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MDPSupportFAQ implements Parcelable {

    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("HelpTopic")
    @Expose
    private String HelpTopic;
    @SerializedName("FAQID")
    @Expose
    private String FAQID;
    @SerializedName("Question")
    @Expose
    private String Question;
    @SerializedName("Answer")
    @Expose
    private String Answer;
    @SerializedName("Order")
    @Expose
    private int Order;
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

    public MDPSupportFAQ(int ID, String helpTopic, String FAQID, String question, String answer, int order,
                         String extra1, String extra2, String extra3, String notes1, String notes2) {
        this.ID = ID;
        HelpTopic = helpTopic;
        this.FAQID = FAQID;
        Question = question;
        Answer = answer;
        Order = order;
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

    public String getFAQID() {
        return FAQID;
    }

    public String getQuestion() {
        return Question;
    }

    public String getAnswer() {
        return Answer;
    }

    public int getOrder() {
        return Order;
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
        dest.writeString(this.FAQID);
        dest.writeString(this.Question);
        dest.writeString(this.Answer);
        dest.writeInt(this.Order);
        dest.writeString(this.Extra1);
        dest.writeString(this.Extra2);
        dest.writeString(this.Extra3);
        dest.writeString(this.Notes1);
        dest.writeString(this.Notes2);
    }

    protected MDPSupportFAQ(Parcel in) {
        this.ID = in.readInt();
        this.HelpTopic = in.readString();
        this.FAQID = in.readString();
        this.Question = in.readString();
        this.Answer = in.readString();
        this.Order = in.readInt();
        this.Extra1 = in.readString();
        this.Extra2 = in.readString();
        this.Extra3 = in.readString();
        this.Notes1 = in.readString();
        this.Notes2 = in.readString();
    }

    public static final Parcelable.Creator<MDPSupportFAQ> CREATOR = new Parcelable.Creator<MDPSupportFAQ>() {
        @Override
        public MDPSupportFAQ createFromParcel(Parcel source) {
            return new MDPSupportFAQ(source);
        }

        @Override
        public MDPSupportFAQ[] newArray(int size) {
            return new MDPSupportFAQ[size];
        }
    };
}
