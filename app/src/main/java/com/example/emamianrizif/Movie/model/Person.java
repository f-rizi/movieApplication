package com.example.emamianrizif.Movie.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Created by fatemeh on 05/05/15, 09:17.
 */
public class Person implements Parcelable {

    private static final String ID_KEY = "id";
    private static final String NAME_KEY = "name";
    private static final String ADULT_KEY = "adult";
    private static final String IMBD_KEY = "imdb_id";
    private static final String DEATH_DAY = "deathday";
    private static final String BIRTHDAY_KEY = "birthday";
    private static final String HOME_PAGE_KEY = "homepage";
    private static final String BIOGRAPHY_KEY = "biography";
    private static final String KNOWN_KEY = "also_known_as";
    private static final String PROFILE_KEY = "profile_path";
    private static final String POPULARITY_KEY = "popularity";
    private static final String BIRTH_PALCE = "place_of_birth";

    private int id;

    private double popularity;

    private boolean adult;

    private String name;
    private String imdb_id;
    private String birthday;
    private String deathday;
    private String homepage;
    private String biography;
    private String profile_path;
    private String place_of_birth;

    private String[] also_known_as;

    public Person() {
    }

    public Person(JSONObject jsonObject) {
        try {
            id = jsonObject.getInt(ID_KEY);

            popularity = jsonObject.getDouble(POPULARITY_KEY);

            adult = jsonObject.getBoolean(ADULT_KEY);

            name = jsonObject.isNull(NAME_KEY) ?
                    null : jsonObject.getString(NAME_KEY);

            imdb_id = jsonObject.isNull(IMBD_KEY) ?
                    null : jsonObject.getString(IMBD_KEY);

            birthday = jsonObject.isNull(BIRTHDAY_KEY) ?
                    null : jsonObject.getString(BIRTHDAY_KEY);

            deathday = jsonObject.isNull(DEATH_DAY) ?
                    null : jsonObject.getString(DEATH_DAY);

            homepage = jsonObject.isNull(HOME_PAGE_KEY) ?
                    null : jsonObject.getString(HOME_PAGE_KEY);

            biography = jsonObject.isNull(BIOGRAPHY_KEY) ?
                    null : jsonObject.getString(BIOGRAPHY_KEY);

            profile_path = jsonObject.isNull(PROFILE_KEY) ?
                    null : jsonObject.getString(PROFILE_KEY);

            place_of_birth = jsonObject.isNull(BIRTH_PALCE) ?
                    null : jsonObject.getString(BIRTH_PALCE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean isAdult() {
        return adult;
    }

    public String[] getAlso_known_as() {
        return also_known_as;
    }

    public String getBiography() {
        return biography;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getDeathday() {
        return deathday;
    }

    public String getHomepage() {
        return homepage;
    }

    public int getId() {
        return id;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public String getName() {
        return name;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getProfile_path() {
        return profile_path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeDouble(this.popularity);
        dest.writeByte(adult ? (byte) 1 : (byte) 0);
        dest.writeString(this.name);
        dest.writeString(this.imdb_id);
        dest.writeString(this.birthday);
        dest.writeString(this.deathday);
        dest.writeString(this.homepage);
        dest.writeString(this.biography);
        dest.writeString(this.profile_path);
        dest.writeString(this.place_of_birth);
        dest.writeStringArray(this.also_known_as);
    }

    private Person(Parcel in) {
        this.id = in.readInt();
        this.popularity = in.readDouble();
        this.adult = in.readByte() != 0;
        this.name = in.readString();
        this.imdb_id = in.readString();
        this.birthday = in.readString();
        this.deathday = in.readString();
        this.homepage = in.readString();
        this.biography = in.readString();
        this.profile_path = in.readString();
        this.place_of_birth = in.readString();
        this.also_known_as = in.createStringArray();
    }

    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
        public Person createFromParcel(Parcel source) {
            return new Person(source);
        }

        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}
