package com.example.emamianrizif.Movie.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Created by fatemeh on 27/03/15.
 */
public class MovieImageModel implements Parcelable {

    public static final String WIDTH_KEY = "width";
    public static final String HEIGHT_KEY = "height";
    public static final String ISO_639_KEY = "iso_639_1";
    public static final String FILE_PATH_KEY = "file_path";
    public static final String VOTE_COUNT_KEY = "vote_count";
    public static final String ASPECT_RATO_KEY = "aspect_ratio";
    public static final String VOTE_AVERAGE_KEY = "vote_average";

    private String file_path;
    private String iso_639_1;

    private int width;
    private int height;
    private int voteCount;

    private double voteAverage;
    private double aspectRatio;

    public MovieImageModel(JSONObject movieImageObject) {
        try {
            file_path = movieImageObject.isNull(FILE_PATH_KEY) ?
                    null : movieImageObject.getString(FILE_PATH_KEY);

            iso_639_1 = movieImageObject.isNull(ISO_639_KEY) ?
                    null : movieImageObject.getString(ISO_639_KEY);

            width = movieImageObject.getInt(WIDTH_KEY);
            height = movieImageObject.getInt(HEIGHT_KEY);
            voteCount = movieImageObject.getInt(VOTE_COUNT_KEY);

            voteAverage = movieImageObject.getDouble(VOTE_AVERAGE_KEY);
            aspectRatio = movieImageObject.getDouble(ASPECT_RATO_KEY);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double getAspectRatio() {
        return aspectRatio;
    }

    public String getFile_path() {
        return file_path;
    }

    public int getHeight() {
        return height;
    }

    public String getIso_693_1() {
        return iso_639_1;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.file_path);
        dest.writeString(this.iso_639_1);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeInt(this.voteCount);
        dest.writeDouble(this.voteAverage);
        dest.writeDouble(this.aspectRatio);
    }

    private MovieImageModel(Parcel in) {
        this.file_path = in.readString();
        this.iso_639_1 = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
        this.voteCount = in.readInt();
        this.voteAverage = in.readDouble();
        this.aspectRatio = in.readDouble();
    }

    public static final Parcelable.Creator<MovieImageModel> CREATOR = new Parcelable.Creator<MovieImageModel>() {
        public MovieImageModel createFromParcel(Parcel source) {
            return new MovieImageModel(source);
        }

        public MovieImageModel[] newArray(int size) {
            return new MovieImageModel[size];
        }
    };
}
