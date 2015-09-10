package com.example.emamianrizif.Movie.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.emamianrizif.Movie.R;
import com.example.emamianrizif.Movie.adapters.ImagesAdapter;
import com.example.emamianrizif.Movie.model.MovieImageModel;

import java.util.ArrayList;
import java.util.List;

public class MovieImagesGalleryFragment extends Fragment {

    public static final String TAG = MovieImagesGalleryFragment.class.getSimpleName();

    private static final String CURRENT_IMAGE_INDEX = "currentImagePosition";
    private static final String MOVIE_IMAGE_MODELS_LIST = "movies";

    private int movieIndex;

    private List<MovieImageModel> movieImageModels;

    private ViewPager viewPager;
    private ImagesAdapter imagesAdapter;

    public static MovieImagesGalleryFragment newInstance(int movieIndex, List<MovieImageModel> movieImageModels) {
        Bundle args = new Bundle();
        args.putInt(CURRENT_IMAGE_INDEX, movieIndex);
        args.putParcelableArrayList(MOVIE_IMAGE_MODELS_LIST, (ArrayList) movieImageModels);

        MovieImagesGalleryFragment fragment = new MovieImagesGalleryFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public MovieImagesGalleryFragment() {
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
            movieIndex = getArguments().getInt(CURRENT_IMAGE_INDEX);
            movieImageModels = getArguments().getParcelableArrayList(MOVIE_IMAGE_MODELS_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_movie_images_gallery, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imagesAdapter = new ImagesAdapter(getActivity(), movieImageModels);

        viewPager = (ViewPager) view.findViewById(R.id.movies);
        viewPager.setPageTransformer(true, new ImageTransformer(0.8f));
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(imagesAdapter);

        if(movieIndex > 0) {
            viewPager.setCurrentItem(movieIndex);
        }

        if(savedInstanceState != null) {
            int currentImagePosition = savedInstanceState.getInt(CURRENT_IMAGE_INDEX);
            viewPager.setCurrentItem(currentImagePosition);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private static class ImageTransformer implements ViewPager.PageTransformer {

        private final float scaleAmount;

        public ImageTransformer(float scalingStart) {
            scaleAmount = 1 - scalingStart;
        }

        @Override
        public void transformPage(View page, float position) {
            if (position >= 0f) {
                final int w = page.getWidth();
                float scaleFactor = 1 - scaleAmount * position;

                page.setAlpha(1f - position);
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
                page.setTranslationX(w * (1 - position) - w);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(CURRENT_IMAGE_INDEX, viewPager.getCurrentItem());
        super.onSaveInstanceState(outState);
    }
}
