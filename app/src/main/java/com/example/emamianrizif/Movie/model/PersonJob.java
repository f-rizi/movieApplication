package com.example.emamianrizif.Movie.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Created by fatemeh on 05/05/15, 14:01.
 */
public abstract class PersonJob extends Career {

    public static final String TITLE_KEY = "title";
    public static final String ADULT_KEY = "adult";
    public static final String POSTER_PATH_KEY = "poster_path";
    public static final String RELEASED_DATE_KEY = "release_date";
    public static final String ORIGINAL_TITLE_KEY = "original_title";

    protected boolean adult;

    protected String title;
    protected String poster_path;
    protected String release_date;
    protected String original_title;

    public PersonJob() {}

    protected PersonJob(JSONObject jsonObject) {
        try {
            this.id = jsonObject.getInt(ID_KEY);

            this.adult = jsonObject.getBoolean(ADULT_KEY);

            this.title = jsonObject.isNull(TITLE_KEY) ?
                    null : jsonObject.getString(TITLE_KEY);

            this.creditId = jsonObject.isNull(CREDIT_ID_KEY) ?
                    null : jsonObject.getString(CREDIT_ID_KEY);

            this.poster_path = jsonObject.isNull(POSTER_PATH_KEY) ?
                    null : jsonObject.getString(POSTER_PATH_KEY);

            this.release_date = jsonObject.isNull(RELEASED_DATE_KEY) ?
                    null : jsonObject.getString(RELEASED_DATE_KEY);

            this.original_title = jsonObject.isNull(ORIGINAL_TITLE_KEY) ?
                    null : jsonObject.getString(ORIGINAL_TITLE_KEY);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isAdult() {
        return adult;
    }

    public String getCredit_id() {
        return creditId;
    }

    public int getId() {
        return id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getTitle() {
        return title;
    }
}
