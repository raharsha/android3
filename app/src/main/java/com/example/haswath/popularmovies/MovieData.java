package com.example.haswath.popularmovies;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A representing a piece of content.
 */
public class MovieData {
    public String id;
    public JSONObject content;

    public MovieData(String id, JSONObject content) {
        this.id = id;
        this.content = content;
    }

}
