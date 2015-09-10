package com.example.emamianrizif.Movie.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Created by fatemeh on 16/03/15.
 */
public class ProductionCompany implements Parcelable {

    public static final String COUNTRY_ID_KEY = "id";
    public static final String COUNTRY_NAME_KEY = "name";

    private int id;
    private String name;

    public ProductionCompany(JSONObject jsonObject) {

        try {
            this.id = jsonObject.getInt(COUNTRY_ID_KEY);

            this.name = jsonObject.isNull(COUNTRY_NAME_KEY) ?
                    null : jsonObject.getString(COUNTRY_NAME_KEY);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
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
        dest.writeInt(this.id);
        dest.writeString(this.name);
    }

    private ProductionCompany(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<ProductionCompany> CREATOR = new Parcelable.Creator<ProductionCompany>() {
        public ProductionCompany createFromParcel(Parcel source) {
            return new ProductionCompany(source);
        }

        public ProductionCompany[] newArray(int size) {
            return new ProductionCompany[size];
        }
    };
}
