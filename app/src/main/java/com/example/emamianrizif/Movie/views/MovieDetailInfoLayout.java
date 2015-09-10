package com.example.emamianrizif.Movie.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.emamianrizif.Movie.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by fatemeh on 24/03/15.
 */
public class MovieDetailInfoLayout extends LinearLayout implements Target{

    private final TextView mTitleTextView;
    private final TextView mContentTextView;

    public MovieDetailInfoLayout(Context context) {
        this(context, null);
    }

    public MovieDetailInfoLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MovieDetailInfoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.include_movie_detail_info, this, true);

        mTitleTextView = (TextView) findViewById(R.id.title);
        mContentTextView = (TextView) findViewById(R.id.content);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MovieDetailInfoLayout);

        final String title = array.getString(R.styleable.MovieDetailInfoLayout_title);

        if(!TextUtils.isEmpty(title)) {
            mTitleTextView.setText(title);
        }

        array.recycle();
    }

    public TextView getTitleTextView() {
        return mTitleTextView;
    }

    public TextView getContentTextView() {
        return mContentTextView;
    }

    public void setContentText(CharSequence text) {
        mContentTextView.setText(text);
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        mContentTextView.setCompoundDrawablesWithIntrinsicBounds(
                new BitmapDrawable(getResources(), bitmap),
                null, null, null);
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        mContentTextView.setCompoundDrawables(null, null, null, null);
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
        mContentTextView.setCompoundDrawables(null, null, null, null);
    }
}




























