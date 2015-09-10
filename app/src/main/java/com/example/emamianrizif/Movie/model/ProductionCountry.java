package com.example.emamianrizif.Movie.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Created by fatemeh on 16/03/15.
 */
public class ProductionCountry implements Parcelable {

    public static final String PRODUACTION_COUNTRY_ID_KEY = "iso_3166_1";
    public static final String PRODUACTION_COUNTRY_NAME_KEY = "name";

    private String iso_3166_1;
    private String name;

    public ProductionCountry(JSONObject jsonObject) {

        try {
            this.iso_3166_1 = jsonObject.isNull(PRODUACTION_COUNTRY_ID_KEY) ?
                    null : jsonObject.getString(PRODUACTION_COUNTRY_ID_KEY);

            this.name = jsonObject.isNull(PRODUACTION_COUNTRY_NAME_KEY) ?
                    null : jsonObject.getString(PRODUACTION_COUNTRY_NAME_KEY);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getISOId() {
        return iso_3166_1;
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
        dest.writeString(this.iso_3166_1);
        dest.writeString(this.name);
    }

    private ProductionCountry(Parcel in) {
        this.iso_3166_1 = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<ProductionCountry> CREATOR = new Parcelable.Creator<ProductionCountry>() {
        public ProductionCountry createFromParcel(Parcel source) {
            return new ProductionCountry(source);
        }

        public ProductionCountry[] newArray(int size) {
            return new ProductionCountry[size];
        }
    };
}
