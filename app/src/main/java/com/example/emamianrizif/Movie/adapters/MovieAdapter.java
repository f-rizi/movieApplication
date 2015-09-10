package com.example.emamianrizif.Movie.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.emamianrizif.Movie.Network.NetworkConstants;
import com.example.emamianrizif.Movie.R;
import com.example.emamianrizif.Movie.activities.MainActivity;
import com.example.emamianrizif.Movie.helpers.LruBitmapCache;
import com.example.emamianrizif.Movie.helpers.MoviesApplication;
import com.example.emamianrizif.Movie.model.ShortMovie;
import com.example.emamianrizif.Movie.views.PosterImageView;

import java.util.List;


/**
 * Created by fatemeh on 1/4/15.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ItemViewHolder> {

    private final List<ShortMovie> movies;
    private final MovieActionListener movieActionListener;
    private final ImageLoader imageLoader;
    private final Context context;

    public MovieAdapter(Context context, List<ShortMovie> movies, MovieActionListener movieActionListener) {
        this.context = context;
        this.movies = movies;
        this.movieActionListener = movieActionListener;

        ImageLoader.ImageCache imageCache = new LruBitmapCache();
        RequestQueue requestQueue = MoviesApplication.getInstance().getRequestQueue();
        //imageLoader = new ImageLoader(requestQueue, imageCache);
        imageLoader = MoviesApplication.getInstance().getImageLoader();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_movie, parent, false);

        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        final ShortMovie movie = movies.get(position);

        holder.movieLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieActionListener.onMovieClick(holder.movieLayout, movie.getId());
            }
        });

        String url = NetworkConstants.POSTER_PATH_URL + movie.getPosterPath();

        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
//                progressBar.setVisibility(View.GONE);
                holder.posterImageView.setImageBitmap(imageContainer.getBitmap());

                if(MainActivity.firstDisplay) {
                    Animation fadeInAnimation =
                            AnimationUtils.loadAnimation(context, R.anim.fade_in);

                    holder.posterImageView.startAnimation(fadeInAnimation);
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                progressBar.setVisibility(View.GONE);
                holder.posterImageView.setImageResource(R.drawable.placeholder7);
                holder.titleTextView.setText(movie.getTitle());
                holder.titleTextView.setVisibility(View.VISIBLE);
            }
        });

//        holder.posterImageView.setImageUrl(url, imageLoader);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        protected RelativeLayout movieLayout;
        protected PosterImageView posterImageView;
        protected TextView titleTextView;

        ItemViewHolder(View view) {
            super(view);

            movieLayout = (RelativeLayout) view.findViewById(R.id.movie);
            posterImageView = (PosterImageView) view.findViewById(R.id.poster);
            titleTextView = (TextView) view.findViewById(R.id.title);
        }

        public View getPosterImageView() {
            return posterImageView;
        }
    }

    public interface MovieActionListener {
        void onMovieClick(View view, long movieID);
    }
}
