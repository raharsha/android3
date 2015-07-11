package com.example.haswath.popularmovies;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.haswath.popularmovies.dummy.DummyContent;


/**
 * A placeholder fragment containing a simple view.
 */
public class PopularMoviesFragment extends Fragment implements OnSharedPreferenceChangeListener{
    GridView gv;
    Context context;
    private CustomAdapter adapter;

    public PopularMoviesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.adapter = new CustomAdapter(getActivity(), DummyContent.ITEM_MAP, R.id.imageView1);
        View rootView = inflater.inflate(R.layout.fragment_popular_movies, container, false);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        prefs.registerOnSharedPreferenceChangeListener(this);
        FetchMoviesTask weatherTask = new FetchMoviesTask(getActivity(), prefs.getString("sort_by", "popularity.desc"), this.adapter);
        weatherTask.execute();
        gv=(GridView) rootView.findViewById(R.id.gridView);
        gv.setAdapter(adapter);
        return rootView;

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("sort_by")) {
            if (sharedPreferences.getString(key, "popularity.desc").equals("vote_average.desc")) {
                this.adapter.clear();
                FetchMoviesTask weatherTask = new FetchMoviesTask(getActivity(), "vote_average.desc", this.adapter);
                weatherTask.execute();
            } else {
                this.adapter.clear();
                FetchMoviesTask weatherTask = new FetchMoviesTask(getActivity(), "popularity.desc", this.adapter);
                weatherTask.execute();
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        PreferenceManager.getDefaultSharedPreferences(getActivity())
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        PreferenceManager.getDefaultSharedPreferences(getActivity())
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
