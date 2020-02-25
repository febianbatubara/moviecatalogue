package com.example.favouritemoviesapp.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class MovieDatabaseContract {

    public static final String AUTHORITY = "com.example.moviecatalogue";
    private static final String SCHEME = "content";

    public static final class FavouriteMoviesColumns implements BaseColumns {
        public static final String TABLE_NAME = "favorite_movie";

        public static String TITLE = "title";
        public static String DESCRIPTION = "description";
        public static String RELEASE_DATE = "release_date";
        public static String POSTER = "poster";
        public static String POSTER_BACKGROUND = "poster_background";
        public static String VOTE_AVERAGE = "average";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static double getColumnDouble(Cursor cursor, String columnName) {
        return cursor.getDouble(cursor.getColumnIndex(columnName));
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

}
