package com.example.moviecatalogue.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import static android.provider.BaseColumns._ID;
import static com.example.moviecatalogue.db.MovieDatabaseContract.FavouriteMoviesColumns.DESCRIPTION;
import static com.example.moviecatalogue.db.MovieDatabaseContract.FavouriteMoviesColumns.POSTER;
import static com.example.moviecatalogue.db.MovieDatabaseContract.FavouriteMoviesColumns.POSTER_BACKGROUND;
import static com.example.moviecatalogue.db.MovieDatabaseContract.FavouriteMoviesColumns.RELEASE_DATE;
import static com.example.moviecatalogue.db.MovieDatabaseContract.FavouriteMoviesColumns.TITLE;
import static com.example.moviecatalogue.db.MovieDatabaseContract.FavouriteMoviesColumns.VOTE_AVERAGE;
import static com.example.moviecatalogue.db.MovieDatabaseContract.getColumnDouble;
import static com.example.moviecatalogue.db.MovieDatabaseContract.getColumnInt;
import static com.example.moviecatalogue.db.MovieDatabaseContract.getColumnString;

public class Movie implements Parcelable {

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
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

    public Movie(JSONObject object) {
        try {
            String poster = object.getString("poster_path");
            String posterBg = object.getString("background_path");
            String title = object.getString("title");
            String releaseDate = object.getString("release_date");
            String description = object.getString("overview");
            Double voteAverage = object.getDouble("vote_average");
            int id = object.getInt("id");

            setId(id);
            setPoster(poster);
            setTitle(title);
            setPosterBg(posterBg);
            setDescription(description);
            setReleaseDate(releaseDate);
            setVoteAverage(voteAverage);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Movie() {

    }

    public Movie(int id, String title, String description, String releaseDate, String poster, String posterBackground, Double voteAvg) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPosterBg() {
        return posterBg;
    }

    public void setPosterBg(String posterBg) {
        this.posterBg = posterBg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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