package com.example.emamianrizif.Movie.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Outline;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.LinearLayout;

import com.example.emamianrizif.Movie.R;

/**
 * Created by fatemeh on 04/05/15, 11:46.
 */
public class PersonItemLayout extends LinearLayout {

    public PersonItemLayout(Context context) {
        super(context);
    }

    public PersonItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PersonItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PersonItemLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private class ClipOutlineProvider extends ViewOutlineProvider {

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void getOutline(View view, Outline outline) {
            int size = getResources().getDimensionPixelSize(R.dimen.nav_profile_image_size);

            outline.setOval(0, 0, size, size);
        }
    }
}
