package com.example.moviecatalogue.db;

import android.provider.BaseColumns;

public class TvShowDatabaseContract {

    public static final String TABLE_NAME = "favorite_tv_show";

    static final class FavouriteTvShowsColumns implements BaseColumns {
        static final String TABLE_NAME = "favorite_tv_show";

        static String TITLE = "title";
        static String DESCRIPTION = "description";
        static String RELEASE_DATE = "release_date";
        static String POSTER = "poster";
        static String POSTER_BACKGROUND = "poster_background";
        static String VOTE_AVERAGE = "average";
    }

}
