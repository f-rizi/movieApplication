package com.example.emamianrizif.Movie.model;

import android.os.Parcel;

import org.json.JSONObject;

/**
 * Created by fatemeh on 05/05/15, 14:38.
 */
public class PersonCast extends PersonJob {
    public static final String CHARACTER_KEY = "character";

    private String  character;

    public PersonCast() {}

    public PersonCast(JSONObject jsonObject) {
        super(jsonObject);

        try {
            this.character = jsonObject.isNull(CHARACTER_KEY) ?
                    null : jsonObject.getString(CHARACTER_KEY);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCharacter() {
        return character;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.character);
        dest.writeInt(this.id);
        dest.writeByte(adult ? (byte) 1 : (byte) 0);
        dest.writeString(this.title);
        dest.writeString(this.creditId);
        dest.writeString(this.poster_path);
        dest.writeString(this.release_date);
        dest.writeString(this.original_title);
    }

    private PersonCast(Parcel in) {
        this.character = in.readString();
        this.id = in.readInt();
        this.adult = in.readByte() != 0;
        this.title = in.readString();
        this.creditId = in.readString();
        this.poster_path = in.readString();
        this.release_date = in.readString();
        this.original_title = in.readString();
    }

    public static final Creator<PersonCast> CREATOR = new Creator<PersonCast>() {
        public PersonCast createFromParcel(Parcel source) {
            return new PersonCast(source);
        }

        public PersonCast[] newArray(int size) {
            return new PersonCast[size];
        }
    };
}
