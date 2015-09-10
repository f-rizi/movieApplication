package com.example.emamianrizif.Movie.utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.graphics.Palette;

import com.example.emamianrizif.Movie.R;

import java.util.HashMap;
import java.util.Map;

public class ColorUtils {
    public static int getDynamicColor(Context context, Bitmap picture) {
        Palette palette  = Palette.generate(picture, 32);

        int color = context.getResources().getColor(R.color.blue_gray_700);

        Palette.Swatch swatch = palette.getVibrantSwatch();

        if(swatch != null) {
            color = swatch.getRgb();
        }
        return color;
    }
}
