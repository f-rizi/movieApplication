package com.example.emamianrizif.Movie.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.emamianrizif.Movie.R;
import com.example.emamianrizif.Movie.adapters.MovieAdapter;
import com.example.emamianrizif.Movie.helpers.MoviesApplication;
import com.example.emamianrizif.Movie.model.ShortMovie;
import com.squareup.leakcanary.RefWatcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public abstract class MovieListFragment
        extends Fragment
        implements MovieAdapter.MovieActionListener {

    private static final String MOVIES_KEY = "movies";

    private int page;

    private String url;

    private List<ShortMovie> movies = new ArrayList<>();
    private RecyclerView recyclerView;

    private OnMovieListActionListener listener;

    public MovieListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        url = getUrl();
    }

    protected abstract String getUrl();

    public abstract String getTAG();

    public abstract String getRequestTag();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        int columns = getColumns();

        GridLayoutManager layoutManager = new
                GridLayoutManager(getActivity(), columns);

        layoutManager.setOrientation(
                LinearLayoutManager.VERTICAL);


        recyclerView = (RecyclerView) view.findViewById(R.id.cardList);
        recyclerView.setLayoutManager(layoutManager);

        if (savedInstanceState != null) {
            movies = savedInstanceState.getParcelableArrayList(MOVIES_KEY);

        } else {
            sendRequest();
        }

        showMoviesInUI();

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (OnMovieListActionListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(
                    activity.toString() +
                            " must implement " +
                            OnMovieListActionListener.class.getSimpleName());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MoviesApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    private void sendRequest() {
        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(
                        Request.Method.GET,
                        url,
                        null,
                        successListener(),
                        errorListener());

        MoviesApplication.
                getInstance().
                addToRequestQueue(jsonObjectRequest, getRequestTag());
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
                //TODO handel Error
            }
        };
    }

    private void handleResponse(JSONObject response) {
        try {
            page = response.getInt("page");
            JSONArray moviesJsonArray = response.getJSONArray("results");

            movies = new ArrayList<>();
            ShortMovie movie;

            for (int i = 0; i < moviesJsonArray.length(); i++) {
                movie = new ShortMovie(moviesJsonArray.getJSONObject(i));
                movies.add(movie);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        showMoviesInUI();
    }

    private void showMoviesInUI() {
        MovieAdapter itemAdapter = new MovieAdapter(getActivity(), movies, this);
        recyclerView.setAdapter(itemAdapter);
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putParcelableArrayList(MOVIES_KEY,
                (ArrayList<? extends android.os.Parcelable>) movies);
    }

    @Override
    public void onMovieClick(View view, long movieID) {
        listener.onMovieClick(view, movieID);
    }

    private int getColumns() {
        int columns;

        Display display = getActivity().
                getWindowManager().
                getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        int width = size.x;

        columns = width / 220;

        return columns;
    }

    public interface OnMovieListActionListener {
        void onMovieClick(View view, long movieID);
    }
}
