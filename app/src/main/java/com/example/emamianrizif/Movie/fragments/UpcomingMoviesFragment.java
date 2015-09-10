package com.example.emamianrizif.Movie.fragments;

import com.example.emamianrizif.Movie.Network.NetworkConstants;

public class UpcomingMoviesFragment extends MovieListFragment {

    public static final String TAG =
            UpcomingMoviesFragment.class.getSimpleName();
    
    public static final String REQUEST_TAG =
            "upcomingMoviesRequest";

    @Override
    protected String getUrl() {
        return NetworkConstants.UPCOMING_MOVIE_URL;
    }

    public String getTAG() {
        return TAG;
    }

    public String getRequestTag() {
        return REQUEST_TAG;
    }
}
