package com.example.emamianrizif.Movie.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Created by fatemeh on 24/03/15.
 */
public abstract class MovieCredits extends Career {

    public static final String NAME_KEY = "name";
    public static final String PROFILE_PATH_KEY = "profile_path";

    protected int id;

    protected String name;
    protected String creditId;
    protected String profilePath;

    public MovieCredits() {}

    protected MovieCredits(JSONObject jsonObject) {
        try {
            this.id = jsonObject.getInt(ID_KEY);

            this.name = jsonObject.isNull(NAME_KEY) ?
                    null : jsonObject.getString(NAME_KEY);

            this.creditId = jsonObject.isNull(CREDIT_ID_KEY) ?
                    null : jsonObject.getString(CREDIT_ID_KEY);

            this.profilePath = jsonObject.isNull(PROFILE_PATH_KEY) ?
                    null : jsonObject.getString(PROFILE_PATH_KEY);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getProfilePath() {
        return profilePath;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getCreditId() {
        return creditId;
    }
}
