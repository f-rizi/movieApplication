package com.example.emamianrizif.Movie.fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.example.emamianrizif.Movie.Network.NetworkConstants;
import com.example.emamianrizif.Movie.R;
import com.example.emamianrizif.Movie.activities.MovieDetailsActivity;
import com.example.emamianrizif.Movie.activities.MoviePeopleListActivity;
import com.example.emamianrizif.Movie.helpers.LruBitmapCache;
import com.example.emamianrizif.Movie.helpers.MoviesApplication;
import com.example.emamianrizif.Movie.model.LongMovie;
import com.example.emamianrizif.Movie.model.MovieCast;
import com.example.emamianrizif.Movie.model.MovieCrew;
import com.example.emamianrizif.Movie.model.PersonCast;
import com.example.emamianrizif.Movie.model.PersonCrew;
import com.example.emamianrizif.Movie.model.Person;
import com.example.emamianrizif.Movie.views.MovieDetailCardLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import hugo.weaving.DebugLog;

public class PersonFragment extends Fragment {

    private static final String PERSON_ID = "personID";
    private static final String PERSON_POSTER_PATH = "posterPath";

    private int personID;

    private String posterPath;

    private Person person;

    private LongMovie mLongMovie;

    private List<PersonCast> moviePersonCastList;
    private List<PersonCrew> moviePersonCrewList;


    private List<PersonCast> movieMovieCasts;
    private List<PersonCrew> movieMovieCrew;

    TextView nameTextView;
    NetworkImageView personImage;


    private int totalCastSize;
    private int totalCrewSize;

    private ImageLoader imageLoader;

    private OnPersonFragmentInteractionListener mListener;

    public static PersonFragment newInstance(int personID, String posterPath) {
        Bundle args = new Bundle();
        args.putInt(PERSON_ID, personID);
        args.putString(PERSON_POSTER_PATH, posterPath);

        PersonFragment fragment = new PersonFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public PersonFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle argument = getArguments();

        if (argument != null) {
            personID = argument.getInt(PERSON_ID);
            posterPath = argument.getString(PERSON_POSTER_PATH);
        }

        ImageLoader.ImageCache imageCache = new LruBitmapCache();
        RequestQueue requestQueue = MoviesApplication.getInstance().getRequestQueue();
        imageLoader = new ImageLoader(requestQueue, imageCache);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_person_fragemnt, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nameTextView = (TextView) view.findViewById(R.id.name);
        personImage = (NetworkImageView) view.findViewById(R.id.person_image);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        getPersonDetails();
    }

    private void initView() {
        personImage.setImageUrl(
                NetworkConstants.POSTER_PATH_URL + posterPath,
                imageLoader);
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
        nameTextView.setText(person.getName());
        getMovieStaff();
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

        if(totalCastSize == 0) {
            getView().findViewById(R.id.casts_card).setVisibility(View.GONE);
            return;
        }

        int castsSize = Math.min(totalCastSize, 3);

        movieMovieCasts = new ArrayList<>();

        for (int i = 0; i < totalCastSize; i++) {
            PersonCast movieMovieCast = new PersonCast(casts.getJSONObject(i));
            movieMovieCasts.add(movieMovieCast);
        }
    }

    private void setSeeMoreCast() {
        MovieDetailCardLayout castHeader = (MovieDetailCardLayout)
                getView().findViewById(R.id.casts_header);

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
        fragmentTransaction.add(R.id.cast_fragment_place_holder, fragment);
        fragmentTransaction.commit();
    }

    private void setMovieCrew(JSONArray crews) throws JSONException {
        totalCrewSize = crews.length();

        if(totalCrewSize == 0) {
            getView().findViewById(R.id.crew_card).setVisibility(View.GONE);
            return;
        }

        movieMovieCrew = new ArrayList<>();

        for (int i = 0; i < totalCrewSize; i++) {
            PersonCrew movieCrew = new PersonCrew(crews.getJSONObject(i));
            movieMovieCrew.add(movieCrew);
        }
    }

    private void setSeeMoreCrew() {
        MovieDetailCardLayout castHeader = (MovieDetailCardLayout)
                getView().findViewById(R.id.crew_header);

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
        fragmentTransaction.add(R.id.crew_fragment_place_holder, fragment);
        fragmentTransaction.commit();
    }


    private void onSeeMoreClick(int itemsType) {
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

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnPersonFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnPersonFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnPersonFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }
}
