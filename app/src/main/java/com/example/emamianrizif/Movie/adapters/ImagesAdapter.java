package com.example.emamianrizif.Movie.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.emamianrizif.Movie.Network.NetworkConstants;
import com.example.emamianrizif.Movie.R;
import com.example.emamianrizif.Movie.helpers.MoviesApplication;
import com.example.emamianrizif.Movie.model.MovieImageModel;

import java.util.List;

/**
 * Created by fatemeh on 03/05/15, 09:48.
 */
public class ImagesAdapter extends PagerAdapter{

    private Context context;
    private List<MovieImageModel> movieImageModels;

    public ImagesAdapter(Context context, List<MovieImageModel> movieImageModels) {
        this.context = context;
        this.movieImageModels = movieImageModels;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.movie_iamges_gallery_item, container, false);

        final ImageView imageView = (ImageView) view.findViewById(R.id.poster);

        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        final MovieImageModel movieImageModel = movieImageModels.get(position);

        String url = NetworkConstants.POSTER_PATH_URL + movieImageModel.getFile_path();

        ImageLoader imageLoader = MoviesApplication.getInstance().getImageLoader();

        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                progressBar.setVisibility(View.GONE);
                imageView.setImageBitmap(imageContainer.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressBar.setVisibility(View.GONE);
            }
        });

//        ViewGroup.LayoutParams params =new ViewGroup.LayoutParams()
        container.addView(view,ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        return view;
    }

    @Override
    public int getCount() {
        return movieImageModels.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
