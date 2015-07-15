package com.example.haswath.popularmovies;

import android.content.Context;
import android.os.AsyncTask;

import com.uwetrottmann.tmdb.Tmdb;
import com.uwetrottmann.tmdb.entities.MovieResultsPage;

import java.util.List;

/**
 * Created by haswath on 7/10/15.
 */
public class FetchMoviesTask extends AsyncTask<Void, Void, List<com.uwetrottmann.tmdb.entities.Movie>> {

    private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();
    private final CustomAdapter adapter;
    private final com.uwetrottmann.tmdb.services.MoviesService movieService;
    private Context context;
    private String sortBy = "popularity.desc";

    public FetchMoviesTask(Context context, String sort, CustomAdapter adapter) {
        this.context = context;
        this.sortBy = sort;
        this.adapter = adapter;
        Tmdb tmdb = new Tmdb();
        tmdb.setApiKey("e95feea469573da8277773d047559d1a");
        movieService = tmdb.moviesService();
    }

    @Override
    protected List<com.uwetrottmann.tmdb.entities.Movie> doInBackground(Void... params) {
            MovieResultsPage movies = sortBy.equals("popularity.desc") ? movieService.popular(1, "en") : movieService.topRated(1, "en");
            return movies.results;
    }

    @Override
    protected void onPostExecute(List<com.uwetrottmann.tmdb.entities.Movie> result) {
        if (result != null) {
            int i = 0;
            for (com.uwetrottmann.tmdb.entities.Movie movie : result) {
                Movie movie1 = new Movie(movie);
                adapter.add(i,movie1);
                i++;
            }
        }
    }
}
