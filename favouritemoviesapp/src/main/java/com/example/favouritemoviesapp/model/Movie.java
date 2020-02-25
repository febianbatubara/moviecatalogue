package com.example.favouritemoviesapp.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static android.provider.BaseColumns._ID;
import static com.example.favouritemoviesapp.db.MovieDatabaseContract.FavouriteMoviesColumns.DESCRIPTION;
import static com.example.favouritemoviesapp.db.MovieDatabaseContract.FavouriteMoviesColumns.POSTER;
import static com.example.favouritemoviesapp.db.MovieDatabaseContract.FavouriteMoviesColumns.POSTER_BACKGROUND;
import static com.example.favouritemoviesapp.db.MovieDatabaseContract.FavouriteMoviesColumns.RELEASE_DATE;
import static com.example.favouritemoviesapp.db.MovieDatabaseContract.FavouriteMoviesColumns.TITLE;
import static com.example.favouritemoviesapp.db.MovieDatabaseContract.FavouriteMoviesColumns.VOTE_AVERAGE;
import static com.example.favouritemoviesapp.db.MovieDatabaseContract.getColumnDouble;
import static com.example.favouritemoviesapp.db.MovieDatabaseContract.getColumnInt;
import static com.example.favouritemoviesapp.db.MovieDatabaseContract.getColumnString;

public class Movie implements Parcelable {

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    private int id;
    private String poster;
    private String posterBg;
    private String title;
    private String releaseDate;
    private String description;
    private Double voteAverage;


    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.releaseDate = in.readString();
        this.description = in.readString();
        this.poster = in.readString();
        this.posterBg = in.readString();
        this.voteAverage = in.readDouble();
    }

    public Movie(Cursor cursor){
        this.id = getColumnInt(cursor, _ID);
        this.title = getColumnString(cursor, TITLE);
        this.releaseDate = getColumnString(cursor, RELEASE_DATE);
        this.description = getColumnString(cursor, DESCRIPTION);
        this.poster = getColumnString(cursor, POSTER);
        this.posterBg = getColumnString(cursor, POSTER_BACKGROUND);
        this.voteAverage = getColumnDouble(cursor, VOTE_AVERAGE);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.title);
        parcel.writeString(this.releaseDate);
        parcel.writeString(this.description);
        parcel.writeString(this.poster);
        parcel.writeString(this.posterBg);
        parcel.writeDouble(this.voteAverage);
    }

}