package com.example.emamianrizif.Movie.model;


import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Created by fatemeh on 12/05/15, 21:35.
 */
public abstract class Career implements Parcelable {

    public static final String ID_KEY = "id";
    public static final String CREDIT_ID_KEY = "credit_id";

    protected int id;
    protected String creditId;

    public Career() {}

    protected Career(JSONObject jsonObject) {
        try {
            this.id = jsonObject.getInt(ID_KEY);

            this.creditId = jsonObject.isNull(CREDIT_ID_KEY) ?
                    null : jsonObject.getString(CREDIT_ID_KEY);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public String getCreditId() {
        return creditId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.creditId);
    }

    private Career(Parcel in) {
        this.id = in.readInt();
        this.creditId = in.readString();
    }
}
