package com.example.emamianrizif.Movie.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by fatemeh on 13/05/15, 12:18.
 */
public class ShortMovie implements Parcelable {

    public static final String ID_KEY = "id";
    public static final String TITLE_KEY = "title";
    public static final String VOTE_COUNT_KEY = "vote_count";
    public static final String POPULARITY_KEY = "popularity";
    public static final String POSTER_PATH_KEY = "poster_path";
    public static final String RELEASE_DATE_KEY = "release_date";
    public static final String VOTE_AVERAGE_KEY = "vote_average";
    public static final String BACKDROP_PATH_KEY = "backdrop_path";
    public static final String ORIGINAL_TITLE_KEY = "original_title";

    protected long id;

    protected int voteCount;

    protected double popularity;

    protected float voteAverage;

    protected String title;
    protected String posterPath;
    protected String releaseDate;
    protected String backdropPath;
    protected String originalTitle;


    public ShortMovie() {
    }

    public ShortMovie(JSONObject movieJsonObject) {
        try {
            this.id = movieJsonObject.getInt(ID_KEY);
            this.voteCount = movieJsonObject.getInt(VOTE_COUNT_KEY);

            this.popularity = movieJsonObject.getDouble(POPULARITY_KEY);
            this.voteAverage = (float) movieJsonObject.getDouble(VOTE_AVERAGE_KEY);

            this.title = movieJsonObject.isNull(TITLE_KEY) ?
                    null :movieJsonObject.getString(TITLE_KEY);

            this.posterPath = movieJsonObject.isNull(POSTER_PATH_KEY) ?
                    null : movieJsonObject.getString(POSTER_PATH_KEY);

            this.releaseDate = movieJsonObject.isNull(RELEASE_DATE_KEY) ?
                    null : movieJsonObject.getString(RELEASE_DATE_KEY);

            this.backdropPath = movieJsonObject.isNull(BACKDROP_PATH_KEY) ?
                    null : movieJsonObject.getString(BACKDROP_PATH_KEY);

            this.originalTitle = movieJsonObject.isNull(ORIGINAL_TITLE_KEY) ?
                    null : movieJsonObject.getString(ORIGINAL_TITLE_KEY);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static ShortMovie ShortMovie(JSONObject movieJsonObject) {
        ShortMovie movie = new ShortMovie();

        try {
            movie.id = movieJsonObject.getInt(ID_KEY);
            movie.title = movieJsonObject.getString(TITLE_KEY);
            movie.voteCount = movieJsonObject.getInt(VOTE_COUNT_KEY);
            movie.popularity = movieJsonObject.getDouble(POPULARITY_KEY);
            movie.posterPath = movieJsonObject.getString(POSTER_PATH_KEY);
            movie.releaseDate = movieJsonObject.getString(RELEASE_DATE_KEY);
            movie.backdropPath = movieJsonObject.getString(BACKDROP_PATH_KEY);
            movie.originalTitle = movieJsonObject.getString(ORIGINAL_TITLE_KEY);
            movie.voteAverage = (float) movieJsonObject.getDouble(VOTE_AVERAGE_KEY);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movie;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public long getId() {
        return id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public double getPopularity() {
        return popularity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeInt(this.voteCount);
        dest.writeDouble(this.popularity);
        dest.writeFloat(this.voteAverage);
        dest.writeString(this.title);
        dest.writeString(this.posterPath);
        dest.writeString(this.releaseDate);
        dest.writeString(this.backdropPath);
        dest.writeString(this.originalTitle);
    }

    private ShortMovie(Parcel in) {
        this.id = in.readLong();
        this.voteCount = in.readInt();
        this.popularity = in.readDouble();
        this.voteAverage = in.readFloat();
        this.title = in.readString();
        this.posterPath = in.readString();
        this.releaseDate = in.readString();
        this.backdropPath = in.readString();
        this.originalTitle = in.readString();
    }

    public static final Parcelable.Creator<ShortMovie> CREATOR = new Parcelable.Creator<ShortMovie>() {
        public ShortMovie createFromParcel(Parcel source) {
            return new ShortMovie(source);
        }

        public ShortMovie[] newArray(int size) {
            return new ShortMovie[size];
        }
    };
}
