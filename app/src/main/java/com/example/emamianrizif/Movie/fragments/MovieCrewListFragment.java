package com.example.emamianrizif.Movie.fragments;

import android.os.Bundle;

import com.example.emamianrizif.Movie.adapters.MovieCreditsAdapter;
import com.example.emamianrizif.Movie.adapters.MovieCrewAdapter;
import com.example.emamianrizif.Movie.model.Career;
import com.example.emamianrizif.Movie.model.MovieCrew;

import java.util.ArrayList;
import java.util.List;

public class MovieCrewListFragment extends MovieCreditsListFragment {

    public MovieCreditsAdapter getAdapter(){
        MovieCreditsAdapter adapter = new MovieCrewAdapter(movieCastList);
        return adapter;
    }

    public static MovieCrewListFragment newInstance(List<? extends Career> castList) {
        MovieCrewListFragment fragment = new MovieCrewListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(CREDITS_LIST_KEY, (ArrayList<MovieCrew>) castList);
        fragment.setArguments(args);
        return fragment;
    }
}
