package com.example.emamianrizif.Movie.model;

/**
 * Created by fatemeh on 16/03/15.
 */
public class MovieCollection {

    private int id;
    private String name;
    private String posterPath;
    private String backdropPath;

    public MovieCollection() {}

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }
}
