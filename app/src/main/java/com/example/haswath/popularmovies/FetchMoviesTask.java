package com.example.haswath.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.haswath.popularmovies.dummy.DummyContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by haswath on 7/10/15.
 */
public class FetchMoviesTask extends AsyncTask<Void, Void, JSONObject[]> {

    private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();
    private final CustomAdapter adapter;
    private Context context;
    private String sortBy = "popularity.desc";

    public FetchMoviesTask(Context context, String sort, CustomAdapter adapter) {
        this.context = context;
        this.sortBy = sort;
        this.adapter = adapter;
    }

    /**
     * Take the String representing the complete forecast in JSON Format and
     * pull out the data we need to construct the Strings needed for the wireframes.
     * <p/>
     * Fortunately parsing is easy:  constructor takes the JSON string and converts it
     * into an Object hierarchy for us.
     */
    private JSONObject[] getMovieDataFromJson(String moviesJsonStr)
            throws JSONException {

        // These are the names of the JSON objects that need to be extracted.
        final String OWM_LIST = "results";

        JSONObject moviesJson = new JSONObject(moviesJsonStr);
        JSONArray movies = moviesJson.getJSONArray(OWM_LIST);

        JSONObject[] resultStrs = new JSONObject[movies.length()];
        for (int i = 0; i < movies.length(); i++) {
            // Get the JSON object representing the day
            JSONObject movie = movies.getJSONObject(i);

            resultStrs[i] = movie;
        }
        return resultStrs;

    }

    @Override
    protected JSONObject[] doInBackground(Void... params) {

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String moviesJsonStr = null;

        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            final String FORECAST_BASE_URL =
                    "https://api.themoviedb.org/3/discover/movie?";
            final String SORTBY_PARAM = "sort_by";
            final String APIKEY_PARAM = "api_key";

            Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                    .appendQueryParameter(SORTBY_PARAM, sortBy)
                    .appendQueryParameter(APIKEY_PARAM, "e95feea469573da8277773d047559d1a")
                    .build();

            URL url = new URL(builtUri.toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            moviesJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        try {
            return getMovieDataFromJson(moviesJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        // This will only happen if there was an error getting or parsing the forecast.
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject[] result) {
        if (result != null) {
            int i = 0;
            for (JSONObject jsonObject : result) {
                Log.i(LOG_TAG, "result " + jsonObject);
//                    Picasso.with(context).load(url).into(imageView);
                String id = String.valueOf(i);
                try {
                    String originalTitle = jsonObject.getString("original_title");
                    MovieData item = new MovieData(id, jsonObject);
                    adapter.add(item);
                    DummyContent.ITEM_MAP.put(id, item);
                    DummyContent.ITEMS.add(item);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                i++;
            }
            // New data is back from the server.  Hooray!
        }
    }
}
