package com.example.emamianrizif.Movie.activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.emamianrizif.Movie.Network.NetworkConstants;
import com.example.emamianrizif.Movie.R;
import com.example.emamianrizif.Movie.fragments.InTheaterMoviesFragment;
import com.example.emamianrizif.Movie.fragments.MovieListFragment;
import com.example.emamianrizif.Movie.fragments.NavigationDrawerFragment;
import com.example.emamianrizif.Movie.fragments.PopularMoviesFragment;
import com.example.emamianrizif.Movie.fragments.UpcomingMoviesFragment;
import com.example.emamianrizif.Movie.helpers.MoviesApplication;
import com.example.emamianrizif.Movie.model.LongMovie;
import com.example.emamianrizif.Movie.views.SlidingTabLayout;

import org.json.JSONObject;

import java.util.NoSuchElementException;

import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationItemActions,
        MovieListFragment.OnMovieListActionListener {

    private Toolbar toolbar;

    private LongMovie longMovie;

    private static final String FRAGMENT_INDEX = "currentFragment";
    private static final String IS_FIRST_DISPLAY = "isFirstDisplay";

    private static final int POPULAR_MOVIES = 0;
    private static final int IN_THEATER_MOVIES = 1;
    private static final int UPCOMING_MOVIES = 2;

    public static boolean firstDisplay;

    private ViewPager viewPager;
    private int currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            firstDisplay = false;

        } else {
            firstDisplay = true;
        }

        setToolbar();
        setNavigationDrawer();
        setFragments();
    }

    private void setFragments() {
        android.app.FragmentManager fragmentManager = getFragmentManager();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new ContentFragmentAdapter(fragmentManager));

        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        slidingTabLayout.setCustomTabView(R.layout.custom_tab_layout, R.id.title, R.id.icon);
        slidingTabLayout.setViewPager(viewPager);
    }

    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
    }

    private void setNavigationDrawer() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationDrawerFragment navigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.start_drawer);

        navigationDrawerFragment.setup(R.id.start_drawer, drawerLayout, toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finishAfterTransition();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNavigationItemCLick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.nav_item_profile:
                break;
            case R.id.nav_item_edit:
                break;
            case R.id.nav_item_favorites:
                break;
            case R.id.nav_item_location:
                break;
            case R.id.nav_item_settings:
                break;
            case R.id.nav_item_help_feedback:
                break;
            case R.id.nav_item_about:
                break;
            default:
                throw new NoSuchElementException("unknown ID_KEY");
        }
    }

//    public void onMovieClick2(View view, long movieID) {

        //Start MovieDetailsActivity2
//        View textView = view.findViewById(R.id.title);
//        View posterView = view.findViewById(R.id.poster);
//
//
//        Intent intent = new Intent(this, MovieDetailsActivity2.class);
//        intent.putExtra(EXTRA_MOVIE_TITLE, ((TextView)textView).getText().toString());
////        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.cardList);
////        MovieAdapter.ItemViewHolder itemViewHolder = (MovieAdapter.ItemViewHolder)
////                recyclerView.findViewHolderForItemId(movieID);
////        String movieTitleTransitionName = getString(R.string.transition_movie_title_name);
//        String moviePosterTransitionName = getString(R.string.transition_movie_poster_name);
//
////        Pair<View, String> titlePair = Pair.create(textView, movieTitleTransitionName);
//        Pair<View, String> posterPair = Pair.create(posterView, moviePosterTransitionName);
//
//        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, /*titlePair, */ posterPair);
//
//        ActivityCompat.startActivity(this, intent, options.toBundle());
////        startActivity(intent);
////        Toast.makeText(this, ((TextView)view.findViewById(R.id.title)).getText(), Toast.LENGTH_SHORT).show();



//        ////Start MovieDetailsCollapsingActivity
//        Intent intent1 = new Intent(this, MovieDetailsCollapsingActivity.class);
//        startActivity(intent1);


        //start my scroolable image activity
//        Intent intent2 = new Intent(this, FillGapScrollViewActivity.class);
//        startActivity(intent2);


//        Intent intent = new Intent(this, FlexibleSpaceToolbarScrollViewActivity.class);
//        startActivity(intent);


//        Intent intent = new Intent(this, FlexibleSpaceWithImageScrollViewActivity.class);
//        startActivity(intent);


//        Intent intent = new Intent(this, FlexibleSpaceWithImageListViewActivity.class);
//        startActivity(intent);


//        String url = NetworkConstants.MOVIE_DETAILS_URL + NetworkConstants.API;
//        url = String.format(url, movieID);
//
//        JsonObjectRequest jsonObjectRequest = new
//                JsonObjectRequest(Request.Method.GET, url, null, successListener(), errorListener());
//
//        MoviesApplication.getInstance().addToRequestQueue(jsonObjectRequest, "jsonRequestFromUpcommingFragment");
//
//    }

    @Override
    public void onMovieClick(View view, long movieID) {
        String url = NetworkConstants.MOVIE_DETAILS_URL + NetworkConstants.API;
        url = String.format(url, movieID);

        JsonObjectRequest jsonObjectRequest = new
                JsonObjectRequest(Request.Method.GET, url, null, successListener(), errorListener());

        MoviesApplication.getInstance().addToRequestQueue(jsonObjectRequest, "onMovieClick");

    }

    private Response.Listener<JSONObject> successListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                handleResponse(response);
            }
        };
    }


    private Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void handleResponse(JSONObject response) {
        try {
            longMovie = new LongMovie(response);

        } catch (Exception e) {
            e.printStackTrace();
        }

        gotoNextActivity();
    }

    private void gotoNextActivity() {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        EventBus.getDefault().postSticky(longMovie);
        startActivity(intent);
    }

    private class ContentFragmentAdapter extends FragmentStatePagerAdapter {
        private String[] pagerTitles = getResources().getStringArray(R.array.movies);

        private static final int FRAGMENTS_ITEMS = 3;

        ContentFragmentAdapter(android.app.FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return pagerTitles[position];
        }

        @Override
        public Fragment getItem(int index) {
            Fragment fragment;

            switch (index) {
                case POPULAR_MOVIES:
                    fragment = new PopularMoviesFragment();
                    break;

                case IN_THEATER_MOVIES:
                    fragment = new InTheaterMoviesFragment();
                    break;

                case UPCOMING_MOVIES:
                    fragment = new UpcomingMoviesFragment();
                    break;

                default:
                    throw new NoSuchElementException();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return FRAGMENTS_ITEMS;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }
}
