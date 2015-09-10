package com.example.emamianrizif.Movie.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.emamianrizif.Movie.R;
import com.example.emamianrizif.Movie.fragments.MovieCreditsListFragment;
import com.example.emamianrizif.Movie.helpers.LruBitmapCache;
import com.example.emamianrizif.Movie.helpers.MoviesApplication;
import com.example.emamianrizif.Movie.model.Career;
import com.example.emamianrizif.Movie.model.MovieCredits;

import java.util.List;

/**
 * Created by fatemeh on 04/05/15, 11:27.
 */
public class MovieCreditsAdapter extends RecyclerView.Adapter<MovieCreditsAdapter.ItemViewHolder>{

    protected List<? extends Career> movieCreditsList;
    protected final ImageLoader imageLoader;
    protected Context context;

    public int itemHeight;

    protected MovieCreditsListFragment.OnCreditsListFragmentInteractionListener listener;

    public MovieCreditsAdapter(List<? extends Career> movieCastList) {
        this.movieCreditsList = movieCastList;

        ImageLoader.ImageCache imageCache = new LruBitmapCache();
        RequestQueue requestQueue = MoviesApplication.getInstance().getRequestQueue();
        imageLoader = new ImageLoader(requestQueue, imageCache);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_person, parent, false);

        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        bindView(holder, position);
    }

    public void bindView(ItemViewHolder holder, int position) {}

    public void setListener(MovieCreditsListFragment.OnCreditsListFragmentInteractionListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return movieCreditsList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        protected LinearLayout personLayout;
        protected ImageView personImageView;
        protected TextView nameTextView;
        protected TextView characterTextView;

        ItemViewHolder(View view) {
            super(view);

            personLayout = (LinearLayout) view.findViewById(R.id.root_layout);
            personImageView = (ImageView) view.findViewById(R.id.person_image);
            nameTextView = (TextView) view.findViewById(R.id.name);
            characterTextView = (TextView) view.findViewById(R.id.character);
        }
    }
}
