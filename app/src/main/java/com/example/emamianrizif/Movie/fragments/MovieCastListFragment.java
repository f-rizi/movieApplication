package com.example.emamianrizif.Movie.fragments;

import android.os.Bundle;

import com.example.emamianrizif.Movie.adapters.MovieCastAdapter;
import com.example.emamianrizif.Movie.adapters.MovieCreditsAdapter;
import com.example.emamianrizif.Movie.model.Career;
import com.example.emamianrizif.Movie.model.MovieCast;

import java.util.ArrayList;
import java.util.List;

public class MovieCastListFragment extends MovieCreditsListFragment {

    public MovieCreditsAdapter getAdapter() {
        MovieCreditsAdapter adapter = new MovieCastAdapter(movieCastList);
        return adapter;
    }

    public static MovieCastListFragment newInstance(List<? extends Career> movieCastList) {
        MovieCastListFragment fragment = new MovieCastListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(CREDITS_LIST_KEY, (ArrayList<MovieCast>) movieCastList);
        fragment.setArguments(args);
        return fragment;
    }
}
