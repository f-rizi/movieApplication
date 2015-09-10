package com.example.emamianrizif.Movie.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Created by fatemeh on 16/03/15.
 */
public class SpokenLanguage implements Parcelable {

    public static final String LANGUAGE_ISO_KEY = "iso_639_1";
    public static final String LANGUAGE_NAME_KEY = "name";

    private String iso_639_1;
    private String name;

    public SpokenLanguage(JSONObject jsonObject) {

        try {
            this.iso_639_1 = jsonObject.isNull(LANGUAGE_ISO_KEY) ?
                    null : jsonObject.getString(LANGUAGE_ISO_KEY);

            this.name = jsonObject.isNull(LANGUAGE_NAME_KEY) ?
                    null : jsonObject.getString(LANGUAGE_NAME_KEY);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getISOId() {
        return iso_639_1;
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
        dest.writeString(this.iso_639_1);
        dest.writeString(this.name);
    }

    private SpokenLanguage(Parcel in) {
        this.iso_639_1 = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<SpokenLanguage> CREATOR = new Parcelable.Creator<SpokenLanguage>() {
        public SpokenLanguage createFromParcel(Parcel source) {
            return new SpokenLanguage(source);
        }

        public SpokenLanguage[] newArray(int size) {
            return new SpokenLanguage[size];
        }
    };
}
