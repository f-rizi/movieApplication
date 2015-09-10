package com.example.emamianrizif.Movie.fragments;

import android.os.Bundle;

import com.example.emamianrizif.Movie.adapters.MovieCastAdapter;
import com.example.emamianrizif.Movie.adapters.MovieCreditsAdapter;
import com.example.emamianrizif.Movie.adapters.PersonCastAdapter;
import com.example.emamianrizif.Movie.model.Career;
import com.example.emamianrizif.Movie.model.MovieCast;

import java.util.ArrayList;
import java.util.List;

public class PersonCastListFragment extends MovieCreditsListFragment {

    public MovieCreditsAdapter getAdapter() {
        MovieCreditsAdapter adapter = new PersonCastAdapter(movieCastList);
        return adapter;
    }

    public static PersonCastListFragment newInstance(List<? extends Career> movieCastList) {
        PersonCastListFragment fragment = new PersonCastListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(CREDITS_LIST_KEY, (ArrayList<MovieCast>) movieCastList);
        fragment.setArguments(args);
        return fragment;
    }
}
