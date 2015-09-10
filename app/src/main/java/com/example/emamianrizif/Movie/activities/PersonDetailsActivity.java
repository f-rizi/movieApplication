package com.example.emamianrizif.Movie.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.example.emamianrizif.Movie.Network.NetworkConstants;
import com.example.emamianrizif.Movie.R;
import com.example.emamianrizif.Movie.fragments.MovieCreditsListFragment;
import com.example.emamianrizif.Movie.fragments.PersonCastListFragment;
import com.example.emamianrizif.Movie.fragments.PersonCrewListFragment;
import com.example.emamianrizif.Movie.fragments.PersonFragment;
import com.example.emamianrizif.Movie.helpers.LruBitmapCache;
import com.example.emamianrizif.Movie.helpers.MoviesApplication;
import com.example.emamianrizif.Movie.model.LongMovie;
import com.example.emamianrizif.Movie.model.Person;
import com.example.emamianrizif.Movie.model.PersonCast;
import com.example.emamianrizif.Movie.model.PersonCrew;
import com.example.emamianrizif.Movie.views.ExpandableTextView;
import com.example.emamianrizif.Movie.views.MovieDetailCardLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import hugo.weaving.DebugLog;

public class PersonDetailsActivity extends AppCompatActivity
        implements PersonFragment.OnPersonFragmentInteractionListener,
        MovieCreditsListFragment.OnCreditsListFragmentInteractionListener {

    public static final String PERSON_NAME = "personName";
    public static final String PERSON_ID_EXTRA = "personID";
    public static final String PERSON_IMAGE_PATH = "personImagePath";

    private int personID;

    private String personName;
    private String posterPath;

    private Toolbar toolbar;

    private Person person;

    private List<PersonCast> moviePersonCastList;
    private List<PersonCrew> moviePersonCrewList;


    private List<PersonCast> movieMovieCasts;
    private List<PersonCrew> movieMovieCrew;

    private TextView nameTextView;
    private ImageView personImage;
    private CardView castsCardView;
    private CardView crewCardView;
    private ExpandableTextView biographyTextView;

    private int totalCastSize;
    private int totalCrewSize;

    private ImageLoader imageLoader;

    private LongMovie longMovie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_details);

        getIntentExtra();

        setToolbar();
        getViews();
        initData();

        initView();
        getPersonDetails();
    }

    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setTitle(personName);
    }

    private void getIntentExtra() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        personID = bundle.getInt(PERSON_ID_EXTRA);
        personName = bundle.getString(PERSON_NAME);
        posterPath = bundle.getString(PERSON_IMAGE_PATH);
    }

    private void initData() {
        ImageLoader.ImageCache imageCache = new LruBitmapCache();
        RequestQueue requestQueue = MoviesApplication.getInstance().getRequestQueue();
        imageLoader = new ImageLoader(requestQueue, imageCache);
    }

    private void getViews() {
        nameTextView = (TextView) findViewById(R.id.name);
        personImage = (ImageView) findViewById(R.id.person_image);
        castsCardView = (CardView) findViewById(R.id.casts_card);
        crewCardView = (CardView) findViewById(R.id.crew_card);
        biographyTextView = (ExpandableTextView) findViewById(R.id.biography);
    }

    private void initView() {
//        personImage.setImageUrl(
//                NetworkConstants.POSTER_PATH_URL + posterPath,
//                imageLoader);


//        personImage.setDefaultImageResId(R.drawable._default);
//        personImage.setErrorImageResId(R.drawable.error);

        String url = NetworkConstants.POSTER_PATH_URL + posterPath;
//
//        if(TextUtils.isEmpty(posterPath)) {
//            personImage.setImageResource(R.drawable.ic_profile_placeholder);
//            return;
//        }

        ImageLoader imageLoader = MoviesApplication.getInstance().getImageLoader();

        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
//                progressBar.setVisibility(View.GONE);
                personImage.setImageBitmap(imageContainer.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                progressBar.setVisibility(View.GONE);
                personImage.setImageResource(R.drawable.ic_profile_placeholder);
            }
        });


//        ImageRequest request = new ImageRequest(url,
//                new Response.Listener<Bitmap>() {
//                    @Override
//                    public void onResponse(Bitmap bitmap) {
//                        personImage.setImageBitmap(bitmap);
//                    }
//                }, 0, 0, null,
//                new Response.ErrorListener() {
//                    public void onErrorResponse(VolleyError error) {
//                        personImage.setImageResource(R.drawable.ic_profile_placeholder);
//                    }
//                });
//
//        MoviesApplication.getInstance().addToRequestQueue(request);

        castsCardView.setVisibility(View.GONE);
        crewCardView.setVisibility(View.GONE);
    }

    private void getPersonDetails() {
        String url = String.format(NetworkConstants.PERSON_DETAILS_URL,
                personID);

        JsonObjectRequest jsonObjectRequest = new
                JsonObjectRequest(Request.Method.GET, url, null,
                successListenerImages(), errorListener());

        MoviesApplication.getInstance().addToRequestQueue(
                jsonObjectRequest, "getMovieImages");
    }

    private Response.Listener<JSONObject> successListenerImages() {
        return new Response.Listener<JSONObject>() {
            @DebugLog
            @Override
            public void onResponse(JSONObject response) {

                try {
                    person = new Person(response);
                    showPersonDetailsOnUI();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void showPersonDetailsOnUI() {
        setNameTextVIewText();

        setBiographyTextViewText();

        getMovieStaff();
    }

    private void setNameTextVIewText() {
        String name = "";

        if(!TextUtils.isEmpty(person.getName())) {
            name += (person.getName() + '\n');
        }

        if(!TextUtils.isEmpty(person.getBirthday())) {
            name += (person.getBirthday() + '\n');
        }

        if(!TextUtils.isEmpty(person.getPlace_of_birth())) {
            name += (person.getPlace_of_birth() + '\n');
        }

        if(!TextUtils.isEmpty(person.getDeathday())) {
            name += (person.getDeathday() + '\n');
        }

        nameTextView.setText(name);
    }

    private void setBiographyTextViewText() {
        if(!TextUtils.isEmpty(person.getBiography())) {
            biographyTextView.setText(person.getBiography());

        } else {
            biographyTextView.setText("No Biography");
        }
    }

    public void getMovieStaff() {
        String url = String.format(NetworkConstants.PERSON_CREDIT_URL, personID);

        JsonObjectRequest jsonObjectRequest = new
                JsonObjectRequest(Request.Method.GET, url, null,
                successListenerStaff(), errorListener());

        MoviesApplication.getInstance().addToRequestQueue(
                jsonObjectRequest, "getMovieStaff");
    }

    private Response.Listener<JSONObject> successListenerStaff() {
        return new Response.Listener<JSONObject>() {
            @DebugLog
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray casts = response.getJSONArray("cast");
                    setMovieCasts(casts);
                    setSeeMoreCast();
                    setCastsFragment();

                    JSONArray crews = response.getJSONArray("crew");
                    setMovieCrew(crews);
                    setSeeMoreCrew();
                    setCrewFragment();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void setMovieCasts(JSONArray casts) throws JSONException {
        totalCastSize = casts.length();

        if(totalCastSize != 0) {
            findViewById(R.id.casts_card).setVisibility(View.VISIBLE);

            movieMovieCasts = new ArrayList<>();

            for (int i = 0; i < totalCastSize; i++) {
                PersonCast movieMovieCast = new PersonCast(casts.getJSONObject(i));
                movieMovieCasts.add(movieMovieCast);
            }
        }
    }

    private void setSeeMoreCast() {
        MovieDetailCardLayout castHeader = (MovieDetailCardLayout)
                 findViewById(R.id.casts_header);

        if (totalCastSize >= 3) {
            castHeader.setSeeMoreVisibility(true);

            castHeader.setSeeMoreOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSeeMoreClick(MovieDetailsActivity.CAST_TYPE);
                }
            });
        }
    }

    private void setCastsFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        List<PersonCast> movieCasts = new ArrayList<>();

        int min = Math.min(totalCastSize, 3);
        for(int i = 0; i< min; i++) {
            movieCasts.add(movieMovieCasts.get(i));
        }

        PersonCastListFragment fragment = PersonCastListFragment.newInstance(movieCasts);
        fragmentTransaction.replace(R.id.cast_fragment_place_holder, fragment);
        fragmentTransaction.commit();
    }

    private void setMovieCrew(JSONArray crews) throws JSONException {
        totalCrewSize = crews.length();

        if(totalCrewSize != 0) {
             findViewById(R.id.crew_card).setVisibility(View.VISIBLE);
            movieMovieCrew = new ArrayList<>();

            for (int i = 0; i < totalCrewSize; i++) {
                PersonCrew movieCrew = new PersonCrew(crews.getJSONObject(i));
                movieMovieCrew.add(movieCrew);
            }
        }
    }

    private void setSeeMoreCrew() {
        MovieDetailCardLayout castHeader = (MovieDetailCardLayout)
                 findViewById(R.id.crew_header);

        if (totalCrewSize >= 3) {
            castHeader.setSeeMoreVisibility(true);

            castHeader.setSeeMoreOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSeeMoreClick(MovieDetailsActivity.CREW_TYPE);
                }
            });
        }
    }

    private void setCrewFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        List<PersonCrew> movieCrews = new ArrayList<>();

        int min = Math.min(totalCrewSize, 3);
        for(int i = 0; i < min; i++) {
            movieCrews.add(movieMovieCrew.get(i));
        }

        PersonCrewListFragment fragment = PersonCrewListFragment.newInstance(movieCrews);
        fragmentTransaction.replace(R.id.crew_fragment_place_holder, fragment);
        fragmentTransaction.commit();
    }


    private void onSeeMoreClick(int itemsType) {
        int x = itemsType;
//        Intent intent = new Intent(this, MoviePeopleListActivity.class);
//
//        Bundle bundle = new Bundle();
//
//        bundle.putInt(MoviePeopleListActivity.MOVIE_PEOPLE_TYPE, itemsType);
//
//
//        if (itemsType == MovieDetailsActivity.CREW_TYPE) {
//            bundle.putString(MoviePeopleListActivity.MOVIE_NAME
//                    ,mLongMovie.getOriginalTitle());
//
//            bundle.putParcelableArrayList(
//                    MoviePeopleListActivity.MOVIE_PEOPLE_LIST,
//                    (ArrayList<? extends Parcelable>) movieMovieCrew);
//
//        } else if (itemsType == MovieDetailsActivity.CAST_TYPE) {
//            bundle.putString(MoviePeopleListActivity.MOVIE_NAME
//                    ,mLongMovie.getOriginalTitle());
//
//            bundle.putParcelableArrayList(
//                    MoviePeopleListActivity.MOVIE_PEOPLE_LIST,
//                    (ArrayList<? extends Parcelable>) movieMovieCasts);
//        }
//
//        intent.putExtras(bundle);
//        startActivity(intent);
    }

    private Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO handel Error
            }
        };
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    @Override
    public void onCreditClick(int movieID, String name, String imagePath) {
    Log.d("TAG", movieID + " " + name + " " + imagePath);

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
}
