package com.goodkredit.myapplication.model.fairchild;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FairChildMembers implements Parcelable {

     @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("ParticipantID")
    @Expose
    private String ParticipantID;
    @SerializedName("Name")
    @Expose
    private String Name;
    @SerializedName("Position")
    @Expose
    private String Position;
    @SerializedName("TotalVotes")
    @Expose
    private int TotalVotes;
    @SerializedName("ImageUrl")
    @Expose
    private String ImageUrl;
    @SerializedName("VotingPeriodFrom")
    @Expose
    private String VotingPeriodFrom;
    @SerializedName("VotingPeriodTo")
    @Expose
    private String VotingPeriodTo;
    @SerializedName("Status")
    @Expose
    private String Status;
    @SerializedName("Extra1")
    @Expose
    private String Extra1;
    @SerializedName("Extra2")
    @Expose
    private String Extra2;
    @SerializedName("Notes1")
    @Expose
    private String Notes1;

    public FairChildMembers(int ID, String participantID, String name, String position, int totalVotes, String imageUrl, String votingPeriodFrom, String votingPeriodTo, String status, String extra1, String extra2, String notes1) {
        this.ID = ID;
        ParticipantID = participantID;
        Name = name;
        Position = position;
        TotalVotes = totalVotes;
        ImageUrl = imageUrl;
        VotingPeriodFrom = votingPeriodFrom;
        VotingPeriodTo = votingPeriodTo;
        Status = status;
        Extra1 = extra1;
        Extra2 = extra2;
        Notes1 = notes1;
    }

    protected FairChildMembers(Parcel in) {
        ID = in.readInt();
        ParticipantID = in.readString();
        Name = in.readString();
        Position = in.readString();
        TotalVotes = in.readInt();
        ImageUrl = in.readString();
        VotingPeriodFrom = in.readString();
        VotingPeriodTo = in.readString();
        Status = in.readString();
        Extra1 = in.readString();
        Extra2 = in.readString();
        Notes1 = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(ParticipantID);
        dest.writeString(Name);
        dest.writeString(Position);
        dest.writeInt(TotalVotes);
        dest.writeString(ImageUrl);
        dest.writeString(VotingPeriodFrom);
        dest.writeString(VotingPeriodTo);
        dest.writeString(Status);
        dest.writeString(Extra1);
        dest.writeString(Extra2);
        dest.writeString(Notes1);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FairChildMembers> CREATOR = new Creator<FairChildMembers>() {
        @Override
        public FairChildMembers createFromParcel(Parcel in) {
            return new FairChildMembers(in);
        }

        @Override
        public FairChildMembers[] newArray(int size) {
            return new FairChildMembers[size];
        }
    };

    public int getID() {
        return ID;
    }

    public String getParticipantID() {
        return ParticipantID;
    }

    public String getName() {
        return Name;
    }

    public String getPosition() {
        return Position;
    }

    public int getTotalVotes() {
        return TotalVotes;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public String getVotingPeriodFrom() {
        return VotingPeriodFrom;
    }

    public String getVotingPeriodTo() {
        return VotingPeriodTo;
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

    public String getNotes1() {
        return Notes1;
    }
}
