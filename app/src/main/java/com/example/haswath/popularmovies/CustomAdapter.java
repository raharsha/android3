package com.example.haswath.popularmovies;

/**
 * Created by haswath on 7/10/15.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    protected Context context;
    private static ArrayList<Movie> items;
    public CustomAdapter(Context mainActivity, ArrayList<Movie> arr) {
        context=mainActivity;
        this.items = arr;

    }

    public ArrayList<Movie> getItems() {
        return items;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.movies, parent, false);
        // Return a new holder instance
        return new ViewHolder(itemView.getContext(), itemView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the data model based on position
        Movie item = items.get(position);
        // Set item views based on the data model
        String url = null;
        url = "http://image.tmdb.org/t/p/w185" + item.poster_path;
        Picasso.with(context).load(url).into(holder.ivImg);
    }

    // Return the total count of items
    @Override
    public int getItemCount() {
        return items.size();
    }

    /*
     * Inserting a new item at the head of the list. This uses a specialized
     * RecyclerView method, notifyItemInserted(), to trigger any enabled item
     * animations in addition to updating the view.
     */
    public void add(int position, Movie item) {

        items.add(position, item);
        notifyItemInserted(position);
    }

    public Movie get(int position) {
        return items.get(position);
    }

    public void clear() {
        items.clear();
    }


    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView ivImg;
        private Context context;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(Context context, View itemView) {
            super(itemView);
            this.ivImg = (ImageView) itemView.findViewById(R.id.imageView1);
            this.context = context;
            itemView.setOnClickListener(this);
        }

        // Handles the row being being clicked
        @Override
        public void onClick(View view) {
            int position = getLayoutPosition(); // gets item position
            Movie item = items.get(position);
            // We can access the data within the views
            //Toast.makeText(context, "click ", Toast.LENGTH_SHORT).show();
            Intent detailIntent = new Intent(context, MovieDetailActivity.class);
            detailIntent.putExtra("movie_id", position);
                detailIntent.putExtra("original_title", item.original_title);
                detailIntent.putExtra("release_date", item.release_date);
                detailIntent.putExtra("overview", item.overview);
                detailIntent.putExtra("vote_average", item.vote_average);
                detailIntent.putExtra("poster_path", item.poster_path);
            context.startActivity(detailIntent);


        }
    }
}
