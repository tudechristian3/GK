package com.goodkredit.myapplication.model.gkads;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GKAds implements Parcelable {

    public static final String KEY_OBJECT = "GKAds";

    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("GKAdID")
    @Expose
    private String GKAdID;
    @SerializedName("Type")
    @Expose
    private String Type;
    @SerializedName("Name")
    @Expose
    private String Name;
    @SerializedName("Description")
    @Expose
    private String Description;
    @SerializedName("URL")
    @Expose
    private String URL;
    @SerializedName("Priority")
    @Expose
    private int Priority;
    @SerializedName("Status")
    @Expose
    private String Status;
    @SerializedName("DurationFrom")
    @Expose
    private String DurationFrom;
    @SerializedName("RedirectionURL")
    @Expose
    private String RedirectionURL;

    protected GKAds(Parcel in) {
        ID = in.readInt();
        GKAdID = in.readString();
        Type = in.readString();
        Name = in.readString();
        Description = in.readString();
        URL = in.readString();
        Priority = in.readInt();
        Status = in.readString();
        DurationFrom = in.readString();
        RedirectionURL = in.readString();
    }

    public static final Creator<GKAds> CREATOR = new Creator<GKAds>() {
        @Override
        public GKAds createFromParcel(Parcel in) {
            return new GKAds(in);
        }

        @Override
        public GKAds[] newArray(int size) {
            return new GKAds[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(GKAdID);
        dest.writeString(Type);
        dest.writeString(Name);
        dest.writeString(Description);
        dest.writeString(URL);
        dest.writeInt(Priority);
        dest.writeString(Status);
        dest.writeString(DurationFrom);
        dest.writeString(RedirectionURL);
    }

    public int getID() {
        return ID;
    }

    public String getGKAdID() {
        return GKAdID;
    }

    public String getType() {
        return Type;
    }

    public String getName() {
        return Name;
    }

    public String getDescription() {
        return Description;
    }

    public String getURL() {
        return URL;
    }

    public int getPriority() {
        return Priority;
    }

    public String getStatus() {
        return Status;
    }

    public String getDurationFrom() {
        return DurationFrom;
    }
    public String getRedirectionURL() {
        return RedirectionURL;
    }
}
