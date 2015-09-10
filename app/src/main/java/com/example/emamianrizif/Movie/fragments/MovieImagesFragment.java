package com.example.emamianrizif.Movie.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.emamianrizif.Movie.Network.NetworkConstants;
import com.example.emamianrizif.Movie.R;
import com.example.emamianrizif.Movie.adapters.MovieImageAdapter;
import com.example.emamianrizif.Movie.helpers.MoviesApplication;
import com.example.emamianrizif.Movie.model.MovieImageModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import hugo.weaving.DebugLog;

public class MovieImagesFragment extends Fragment {

    private static final String TAG = MovieImagesFragment.class.getSimpleName();

    private static final String MOVIE_ID = "movieId";
    private static final String MOVIE_IMAGE_MODEL_lLIST = "movieImageModelList";

    private double movieId;

    private RecyclerView imagesRecyclerView;

    private OnImageInteractionListener mListener;

    private List<MovieImageModel> movieImageModels;

    public static MovieImagesFragment newInstance(double movieID) {
        MovieImagesFragment fragment = new MovieImagesFragment();
        Bundle args = new Bundle();
        args.putDouble(MOVIE_ID, movieID);
        fragment.setArguments(args);
        return fragment;
    }

    public static MovieImagesFragment newInstance(List<MovieImageModel> movieImageModels) {
        MovieImagesFragment fragment = new MovieImagesFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(MOVIE_IMAGE_MODEL_lLIST, (ArrayList) movieImageModels);
        fragment.setArguments(args);
        return fragment;
    }

    public MovieImagesFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            movieId = getArguments().getDouble(MOVIE_ID);
            movieImageModels = getArguments().getParcelableArrayList(MOVIE_IMAGE_MODEL_lLIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_details_images, container, false);

        imagesRecyclerView = (RecyclerView) view.findViewById(R.id.images_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        imagesRecyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addImagesToUI();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnImageInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnImageInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private  void addImagesToUI() {
        MovieImageAdapter adapter = new MovieImageAdapter(getActivity(), movieImageModels);
        adapter.setListener(mListener);

        imagesRecyclerView.setAdapter(adapter);
    }

    public interface OnImageInteractionListener {
        void onImageCLick(int position);
    }
}
