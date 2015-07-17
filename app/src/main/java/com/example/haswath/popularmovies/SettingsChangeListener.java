package com.example.haswath.popularmovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by haswath on 7/14/15.
 */
public class SettingsChangeListener implements SharedPreferences.OnSharedPreferenceChangeListener {


    private final Context context;
    private final CustomAdapter adapter;

    public SettingsChangeListener(Context applicationContext, CustomAdapter adapter) {
        this.context = applicationContext;
        this.adapter = adapter;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("sort_by")) {
            String sortBy = sharedPreferences.getString(key, "popularity.desc");
            this.adapter.clear();
            FetchMoviesTask weatherTask = new FetchMoviesTask(context, sortBy, this.adapter);
            weatherTask.execute();
        }

    }



}
