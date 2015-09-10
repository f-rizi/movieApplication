package com.example.emamianrizif.Movie.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Created by fatemeh on 23/03/15.
 */

public class MovieCast extends MovieCredits {

    public static final String ORDER_KEY = "order";
    public static final String CAST_ID_KEY = "cast_id";
    public static final String CHARACTER_KEY = "character";

    private int order;
    private int cast_id;

    private String character;

    public MovieCast(JSONObject jsonObject) {
        super(jsonObject);

        try {
            this.id = jsonObject.getInt(ID_KEY);
            this.order = jsonObject.getInt(ORDER_KEY);
            this.cast_id = jsonObject.getInt(CAST_ID_KEY);

            this.name = jsonObject.isNull(NAME_KEY) ?
                    null : jsonObject.getString(NAME_KEY);

            this.creditId = jsonObject.isNull(CREDIT_ID_KEY) ?
                    null : jsonObject.getString(CREDIT_ID_KEY);

            this.character = jsonObject.isNull(CHARACTER_KEY) ?
                    null : jsonObject.getString(CHARACTER_KEY);

            this.profilePath = jsonObject.isNull(PROFILE_PATH_KEY) ?
                    null :jsonObject.getString(PROFILE_PATH_KEY);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getOrder() {
        return order;
    }

    public String getCharacter() {
        return character;
    }

    public int getCast_id() {
        return cast_id;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.order);
        dest.writeInt(this.cast_id);
        dest.writeString(this.character);
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.creditId);
        dest.writeString(this.profilePath);
    }

    private MovieCast(Parcel in) {
        this.order = in.readInt();
        this.cast_id = in.readInt();
        this.character = in.readString();
        this.id = in.readInt();
        this.name = in.readString();
        this.creditId = in.readString();
        this.profilePath = in.readString();
    }

    public static final Parcelable.Creator<MovieCast> CREATOR = new Parcelable.Creator<MovieCast>() {
        public MovieCast createFromParcel(Parcel source) {
            return new MovieCast(source);
        }

        public MovieCast[] newArray(int size) {
            return new MovieCast[size];
        }
    };
}
