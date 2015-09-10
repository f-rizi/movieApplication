package com.example.emamianrizif.Movie.activities;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.example.emamianrizif.Movie.Network.NetworkConstants;
import com.example.emamianrizif.Movie.R;
import com.example.emamianrizif.Movie.adapters.MovieVideoAdapter;
import com.example.emamianrizif.Movie.fragments.MovieCrewListFragment;
import com.example.emamianrizif.Movie.fragments.MovieCreditsListFragment;
import com.example.emamianrizif.Movie.fragments.MovieImagesFragment;
import com.example.emamianrizif.Movie.fragments.MovieCastListFragment;
import com.example.emamianrizif.Movie.helpers.MoviesApplication;
import com.example.emamianrizif.Movie.model.MovieCast;
import com.example.emamianrizif.Movie.model.MovieCrew;
import com.example.emamianrizif.Movie.model.Genre;
import com.example.emamianrizif.Movie.model.LongMovie;
import com.example.emamianrizif.Movie.model.MovieImageModel;
import com.example.emamianrizif.Movie.model.SpokenLanguage;
import com.example.emamianrizif.Movie.model.movieVideo;
import com.example.emamianrizif.Movie.utilities.ColorUtils;
import com.example.emamianrizif.Movie.views.ExpandableTextView;
import com.example.emamianrizif.Movie.views.MovieDetailCardLayout;
import com.example.emamianrizif.Movie.views.MovieDetailInfoLayout;
import com.example.emamianrizif.Movie.views.NotifyingScrollView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import hugo.weaving.DebugLog;


public class MovieDetailsActivity
        extends AppCompatActivity
        implements MovieImagesFragment.OnImageInteractionListener,
        MovieCreditsListFragment.OnCreditsListFragmentInteractionListener {

    private static final String TAG = MovieDetailsActivity.class.getSimpleName();

    public static final int CREW_TYPE = 0;
    public static final int CAST_TYPE = 1;

    private RecyclerView videosRecyclerView;

    private Toolbar toolbar;

    private LongMovie mLongMovie;

    private int averageColor;

    private List<movieVideo> movieVideos;
    private List<MovieImageModel> movieImageModels;
    private List<MovieCast> movieMovieCasts;
    private List<MovieCrew> movieMovieCrew;


    private ViewOutlineProvider mOutlineProvider;

    private int totalCastSize;
    private int totalCrewSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        initViews();

        setToolbar();
        setListeners();

        mOutlineProvider = new ClipOutlineProvider();
    }

    private void initViews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        videosRecyclerView = (RecyclerView) findViewById(R.id.videos);
        videosRecyclerView.setLayoutManager(layoutManager);
    }

    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolBar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Drawable backgroundColor = getResources().getDrawable(
                R.color.blue_gray_500, null);
        toolbar.setBackground(backgroundColor);
        toolbar.getBackground().setAlpha(0);

        int textColor = Color.argb(0, 250, 250, 250);
        toolbar.setTitleTextColor(textColor);
    }

    private void setListeners() {
        ((NotifyingScrollView) findViewById(R.id.scroll))
                .setOnScrollChangedListener(mOnScrollChangedListener);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().registerSticky(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @DebugLog
    public void onEvent(LongMovie longMovie) {
        mLongMovie = longMovie;

        setTitles();
        setMovieDetails();

        setPosterImage();
        setBackDropImage();

        setOverview();

        getMovieImages();
        getMovieTrailers();
        getMovieStaff();
    }

    private void setTitles() {
        getSupportActionBar().setTitle(mLongMovie.getOriginalTitle());
        setTagLine();
        setMovieTitle();
    }

    private void setTagLine() {
        final TextView tagLineTextView = (TextView) findViewById(R.id.tag_line);

        String tagLine = mLongMovie.getTagLine();

        if (TextUtils.isEmpty(tagLine)) {
            tagLine = getString(R.string.empty_tag_line);
        }

        tagLineTextView.setText(tagLine);
    }

    private void setMovieTitle() {
        final TextView movieTitleTextView = (TextView) findViewById(R.id.text_next_to_poster);

        String year = mLongMovie.getReleaseDate();

        if (!TextUtils.isEmpty(year)) {
            year = year.split("-")[0];
            year = " (" + year + ")";
        }

        String title = mLongMovie.getTitle() + year;

        if (TextUtils.isEmpty(title)) {
            title = getString(R.string.empty_title);
        }

        movieTitleTextView.setText(title);
    }

    private void setMovieDetails() {
        setRuntime();
        setReleased();
        setGenres();
        setBudgets();
        setLanguages();
    }

    private void setRuntime() {
        MovieDetailInfoLayout runtimeInfoLayout = (MovieDetailInfoLayout)
                findViewById(R.id.runtime_info);

        if (mLongMovie.getRunTime() != 0) {
            runtimeInfoLayout.setContentText(mLongMovie.getRunTime() + "");
            runtimeInfoLayout.setVisibility(View.VISIBLE);
        }
    }

    private void setReleased() {
        MovieDetailInfoLayout releasedInfoLayout = (MovieDetailInfoLayout)
                findViewById(R.id.released_info);

        if (!TextUtils.isEmpty(mLongMovie.getReleaseDate())) {
            releasedInfoLayout.setContentText(mLongMovie.getReleaseDate());
            releasedInfoLayout.setVisibility(View.VISIBLE);
        }
    }

    private void setGenres() {
        MovieDetailInfoLayout genresInfoLayout = (MovieDetailInfoLayout)
                findViewById(R.id.genres_info);

        List<Genre> genres = mLongMovie.getGenreList();
        int gentsSize = genres.size();

        if (gentsSize > 0) {
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < gentsSize - 1; i++) {
                stringBuilder.append(genres.get(i).getName()).append(", ");
            }
            stringBuilder.append(genres.get(gentsSize - 1).getName());

            genresInfoLayout.setContentText(stringBuilder.toString());
            genresInfoLayout.setVisibility(View.VISIBLE);
        }
    }

    private void setBudgets() {
        MovieDetailInfoLayout budgetInfoLayout = (MovieDetailInfoLayout)
                findViewById(R.id.budget_info);

        if (mLongMovie.getBudget() > 0) {
            budgetInfoLayout.setContentText(mLongMovie.getBudget() + "");
            budgetInfoLayout.setVisibility(View.VISIBLE);
        }
    }

    private void setLanguages() {
        MovieDetailInfoLayout languagesInfoLayout = (MovieDetailInfoLayout)
                findViewById(R.id.language_info);

        List<SpokenLanguage> spokenLanguages = mLongMovie.getSpokenLanguageList();
        int spokenLanguagesSize = spokenLanguages.size();

        if (spokenLanguagesSize > 0) {
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < spokenLanguagesSize - 1; i++) {
                stringBuilder.append(spokenLanguages.get(i).getName() + ", ");
            }
            stringBuilder.append(spokenLanguages.get(spokenLanguagesSize - 1).getName());

            languagesInfoLayout.setContentText(stringBuilder.toString());
            languagesInfoLayout.setVisibility(View.VISIBLE);
        }
    }

    private void setPosterImage() {
        final ImageView posterImageView = (ImageView) findViewById(R.id.poster);

        final int posterType = getResources().getInteger(R.integer.poster_type);

        String imagePath;

        if (posterType == 1) {
            imagePath = mLongMovie.getBackdropPath();

        } else {
            imagePath = mLongMovie.getPosterPath();
        }

        String url = NetworkConstants.POSTER_PATH_URL + imagePath;

        Picasso.with(this).load(url).into(posterImageView,
                new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        final Bitmap bitmap = ((BitmapDrawable)
                                posterImageView.getDrawable()).getBitmap();

                        averageColor = ColorUtils.getDynamicColor(getBaseContext(), bitmap);
                        LinearLayout secondPartLayout = (LinearLayout)
                                findViewById(R.id.second_part);
                        secondPartLayout.setBackgroundColor(averageColor);

                        TextView tagLineTextView = (TextView) findViewById(R.id.tag_line);
                        tagLineTextView.setBackgroundColor(averageColor);
                    }

                    @Override
                    public void onError() {

                        if (posterType == 1) {
                            posterImageView.setImageResource(R.drawable.placeholder);

                        } else if (posterType == 2) {
                            posterImageView.setImageResource(R.drawable.placeholder);
                        }
                    }
                }
        );
    }

    private void setBackDropImage() {
        final ImageView backDropImageView =
                (ImageView) findViewById(R.id.backdrop);

        String url = NetworkConstants.POSTER_PATH_URL +
                mLongMovie.getPosterPath();

        Picasso.with(this).load(url).into(backDropImageView);
    }

    private void setOverview() {
        final ExpandableTextView expandableTextView =
                (ExpandableTextView) findViewById(R.id.overview);

        expandableTextView.setText(mLongMovie.getOverView());
    }

    public void getMovieImages() {
        String url = String.format(
                NetworkConstants.IMAGES_LIST_URL, mLongMovie.getId());

        JsonObjectRequest jsonObjectRequest = new
                JsonObjectRequest(Request.Method.GET, url, null,
                successListenerImages(), errorListener());

        MoviesApplication.getInstance().addToRequestQueue(
                jsonObjectRequest, "getMovieImages");
    }

    private Response.Listener<JSONObject> successListenerImages() {
        return new Response.Listener<JSONObject>() {
            @DebugLog
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray backdrops = response.getJSONArray("backdrops");
                    JSONArray posters = response.getJSONArray("posters");

                    int backdropsSize = Math.min(backdrops.length(), 6);
                    int postersSize = Math.min(posters.length(), 4);

                    movieImageModels = new ArrayList<>();
                    MovieImageModel movieImageModel;

                    for (int i = 0; i < backdropsSize; i++) {
                        movieImageModel = new MovieImageModel(backdrops.getJSONObject(i));
                        movieImageModels.add(movieImageModel);
                    }

                    setMovieImagesFragment();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void setMovieImagesFragment() {
        if (movieImageModels.size() == 0) {
            findViewById(R.id.images).setVisibility(View.GONE);

        } else {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            MovieImagesFragment fragment = MovieImagesFragment.newInstance(movieImageModels);
            fragmentTransaction.add(R.id.images, fragment);
            fragmentTransaction.commit();
        }
    }

    private void getMovieTrailers() {
        String url = String.format(NetworkConstants.VIDEO_LIST_URL, mLongMovie.getId());

        JsonObjectRequest jsonObjectRequest = new
                JsonObjectRequest(Request.Method.GET, url, null,
                successVideosListener(), errorListener());

        MoviesApplication.getInstance().addToRequestQueue(
                jsonObjectRequest, "getMovieTrailers");
    }

    private Response.Listener<JSONObject> successVideosListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray videos = response.getJSONArray("results");

                    int videosSize = Math.min(videos.length(), 4);

                    List<movieVideo> movieVideos = new ArrayList<>();
                    movieVideo video;

                    for (int i = 0; i < videosSize; i++) {
                        video = new movieVideo(videos.getJSONObject(i));
                        movieVideos.add(video);
                    }

                    if (videosSize == 0) {
                        findViewById(R.id.movie_videos_card).setVisibility(View.GONE);

                    } else {
                        MovieVideoAdapter adapter = new MovieVideoAdapter(getBaseContext(),
                                movieVideos);

                        videosRecyclerView.setAdapter(adapter);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public void getMovieStaff() {
        String url = String.format(NetworkConstants.MOVIE_CASTS_URL, mLongMovie.getId());

        JsonObjectRequest jsonObjectRequest = new
                JsonObjectRequest(Request.Method.GET, url, null,
                successListenerStaff(), errorListener());

        MoviesApplication.getInstance().addToRequestQueue(
                jsonObjectRequest, "getMovieStaff");
    }

    private Response.Listener<JSONObject> successListenerStaff() {
        return new Response.Listener<JSONObject>() {
            @DebugLog
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray casts = response.getJSONArray("cast");
                    setMovieCasts(casts);
                    setSeeMoreCast();
                    setCastsFragment();

                    JSONArray crews = response.getJSONArray("crew");
                    setMovieCrew(crews);
                    setSeeMoreCrew();
                    setCrewFragment();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void setMovieCasts(JSONArray casts) throws JSONException {
        totalCastSize = casts.length();

        if (totalCastSize == 0) {
            findViewById(R.id.casts_card).setVisibility(View.GONE);
            return;
        }

        int castsSize = Math.min(totalCastSize, 3);

        movieMovieCasts = new ArrayList<>();

        for (int i = 0; i < totalCastSize; i++) {
            MovieCast movieMovieCast = new MovieCast(casts.getJSONObject(i));
            movieMovieCasts.add(movieMovieCast);
        }
    }

    private void setSeeMoreCast() {
        MovieDetailCardLayout castHeader = (MovieDetailCardLayout)
                findViewById(R.id.casts_header);

        if (totalCastSize >= 3) {
            castHeader.setSeeMoreVisibility(true);

            castHeader.setSeeMoreOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSeeMoreClick(CAST_TYPE);
                }
            });
        }
    }

    private void setCastsFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        List<MovieCast> movieCasts = new ArrayList<>();

        int min = Math.min(totalCastSize, 3);
        for (int i = 0; i < min; i++) {
            movieCasts.add(movieMovieCasts.get(i));
        }

        MovieCastListFragment fragment = MovieCastListFragment.newInstance(movieCasts);
        fragmentTransaction.add(R.id.cast_fragment_place_holder, fragment);
        fragmentTransaction.commit();
    }

    private void setMovieCrew(JSONArray crews) throws JSONException {
        totalCrewSize = crews.length();

        if (totalCrewSize == 0) {
            findViewById(R.id.crew_card).setVisibility(View.GONE);
            return;
        }

        movieMovieCrew = new ArrayList<>();

        for (int i = 0; i < totalCrewSize; i++) {
            MovieCrew movieCrew = new MovieCrew(crews.getJSONObject(i));
            movieMovieCrew.add(movieCrew);
        }
    }

    private void setSeeMoreCrew() {
        MovieDetailCardLayout castHeader = (MovieDetailCardLayout)
                findViewById(R.id.crew_header);

        if (totalCrewSize >= 3) {
            castHeader.setSeeMoreVisibility(true);

            castHeader.setSeeMoreOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSeeMoreClick(CREW_TYPE);
                }
            });
        }
    }

    private void setCrewFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        List<MovieCrew> movieCrews = new ArrayList<>();
        int min = Math.min(totalCrewSize, 3);
        for (int i = 0; i < min; i++) {
            movieCrews.add(movieMovieCrew.get(i));
        }

        MovieCrewListFragment fragment = MovieCrewListFragment.newInstance(movieCrews);
        fragmentTransaction.add(R.id.crew_fragment_place_holder, fragment);
        fragmentTransaction.commit();
    }


    private NotifyingScrollView.OnScrollChangedListener mOnScrollChangedListener =
            new NotifyingScrollView.OnScrollChangedListener() {
                public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
                    final int headerHeight = findViewById(R.id.poster).getHeight()
                            - getSupportActionBar().getHeight();
                    final float ratio = (float) Math.min(Math.max(t, 0),
                            headerHeight) / headerHeight;
                    final int newAlpha = (int) (ratio * 255);

                    toolbar.getBackground().setAlpha(newAlpha);

                    int textColor = Color.argb(newAlpha, 250, 250, 250);
                    toolbar.setTitleTextColor(textColor);
                }
            };


    private Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO handel Error
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finishAfterTransition();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onImageCLick(int moviePosition) {
        Intent intent = new Intent(this, MovieImageGalleryActivity.class);

        Bundle bundle = new Bundle();
        bundle.putInt(MovieImageGalleryActivity.MOVIE_POSITION_KEY, moviePosition);
        bundle.putParcelableArrayList(MovieImageGalleryActivity.MOVIE_LIST_KEY, (ArrayList) movieImageModels);

        intent.putExtras(bundle);
        startActivity(intent);
    }

    @DebugLog
    @Override
    public void onCreditClick(int castID, String name, String imagePath) {
        Intent intent = new Intent(this, PersonDetailsActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString(PersonDetailsActivity.PERSON_NAME, name);
        bundle.putInt(PersonDetailsActivity.PERSON_ID_EXTRA, castID);
        bundle.putString(PersonDetailsActivity.PERSON_IMAGE_PATH, imagePath);

        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void onSeeMoreClick(int itemsType) {
        Intent intent = new Intent(this, MoviePeopleListActivity.class);

        Bundle bundle = new Bundle();

        bundle.putInt(MoviePeopleListActivity.MOVIE_PEOPLE_TYPE, itemsType);


        if (itemsType == CREW_TYPE) {
            bundle.putString(MoviePeopleListActivity.MOVIE_NAME
                    , mLongMovie.getOriginalTitle());

            bundle.putParcelableArrayList(
                    MoviePeopleListActivity.MOVIE_PEOPLE_LIST,
                    (ArrayList<? extends Parcelable>) movieMovieCrew);

        } else if (itemsType == CAST_TYPE) {
            bundle.putString(MoviePeopleListActivity.MOVIE_NAME
                    , mLongMovie.getOriginalTitle());

            bundle.putParcelableArrayList(
                    MoviePeopleListActivity.MOVIE_PEOPLE_LIST,
                    (ArrayList<? extends Parcelable>) movieMovieCasts);
        }

        intent.putExtras(bundle);
        startActivity(intent);
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
