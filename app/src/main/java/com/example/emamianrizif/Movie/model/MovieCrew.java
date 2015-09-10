package com.example.emamianrizif.Movie.model;

import android.os.Parcel;

import org.json.JSONObject;

/**
 * Created by fatemeh on 24/03/15.
 */

public class MovieCrew extends MovieCredits implements android.os.Parcelable {

    public static final String JOB_KEY = "job";
    public static final String DEPARTMENT_KEY = "department";

    private String job;
    private String department;

    public MovieCrew(JSONObject jsonObject) {
        super(jsonObject);

        try {
            this.job = jsonObject.isNull(JOB_KEY) ?
                    null :jsonObject.getString(JOB_KEY);

            this.department = jsonObject.isNull(DEPARTMENT_KEY) ?
                    null :jsonObject.getString(DEPARTMENT_KEY);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getJob() {
        return job;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.job);
        dest.writeString(this.department);
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.creditId);
        dest.writeString(this.profilePath);
    }

    private MovieCrew(Parcel in) {
        this.job = in.readString();
        this.department = in.readString();
        this.id = in.readInt();
        this.name = in.readString();
        this.creditId = in.readString();
        this.profilePath = in.readString();
    }

    public static final Creator<MovieCrew> CREATOR = new Creator<MovieCrew>() {
        public MovieCrew createFromParcel(Parcel source) {
            return new MovieCrew(source);
        }

        public MovieCrew[] newArray(int size) {
            return new MovieCrew[size];
        }
    };
}

