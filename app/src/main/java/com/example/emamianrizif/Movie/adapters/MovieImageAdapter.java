package com.example.emamianrizif.Movie.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.emamianrizif.Movie.Network.NetworkConstants;
import com.example.emamianrizif.Movie.R;
import com.example.emamianrizif.Movie.fragments.MovieImagesFragment;
import com.example.emamianrizif.Movie.model.MovieImageModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by fatemeh on 27/03/15.
 */

public class MovieImageAdapter extends RecyclerView.Adapter<MovieImageAdapter.ViewHolder>{
    private MovieImagesFragment.OnImageInteractionListener listner;

    List<MovieImageModel> movieImageModels;
    Context context;

    public MovieImageAdapter(Context context, List<MovieImageModel> movieImageModels) {
        this.movieImageModels = movieImageModels;
        this.context = context;
    }

    public MovieImageAdapter(List<MovieImageModel> movieImageModels) {
        this.movieImageModels = movieImageModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_movie_image, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final MovieImageModel movieImageModel = movieImageModels.get(position);

        String url = NetworkConstants.POSTER_PATH_URL + movieImageModel.getFile_path();

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.onImageCLick(position);
            }
        });

        Picasso.with(context).load(url).into(holder.imageView);
    }

    public void setListener(MovieImagesFragment.OnImageInteractionListener listener) {
        this.listner = listener;
    }

    @Override
    public int getItemCount() {
        return movieImageModels.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.image);
        }
    }
}
