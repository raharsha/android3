package com.example.haswath.popularmovies;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;


public class MovieDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        if (getIntent().getExtras().containsKey("movie_id")) {
            Bundle extras = getIntent().getExtras();
            ImageView iv = ((ImageView) findViewById(R.id.imageView));
            String url = "http://image.tmdb.org/t/p/w185" + extras.getString("poster_path");
            Picasso.with(getApplicationContext()).load(url).into(iv);
            TextView tv = ((TextView) findViewById(R.id.textView));
            tv.setText(extras.getString("original_title"));
            tv = ((TextView) findViewById(R.id.textView2));

            Date release_date = (Date) extras.get("release_date");

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(release_date);
            tv.setText(String.valueOf(calendar.get(Calendar.YEAR)));
            tv = ((TextView) findViewById(R.id.tvOverview));
            tv.setText(extras.getString("overview"));
            tv = ((TextView) findViewById(R.id.tvRating));
            tv.setText(extras.getString("vote_average"));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_detail, menu);
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
}
