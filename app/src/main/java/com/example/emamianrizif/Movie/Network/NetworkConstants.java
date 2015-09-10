package com.example.emamianrizif.Movie.Network;

/**
 * Created by fatemeh on 16/03/15.
 */

public class NetworkConstants {
    public static final String API = "";

    public static final String UPCOMING_MOVIE_URL = "https://api.themoviedb.org/3/movie/upcoming?api_key=" + API;

    public static final String POPULAR_MOVIES_URL = "https://api.themoviedb.org/3/movie/popular?api_key=" + API;

    public static final String IN_THEATER_MOVIES_URL = "https://api.themoviedb.org/3/movie/now-playing?api_key=" + API;

    public static final String POSTER_PATH_URL = "http://image.tmdb.org/t/p/w342";

    public static final String VIDEO_LIST_URL = "http://api.themoviedb.org/3/movie/%s/videos?api_key=" + API;

    public static final String IMAGES_LIST_URL = "http://api.themoviedb.org/3/movie/%s/images?api_key=" + API;

    public static final String MOVIE_CASTS_URL = "http://api.themoviedb.org/3/movie/%s/credits?api_key=" + API;

    public static final String YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v=";

    public static final String YOUTUBE_IMAGE_URL = "http://img.youtube.com/vi/%s/mqdefault.jpg";

    public static final String MOVIE_DETAILS_URL = "http://api.themoviedb.org/3/movie/%d?api_key=";

    public static final String PERSON_DETAILS_URL = "http://api.themoviedb.org/3/person/%d?api_key=" + API;

    public static final String PERSON_CREDIT_URL = "http://api.themoviedb.org/3/person/%d/movie_credits?api_key=" + API;

}
