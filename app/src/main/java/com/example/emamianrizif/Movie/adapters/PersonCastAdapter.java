package com.example.emamianrizif.Movie.adapters;

import android.view.View;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.emamianrizif.Movie.Network.NetworkConstants;
import com.example.emamianrizif.Movie.R;
import com.example.emamianrizif.Movie.helpers.MoviesApplication;
import com.example.emamianrizif.Movie.model.Career;
import com.example.emamianrizif.Movie.model.PersonCast;

import java.util.List;

/**
 * Created by fatemeh on 05/05/15, 16:28.
 */
public class PersonCastAdapter extends MovieCreditsAdapter {

    public PersonCastAdapter(List<? extends Career> movieCastList) {
        super(movieCastList);
    }

    @Override
    public void bindView(final ItemViewHolder holder, int position) {
        final PersonCast personCast = (PersonCast) movieCreditsList.get(position);

        holder.personLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCreditClick(
                        personCast.getId(),
                        personCast.getTitle(),
                        personCast.getPoster_path());
            }
        });

        holder.nameTextView.setText(personCast.getCharacter());
        holder.characterTextView.setText(personCast.getCharacter());


        String url = NetworkConstants.POSTER_PATH_URL + personCast.getPoster_path();
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
                holder.personImageView.setImageResource(R.drawable.placeholder3);
            }
        });

//        holder.personImageView.setImageUrl(
//                NetworkConstants.POSTER_PATH_URL + personCast.getPoster_path(),
//                imageLoader);
    }
}
