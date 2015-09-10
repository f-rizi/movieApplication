package com.example.emamianrizif.Movie.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.example.emamianrizif.Movie.R;

/**
 * Created by fatemeh on 15/05/15, 08:39.
 */

public class ExpandableTextView extends TextView implements View.OnClickListener {

    private int collapsedMaxLine;

    private boolean expanded;


    public ExpandableTextView(Context context) {
        this(context, null);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.expanding_text_view, 0, defStyleAttr);

        collapsedMaxLine = array.getInt(R.styleable.expanding_text_view_collapsed_maxLines, 8);
        expanded = array.getBoolean(R.styleable.expanding_text_view_expanded, false);

        array.recycle();

        setOnClickListener(this);

        if(expanded) {
            expand();

        } else {
            collapse();
        }
    }

    public void expand() {
        setMaxLines(Integer.MAX_VALUE);
        expanded = true;
    }

    public void collapse() {
        setMaxLines(collapsedMaxLine);
        expanded = false;
    }

    @Override
    public void onClick(View v) {
        if (expanded) {
            collapse();

        } else {
            expand();
        }
    }
}

