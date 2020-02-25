package com.example.moviecatalogue.db;

import android.database.Cursor;

import com.example.moviecatalogue.model.Movie;

import java.util.ArrayList;

public class MappingHelper {
    public static ArrayList<Movie> mapCursorToArrayList(Cursor moviesCursor) {
        ArrayList<Movie> movieList = new ArrayList<>();

        while (moviesCursor.moveToNext()) {
            int id = moviesCursor.getInt(moviesCursor.getColumnIndexOrThrow(MovieDatabaseContract.FavouriteMoviesColumns._ID));
            String title = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(MovieDatabaseContract.FavouriteMoviesColumns.TITLE));
            String description = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(MovieDatabaseContract.FavouriteMoviesColumns.DESCRIPTION));
            String releaseDate = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(MovieDatabaseContract.FavouriteMoviesColumns.RELEASE_DATE));
            String poster = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(MovieDatabaseContract.FavouriteMoviesColumns.POSTER));
            String posterBackground = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(MovieDatabaseContract.FavouriteMoviesColumns.POSTER_BACKGROUND));
            Double voteAvg = moviesCursor.getDouble(moviesCursor.getColumnIndexOrThrow(MovieDatabaseContract.FavouriteMoviesColumns.VOTE_AVERAGE));
            movieList.add(new Movie(id, title, description, releaseDate, poster, posterBackground, voteAvg));
        }

        return movieList;
    }

    public static Movie mapCursorToObject(Cursor moviesCursor) {
        moviesCursor.moveToFirst();
        int id = moviesCursor.getInt(moviesCursor.getColumnIndexOrThrow(MovieDatabaseContract.FavouriteMoviesColumns._ID));
        String title = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(MovieDatabaseContract.FavouriteMoviesColumns.TITLE));
        String description = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(MovieDatabaseContract.FavouriteMoviesColumns.DESCRIPTION));
        String releaseDate = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(MovieDatabaseContract.FavouriteMoviesColumns.RELEASE_DATE));
        String poster = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(MovieDatabaseContract.FavouriteMoviesColumns.POSTER));
        String posterBackground = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(MovieDatabaseContract.FavouriteMoviesColumns.POSTER_BACKGROUND));
        Double voteAvg = moviesCursor.getDouble(moviesCursor.getColumnIndexOrThrow(MovieDatabaseContract.FavouriteMoviesColumns.VOTE_AVERAGE));

        return new Movie(id, title, description, releaseDate, poster, posterBackground, voteAvg);
    }
}
