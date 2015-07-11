package com.example.haswath.popularmovies;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.haswath.popularmovies.dummy.DummyContent;


/**
 * A placeholder fragment containing a simple view.
 */
public class PopularMoviesFragment extends Fragment {
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
        FetchMoviesTask weatherTask = new FetchMoviesTask(getActivity(), "popularity.desc", this.adapter);
        weatherTask.execute();
        gv=(GridView) rootView.findViewById(R.id.gridView);
        gv.setAdapter(adapter);
        return rootView;

    }
}
