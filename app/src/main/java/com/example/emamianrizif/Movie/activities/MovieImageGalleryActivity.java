package com.example.emamianrizif.Movie.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.emamianrizif.Movie.R;
import com.example.emamianrizif.Movie.fragments.MovieImagesGalleryFragment;
import com.example.emamianrizif.Movie.model.MovieImageModel;

import java.util.List;

public class MovieImageGalleryActivity extends AppCompatActivity {

    private static final String TAG = MovieImageGalleryActivity.class.getSimpleName();

    public static final String MOVIE_LIST_KEY = "movieList";
    public static final String MOVIE_POSITION_KEY = "moviePosition";

    private List<MovieImageModel> movieImageModels;
    private int moviePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_image_galary);

        Bundle bundle = getIntent().getExtras();
        moviePosition = bundle.getInt(MOVIE_POSITION_KEY);
        movieImageModels = bundle.getParcelableArrayList(MOVIE_LIST_KEY);

        Toast.makeText(this, movieImageModels.size() + "", Toast.LENGTH_SHORT).show();


        FragmentManager fragmentManager = getFragmentManager();
        MovieImagesGalleryFragment fragment = MovieImagesGalleryFragment.newInstance(moviePosition, movieImageModels);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.cast_fragment_place_holder, fragment);
        fragmentTransaction.addToBackStack(MovieImagesGalleryFragment.TAG);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movie_image_galary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
