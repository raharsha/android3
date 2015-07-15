package com.example.haswath.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by haswath on 7/14/15.
 */
public class Movie implements Parcelable {

    String original_title;
    String poster_path;
    String release_date;
    String overview;
    String vote_average;

    public Movie() {
    }

    public Movie(com.uwetrottmann.tmdb.entities.Movie movie) {
        this.original_title = movie.original_title;
        this.poster_path = movie.poster_path;
        this.release_date = movie.release_date.toString();
        this.overview = movie.overview;
        this.vote_average = movie.vote_average.toString();

    }


    private Movie(Parcel in) {
        original_title=in.readString();
        poster_path=in.readString();
        release_date=in.readString();
        overview=in.readString();
        vote_average=in.readString();
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(original_title);
            dest.writeString(poster_path);
            dest.writeString(release_date);
            dest.writeString(overview);
            dest.writeString(vote_average);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

}
