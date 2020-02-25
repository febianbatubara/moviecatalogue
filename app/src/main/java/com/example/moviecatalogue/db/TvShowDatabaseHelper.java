package com.example.moviecatalogue.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class TvShowDatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbtvshowcatalogue";

    private static final int DATABASE_VERSION = 2;

    private static final String SQL_CREATE_TABLE_FAVOURITE_TV_SHOWS = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            TvShowDatabaseContract.TABLE_NAME,
            TvShowDatabaseContract.FavouriteTvShowsColumns._ID,
            TvShowDatabaseContract.FavouriteTvShowsColumns.TITLE,
            TvShowDatabaseContract.FavouriteTvShowsColumns.DESCRIPTION,
            TvShowDatabaseContract.FavouriteTvShowsColumns.RELEASE_DATE,
            TvShowDatabaseContract.FavouriteTvShowsColumns.POSTER,
            TvShowDatabaseContract.FavouriteTvShowsColumns.POSTER_BACKGROUND,
            TvShowDatabaseContract.FavouriteTvShowsColumns.VOTE_AVERAGE
    );

    public TvShowDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FAVOURITE_TV_SHOWS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TvShowDatabaseContract.FavouriteTvShowsColumns.TABLE_NAME);
        onCreate(db);
    }
}
