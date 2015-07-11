package com.example.haswath.popularmovies;

/**
 * Created by haswath on 7/10/15.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class CustomAdapter extends ArrayAdapter<MovieData> {

    Context context;
    public static Map<String, MovieData> items;
    private static LayoutInflater inflater=null;
    public CustomAdapter(Context mainActivity, Map<String, MovieData> arr, int res) {
        // TODO Auto-generated constructor stub
        super(mainActivity, res);
        context=mainActivity;
        this.items = arr;
        addAll(items.values());
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View rowView;

        rowView = inflater.inflate(R.layout.movies, null);
        ImageView img = (ImageView) rowView.findViewById(R.id.imageView1);


        try {
            MovieData item = items.get(String.valueOf(position));
            JSONObject jo = item.content;
            String url = "http://image.tmdb.org/t/p/w185" + jo.getString("poster_path");
            Picasso.with(context).load(url).into(img);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        rowView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_LONG).show();
            }
        });

        return rowView;
    }

}
