package com.example.emamianrizif.Movie.adapters;

import android.view.View;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.emamianrizif.Movie.Network.NetworkConstants;
import com.example.emamianrizif.Movie.R;
import com.example.emamianrizif.Movie.helpers.MoviesApplication;
import com.example.emamianrizif.Movie.model.Career;
import com.example.emamianrizif.Movie.model.MovieCredits;
import com.example.emamianrizif.Movie.model.MovieCrew;

import java.util.List;

/**
 * Created by fatemeh on 05/05/15, 16:29.
 */
public class MovieCrewAdapter extends MovieCreditsAdapter {

    public MovieCrewAdapter(List<? extends Career> movieCastList) {
        super(movieCastList);
    }

    @Override
    public void bindView(final ItemViewHolder holder, int position) {
        final MovieCrew movieCrew = (MovieCrew) movieCreditsList.get(position);

        holder.personLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCreditClick(
                        movieCrew.getId(),
                        movieCrew.getName(),
                        movieCrew.getProfilePath());
            }
        });

        holder.nameTextView.setText(movieCrew.getName());
        holder.characterTextView.setText(movieCrew.getJob());


        String url = NetworkConstants.POSTER_PATH_URL + movieCrew.getProfilePath();
        ImageLoader imageLoader = MoviesApplication.getInstance().getImageLoader();

        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
//                progressBar.setVisibility(View.GONE);
                holder.personImageView.setImageBitmap(imageContainer.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                progressBar.setVisibility(View.GONE);
                holder.personImageView.setImageResource(R.drawable.ic_profile_placeholder);
            }
        });

//        holder.personImageView.setImageUrl(
//                NetworkConstants.POSTER_PATH_URL + movieCrew.getProfilePath(),
//                imageLoader);
    }
}
