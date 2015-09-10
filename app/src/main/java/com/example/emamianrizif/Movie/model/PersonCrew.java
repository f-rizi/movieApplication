package com.example.emamianrizif.Movie.model;

import android.os.Parcel;

import org.json.JSONObject;

/**
 * Created by fatemeh on 05/05/15, 14:38.
 */
public class PersonCrew extends PersonJob {

    public static final String DEPARTMENT_KEY = "department";
    public static final String JOB_KEY = "job";

    private String department;
    private String job;

    public PersonCrew() {}

    public PersonCrew(JSONObject jsonObject) {
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

    public String getDepartment() {
        return department;
    }

    public String getJob() {
        return job;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.department);
        dest.writeString(this.job);
        dest.writeInt(this.id);
        dest.writeByte(adult ? (byte) 1 : (byte) 0);
        dest.writeString(this.title);
        dest.writeString(this.creditId);
        dest.writeString(this.poster_path);
        dest.writeString(this.release_date);
        dest.writeString(this.original_title);
    }

    private PersonCrew(Parcel in) {
        this.department = in.readString();
        this.job = in.readString();
        this.id = in.readInt();
        this.adult = in.readByte() != 0;
        this.title = in.readString();
        this.creditId = in.readString();
        this.poster_path = in.readString();
        this.release_date = in.readString();
        this.original_title = in.readString();
    }

    public static final Creator<PersonCrew> CREATOR = new Creator<PersonCrew>() {
        public PersonCrew createFromParcel(Parcel source) {
            return new PersonCrew(source);
        }

        public PersonCrew[] newArray(int size) {
            return new PersonCrew[size];
        }
    };
}
