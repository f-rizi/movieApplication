package com.example.emamianrizif.Movie.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fatemeh on 16/03/15.
 */

public class LongMovie extends ShortMovie implements Parcelable {

    public static final String GENRES_KEY = "genres";
    public static final String BUDGET_KEY = "budget";
    public static final String STATUES_KEY = "status";
    public static final String TAGLINE_KEY = "tagline";
    public static final String REVENUE_KEY = "revenue";
    public static final String IMBD_ID_KEY = "imdb_id";
    public static final String RUNTIME_KEY = "runtime";
    public static final String OVERVIEW_KEY = "overview";
    public static final String HOME_PAGE_KEY = "homepage";
    public static final String SPOKEN_LANGUAGES_KEY = "spoken_languages";
    public static final String ORIGINAL_LANGUAGE_KEY = "original_language";
    public static final String PRODUCTION_COMPANIES_KEY = "production_companies";
    public static final String PRODUCTION_COUNTRIES_KEY = "production_countries";

    private String status;
    private String tagLine;
    private String imbd_id;
    private String homePage;
    private String overView;
    private String originalLanguage;

    private int budget;
    private int runTime;
    private long revenue;

    private List<Genre> genreList;
    private List<SpokenLanguage> spokenLanguageList;
    private List<ProductionCompany> productionCompanyList;
    private List<ProductionCountry> productionCountries;

    private LongMovie() {}

    public LongMovie(JSONObject movieJsonObject) {
        super(movieJsonObject);

        try {
            this.status = movieJsonObject.isNull(STATUES_KEY) ?
                    null : movieJsonObject.getString(STATUES_KEY);

            this.tagLine = movieJsonObject.isNull(TAGLINE_KEY) ?
                    null : movieJsonObject.getString(TAGLINE_KEY);

            this.imbd_id = movieJsonObject.isNull(IMBD_ID_KEY) ?
                    null : movieJsonObject.getString(IMBD_ID_KEY);

            this.homePage = movieJsonObject.isNull(HOME_PAGE_KEY) ?
                    null : movieJsonObject.getString(HOME_PAGE_KEY);

            this.overView = movieJsonObject.isNull(OVERVIEW_KEY) ?
                    null : movieJsonObject.getString(OVERVIEW_KEY);

            this.originalLanguage = movieJsonObject.isNull(ORIGINAL_LANGUAGE_KEY) ?
                    null : movieJsonObject.getString(ORIGINAL_LANGUAGE_KEY);

            this.budget = movieJsonObject.getInt(BUDGET_KEY);
            this.runTime = movieJsonObject.getInt(RUNTIME_KEY);
            this.revenue = movieJsonObject.getLong(REVENUE_KEY);

            this.genreList = getGenresFromJson(movieJsonObject.getJSONArray(GENRES_KEY));
            this.spokenLanguageList = getSpokenLanguagesFromJson(movieJsonObject.getJSONArray(SPOKEN_LANGUAGES_KEY));
            this.productionCompanyList = getProductionComapnieFromJson(movieJsonObject.getJSONArray(PRODUCTION_COMPANIES_KEY));
            this.productionCountries = getProductionCountriesFromJson(movieJsonObject.getJSONArray(PRODUCTION_COUNTRIES_KEY));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static LongMovie LongMovie(JSONObject movieJsonObject) {
        LongMovie movie = (LongMovie) new ShortMovie(movieJsonObject);

        try {
            movie.status = movieJsonObject.getString(STATUES_KEY);
            movie.tagLine = movieJsonObject.getString(TAGLINE_KEY);
            movie.imbd_id = movieJsonObject.getString(IMBD_ID_KEY);
            movie.homePage = movieJsonObject.getString(HOME_PAGE_KEY);
            movie.overView = movieJsonObject.getString(OVERVIEW_KEY);
            movie.originalLanguage = movieJsonObject.getString(ORIGINAL_LANGUAGE_KEY);

            movie.budget = movieJsonObject.getInt(BUDGET_KEY);
            movie.runTime = movieJsonObject.getInt(RUNTIME_KEY);
            movie.revenue = movieJsonObject.getLong(REVENUE_KEY);

            movie.genreList = getGenresFromJson(movieJsonObject.getJSONArray(GENRES_KEY));
            movie.spokenLanguageList = getSpokenLanguagesFromJson(movieJsonObject.getJSONArray(SPOKEN_LANGUAGES_KEY));
            movie.productionCompanyList = getProductionComapnieFromJson(movieJsonObject.getJSONArray(PRODUCTION_COMPANIES_KEY));
            movie.productionCountries = getProductionCountriesFromJson(movieJsonObject.getJSONArray(PRODUCTION_COUNTRIES_KEY));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movie;
    }

    private static List<Genre> getGenresFromJson(JSONArray jsonArray) {
        List<Genre> genres = new ArrayList<>();

        JSONObject jsonObject;
        Genre genre;

        for(int i = 0; i < jsonArray.length(); i++) {

            try {
                jsonObject = jsonArray.getJSONObject(i);

                genre = new Genre(jsonObject);
                genres.add(genre);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return genres;
    }

    private static List<ProductionCompany> getProductionComapnieFromJson(JSONArray jsonArray) {
        List<ProductionCompany> productionCompanies = new ArrayList<>();

        JSONObject jsonObject;
        ProductionCompany productionCompany;

        for(int i = 0; i < jsonArray.length(); i++) {
            try {
                jsonObject = jsonArray.getJSONObject(i);

                productionCompany = new ProductionCompany(jsonObject);
                productionCompanies.add(productionCompany);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return productionCompanies;
    }

    private static List<ProductionCountry> getProductionCountriesFromJson(JSONArray jsonArray) {
        List<ProductionCountry> productionCountries = new ArrayList<>();

        JSONObject jsonObject;
        ProductionCountry productionCountry;

        for(int i = 0; i < jsonArray.length(); i++) {
            try {
                jsonObject = jsonArray.getJSONObject(i);

                productionCountry = new ProductionCountry(jsonObject);
                productionCountries.add(productionCountry);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return productionCountries;
    }

    private static List<SpokenLanguage> getSpokenLanguagesFromJson(JSONArray jsonArray) {
        List<SpokenLanguage> spokenLanguages = new ArrayList<>();

        JSONObject jsonObject;
        SpokenLanguage spokenLanguage;

        for(int i = 0; i < jsonArray.length(); i++) {
            try {
                jsonObject = jsonArray.getJSONObject(i);

                spokenLanguage = new SpokenLanguage(jsonObject);
                spokenLanguages.add(spokenLanguage);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return spokenLanguages;
    }

    public int getBudget() {
        return budget;
    }

    public List<Genre> getGenreList() {
        return genreList;
    }

    public String getHomePage() {
        return homePage;
    }

    public String getImbd_id() {
        return imbd_id;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOverView() {
        return overView;
    }

    public List<ProductionCountry> getProductionCountries() {
        return productionCountries;
    }

    public List<ProductionCompany> getProductionCompanyList() {
        return productionCompanyList;
    }

    public long getRevenue() {
        return revenue;
    }

    public int getRunTime() {
        return runTime;
    }

    public List<SpokenLanguage> getSpokenLanguageList() {
        return spokenLanguageList;
    }

    public String getStatus() {
        return status;
    }

    public String getTagLine() {
        return tagLine;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
        dest.writeString(this.tagLine);
        dest.writeString(this.imbd_id);
        dest.writeString(this.homePage);
        dest.writeString(this.overView);
        dest.writeString(this.originalLanguage);
        dest.writeInt(this.budget);
        dest.writeInt(this.runTime);
        dest.writeLong(this.revenue);
        dest.writeTypedList(genreList);
        dest.writeTypedList(spokenLanguageList);
        dest.writeTypedList(productionCompanyList);
        dest.writeTypedList(productionCountries);
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

    private LongMovie(Parcel in) {
        this.status = in.readString();
        this.tagLine = in.readString();
        this.imbd_id = in.readString();
        this.homePage = in.readString();
        this.overView = in.readString();
        this.originalLanguage = in.readString();
        this.budget = in.readInt();
        this.runTime = in.readInt();
        this.revenue = in.readLong();
        in.readTypedList(genreList, Genre.CREATOR);
        in.readTypedList(spokenLanguageList, SpokenLanguage.CREATOR);
        in.readTypedList(productionCompanyList, ProductionCompany.CREATOR);
        in.readTypedList(productionCountries, ProductionCountry.CREATOR);
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

    public static final Creator<LongMovie> CREATOR = new Creator<LongMovie>() {
        public LongMovie createFromParcel(Parcel source) {
            return new LongMovie(source);
        }

        public LongMovie[] newArray(int size) {
            return new LongMovie[size];
        }
    };
}
