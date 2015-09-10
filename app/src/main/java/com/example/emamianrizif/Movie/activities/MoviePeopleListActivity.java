package com.example.emamianrizif.Movie.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.emamianrizif.Movie.R;
import com.example.emamianrizif.Movie.fragments.MovieCastListFragment;
import com.example.emamianrizif.Movie.fragments.MovieCreditsListFragment;
import com.example.emamianrizif.Movie.fragments.MovieCrewListFragment;
import com.example.emamianrizif.Movie.model.MovieCast;
import com.example.emamianrizif.Movie.model.MovieCredits;

import java.util.List;

import hugo.weaving.DebugLog;

public class MoviePeopleListActivity
        extends AppCompatActivity
        implements MovieCreditsListFragment.OnCreditsListFragmentInteractionListener{

    public static final String MOVIE_PEOPLE_LIST = "moviePeopleList";
    public static final String MOVIE_PEOPLE_TYPE = "moviePeopleType";
    public static final String MOVIE_NAME = "movieName";

    private Toolbar toolbar;

    private List<? extends MovieCredits> movieCredit;
    private int peopleType;
    private String movieName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_people_list);

        getIntentData();

        setToolbar();
        setFragment();
    }

    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setTitle(movieName);

        if(peopleType == MovieDetailsActivity.CAST_TYPE) {
            getSupportActionBar().setSubtitle("Cast");

        } else if (peopleType == MovieDetailsActivity.CREW_TYPE) {
            getSupportActionBar().setSubtitle("Crew");
        }
    }

    private void getIntentData() {
        Intent intent = getIntent();

        movieName = intent.getStringExtra(MOVIE_NAME);
        peopleType = intent.getIntExtra(MOVIE_PEOPLE_TYPE, 0);
        movieCredit = intent.getParcelableArrayListExtra(MOVIE_PEOPLE_LIST);
    }

    private void setFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();

        MovieCreditsListFragment fragment = null;

        if(peopleType == MovieDetailsActivity.CAST_TYPE) {
            fragment =  MovieCastListFragment.newInstance(movieCredit);

        } else if (peopleType == MovieDetailsActivity.CREW_TYPE) {
            fragment = MovieCrewListFragment.newInstance(movieCredit);
        }

        fragmentTransaction.replace(R.id.people_fragment_place_holder, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onCreditClick(int castID, String name, String posterPath) {
        Intent intent = new Intent(this, PersonDetailsActivity.class);

        Bundle bundle = new Bundle();
        bundle.putInt(PersonDetailsActivity.PERSON_ID_EXTRA, castID);

        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
