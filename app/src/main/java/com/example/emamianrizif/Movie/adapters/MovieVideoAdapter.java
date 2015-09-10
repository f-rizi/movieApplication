package com.example.emamianrizif.Movie.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.emamianrizif.Movie.Network.NetworkConstants;
import com.example.emamianrizif.Movie.R;
import com.example.emamianrizif.Movie.model.movieVideo;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by fatemeh on 28/03/15.
 */

public class MovieVideoAdapter
        extends RecyclerView.Adapter<MovieVideoAdapter.ViewHolder>{

    List<movieVideo> movieVideos;
    Context context;

    public MovieVideoAdapter(Context context, List<movieVideo> movieVideos) {
        this.movieVideos = movieVideos;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_movie_video, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final movieVideo movieVideo = movieVideos.get(position);

        final String videoKey = movieVideo.getKey();
        String url = NetworkConstants.YOUTUBE_IMAGE_URL;
        url = String.format(url, videoKey);

        Picasso.with(context).load(url).into(holder.imageView);

//        holder.videoNameTextView.setText(movieVideo.getName());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String videoURL = NetworkConstants.YOUTUBE_VIDEO_URL + movieVideo.getKey();

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoURL));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieVideos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);

            imageView = (ImageView) view.findViewById(R.id.video_image);
        }
    }
}