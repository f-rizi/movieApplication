package com.example.emamianrizif.Movie.fragments;


import com.example.emamianrizif.Movie.Network.NetworkConstants;

public class PopularMoviesFragment extends MovieListFragment {

    public static final String TAG =
            PopularMoviesFragment.class.getSimpleName();

    public static final String REQUEST_TAG =
            "popularMoviesRequest";

    @Override
    protected String getUrl() {
        return NetworkConstants.POPULAR_MOVIES_URL;
    }

    public String getTAG() {
        return TAG;
    }

    public String getRequestTag() {
        return REQUEST_TAG;
    }
}
