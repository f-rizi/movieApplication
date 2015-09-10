package com.example.emamianrizif.Movie.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by fatemeh on 03/05/15, 14:55.
 */
public class HackyViewPager extends ViewPager {

    private static final String LOG_TAG = HackyViewPager.class.getSimpleName();

    public HackyViewPager(Context context) {
        super(context);
    }

    public HackyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            Log.e(LOG_TAG, "onInterceptTouchEvent in IllegalArgumentException");
            return false;
        }
    }
}