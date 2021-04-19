package com.goodkredit.myapplication.model.globaldialogs;

import android.os.Parcel;
import android.os.Parcelable;

public class GlobalDialogsObject implements Parcelable {
    private int ID;
    private int colorID;
    private int textSize;
    private int gravity;
    private int background;
    private int maxLength;
    private String hint;
    private String typeface;

    public GlobalDialogsObject(int colorID, int textSize, int gravity, int background, int maxLength, String hint) {
        this.colorID = colorID;
        this.textSize = textSize;
        this.gravity = gravity;
        this.background = background;
        this.maxLength = maxLength;
        this.hint = hint;
    }

    public GlobalDialogsObject(int colorID, int textSize, int gravity) {
        this.colorID = colorID;
        this.textSize = textSize;
        this.gravity = gravity;
    }

    public GlobalDialogsObject(String typeface, int colorID, int textSize, int gravity) {
        this.typeface = typeface;
        this.colorID = colorID;
        this.textSize = textSize;
        this.gravity = gravity;
    }

    protected GlobalDialogsObject(Parcel in) {
        ID = in.readInt();
        colorID = in.readInt();
        textSize = in.readInt();
        gravity = in.readInt();
        background = in.readInt();
        maxLength = in.readInt();
        hint = in.readString();
        typeface = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeInt(colorID);
        dest.writeInt(textSize);
        dest.writeInt(gravity);
        dest.writeInt(background);
        dest.writeInt(maxLength);
        dest.writeString(hint);
        dest.writeString(typeface);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GlobalDialogsObject> CREATOR = new Creator<GlobalDialogsObject>() {
        @Override
        public GlobalDialogsObject createFromParcel(Parcel in) {
            return new GlobalDialogsObject(in);
        }

        @Override
        public GlobalDialogsObject[] newArray(int size) {
            return new GlobalDialogsObject[size];
        }
    };

    public int getID() {
        return ID;
    }

    public int getColorID() {
        return colorID;
    }

    public int getTextSize() {
        return textSize;
    }

    public int getGravity() {
        return gravity;
    }

    public int getBackground() {
        return background;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public String getHint() {
        return hint;
    }

    public String getTypeface() {
        return typeface;
    }
}
