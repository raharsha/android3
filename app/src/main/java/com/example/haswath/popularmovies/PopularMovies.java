package com.example.haswath.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;


public class PopularMovies extends Activity  {

    private CustomAdapter adapter;
    private SettingsChangeListener listener;
    private String sortBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvUsers);
        recyclerView.setHasFixedSize(true);
        // First param is number of columns and second param is orientation i.e Vertical or Horizontal
        final GridLayoutManager gridLayoutManager =
                new GridLayoutManager(getApplicationContext(), 2, LinearLayoutManager.VERTICAL, false);
        if(getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridLayoutManager.setSpanCount(4);
        }
        // Attach the layout manager to the recycler view
        recyclerView.setLayoutManager(gridLayoutManager);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sortBy = prefs.getString("sort_by", "popularity.desc");
        if(savedInstanceState == null || !savedInstanceState.containsKey("key")) {
            // Lookup the recyclerview in activity layout
            // Create adapter passing in the sample user data
            this.adapter = new CustomAdapter(getApplicationContext(), new ArrayList<Movie>());
            if (isNetworkAvailable()) {
                FetchMoviesTask weatherTask = new FetchMoviesTask(getApplicationContext(), sortBy, adapter);
                weatherTask.execute();
            }
            // Attach the adapter to the recyclerview to populate items
            // Set layout manager to position the items
            this.listener = new SettingsChangeListener(getApplicationContext(), adapter);
            prefs.registerOnSharedPreferenceChangeListener(listener);
        } else {
            ArrayList<Movie> list = savedInstanceState.getParcelableArrayList("key");
            this.adapter = new CustomAdapter(getApplicationContext(), list);
        }
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("key", adapter.getItems());
        super.onSaveInstanceState(outState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_popular_movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendMessage(MenuItem item) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .registerOnSharedPreferenceChangeListener(listener);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String sortBy = prefs.getString("sort_by", "popularity.desc");
        if (!sortBy.equals(this.sortBy)) {
            adapter.clear();
            final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvUsers);
            recyclerView.removeAllViews();
            if (isNetworkAvailable()) {
                FetchMoviesTask weatherTask = new FetchMoviesTask(getApplicationContext(), sortBy, adapter);
                weatherTask.execute();
            }
        }
    }

    //Based on a stackoverflow snippet
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onPause() {
        super.onPause();
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .unregisterOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .unregisterOnSharedPreferenceChangeListener(listener);
    }
}
