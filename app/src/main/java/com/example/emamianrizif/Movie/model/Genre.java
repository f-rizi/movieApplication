package com.example.emamianrizif.Movie.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Created by fatemeh on 25/03/15.
 */
public class Genre implements Parcelable {

    public static final String ID_KEY = "id";
    public static final String NAME_KEY = "name";

    private int id;
    private String name;

    public Genre(JSONObject jsonObject) {
        try {
            this.id = jsonObject.getInt(ID_KEY);
            this.name = jsonObject.isNull(NAME_KEY) ?
                    null : jsonObject.getString(NAME_KEY);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
    }

    private Genre(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<Genre> CREATOR = new Parcelable.Creator<Genre>() {
        public Genre createFromParcel(Parcel source) {
            return new Genre(source);
        }

        public Genre[] newArray(int size) {
            return new Genre[size];
        }
    };
}
