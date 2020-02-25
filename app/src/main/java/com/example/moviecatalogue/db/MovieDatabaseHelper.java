package com.example.moviecatalogue.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MovieDatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbmoviecatalogue";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_FAVOURITE_MOVIES = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            MovieDatabaseContract.TABLE_NAME,
            MovieDatabaseContract.FavouriteMoviesColumns._ID,
            MovieDatabaseContract.FavouriteMoviesColumns.TITLE,
            MovieDatabaseContract.FavouriteMoviesColumns.DESCRIPTION,
            MovieDatabaseContract.FavouriteMoviesColumns.RELEASE_DATE,
            MovieDatabaseContract.FavouriteMoviesColumns.POSTER,
            MovieDatabaseContract.FavouriteMoviesColumns.POSTER_BACKGROUND,
            MovieDatabaseContract.FavouriteMoviesColumns.VOTE_AVERAGE
    );

    public MovieDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FAVOURITE_MOVIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieDatabaseContract.FavouriteMoviesColumns.TABLE_NAME);
        onCreate(db);
    }
}
