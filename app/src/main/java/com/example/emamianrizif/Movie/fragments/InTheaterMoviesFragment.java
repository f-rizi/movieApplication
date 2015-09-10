package com.example.emamianrizif.Movie.fragments;

import com.example.emamianrizif.Movie.Network.NetworkConstants;

public class InTheaterMoviesFragment extends MovieListFragment {

    public static final String TAG =
            InTheaterMoviesFragment.class.getSimpleName();
    
    public static final String REQUEST_TAG =
            "inTheaterMoviesRequest";

    @Override
    protected String getUrl() {
        return NetworkConstants.IN_THEATER_MOVIES_URL;
    }

    public String getTAG() {
        return TAG;
    }

    public String getRequestTag() {
        return REQUEST_TAG;
    }
}
