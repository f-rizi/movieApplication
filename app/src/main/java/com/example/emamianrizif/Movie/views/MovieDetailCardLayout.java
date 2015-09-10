package com.example.emamianrizif.Movie.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.emamianrizif.Movie.R;

/**
 * Created by fatemeh on 24/03/15.
 */
public class MovieDetailCardLayout extends LinearLayout {

    private final TextView mTitleTextView;
    private final TextView mSeeMoreTextView;

    public MovieDetailCardLayout(Context context) {
        this(context, null);
    }

    public MovieDetailCardLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MovieDetailCardLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.movie_detail_card, this, true);

        mTitleTextView = (TextView) findViewById(R.id.title);
        mSeeMoreTextView = (TextView) findViewById(R.id.see_more);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MovieDetailInfoLayout2);

        final String title = array.getString(R.styleable.MovieDetailInfoLayout2_title);
        final boolean seeMoreVisibility = array.getBoolean(R.styleable.MovieDetailInfoLayout2_see_more_visibility, false);

        if(!TextUtils.isEmpty(title)) {
            mTitleTextView.setText(title);
        }

        if(seeMoreVisibility) {
            mSeeMoreTextView.setVisibility(VISIBLE);
        }

        array.recycle();
    }

    public void setSeeMoreVisibility(boolean visible) {
        mSeeMoreTextView.setVisibility(visible ? VISIBLE : GONE);
    }

    public void setSeeMoreOnClickListener(OnClickListener listener) {
        mSeeMoreTextView.setOnClickListener(listener);
    }

    public void setTitle(CharSequence title) {
        mTitleTextView.setText(title);
    }

    public void setTitle(int titleResId) {
        setTitle(getResources().getString(titleResId));
    }
}














