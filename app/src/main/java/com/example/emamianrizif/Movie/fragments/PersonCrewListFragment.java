package com.example.emamianrizif.Movie.fragments;

import android.os.Bundle;

import com.example.emamianrizif.Movie.adapters.MovieCreditsAdapter;
import com.example.emamianrizif.Movie.adapters.PersonCrewAdapter;
import com.example.emamianrizif.Movie.model.Career;
import com.example.emamianrizif.Movie.model.MovieCrew;

import java.util.ArrayList;
import java.util.List;

public class PersonCrewListFragment extends MovieCreditsListFragment {

    public MovieCreditsAdapter getAdapter(){
        MovieCreditsAdapter adapter = new PersonCrewAdapter(movieCastList);
        return adapter;
    }

    public static PersonCrewListFragment newInstance(List<? extends Career> castList) {
        PersonCrewListFragment fragment = new PersonCrewListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(CREDITS_LIST_KEY, (ArrayList<MovieCrew>) castList);
        fragment.setArguments(args);
        return fragment;
    }
}
