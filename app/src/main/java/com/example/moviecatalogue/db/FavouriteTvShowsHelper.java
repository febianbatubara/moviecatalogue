package com.example.moviecatalogue.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.moviecatalogue.model.TvShow;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static android.provider.MediaStore.Audio.Playlists.Members._ID;
import static com.example.moviecatalogue.db.TvShowDatabaseContract.FavouriteTvShowsColumns.DESCRIPTION;
import static com.example.moviecatalogue.db.TvShowDatabaseContract.FavouriteTvShowsColumns.POSTER;
import static com.example.moviecatalogue.db.TvShowDatabaseContract.FavouriteTvShowsColumns.POSTER_BACKGROUND;
import static com.example.moviecatalogue.db.TvShowDatabaseContract.FavouriteTvShowsColumns.RELEASE_DATE;
import static com.example.moviecatalogue.db.TvShowDatabaseContract.FavouriteTvShowsColumns.TITLE;
import static com.example.moviecatalogue.db.TvShowDatabaseContract.FavouriteTvShowsColumns.VOTE_AVERAGE;
import static com.example.moviecatalogue.db.TvShowDatabaseContract.TABLE_NAME;

public class FavouriteTvShowsHelper {

    private static final String DATABASE_TABLE = TABLE_NAME;
    private static TvShowDatabaseHelper dataBaseHelperTvShow;
    private static FavouriteTvShowsHelper INSTANCE;
    private static SQLiteDatabase database;

    public FavouriteTvShowsHelper(Context context){
        dataBaseHelperTvShow = new TvShowDatabaseHelper(context);
    }

    public FavouriteTvShowsHelper() {

    }

    public static FavouriteTvShowsHelper getInstance(Context context){
        if(INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new FavouriteTvShowsHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelperTvShow.getWritableDatabase();
    }
    public void close() {
        dataBaseHelperTvShow.close();

        if (database.isOpen())
            database.close();
    }

    public ArrayList<TvShow> getAllTvShows() {
        ArrayList<TvShow> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        TvShow tvShow;
        if (cursor.getCount() > 0) {
            do {
                tvShow = new TvShow();
                tvShow.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                tvShow.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                tvShow.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                tvShow.setPosterBg(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_BACKGROUND)));
                tvShow.setVoteAverage(cursor.getDouble(cursor.getColumnIndexOrThrow(VOTE_AVERAGE)));
                tvShow.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));

                arrayList.add(tvShow);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertTvShow (TvShow values) {
        ContentValues args = new ContentValues();
        args.put(TITLE, values.getTitle());
        args.put(DESCRIPTION, values.getDescription());
        args.put(RELEASE_DATE, values.getReleaseDate());
        args.put(POSTER, values.getPoster());
        args.put(POSTER_BACKGROUND, values.getPosterBg());
        args.put(VOTE_AVERAGE, values.getVoteAverage());

        return database.insert(DATABASE_TABLE, null, args);
    }


    public int deleteTvShow(String title) {
        return database.delete(DATABASE_TABLE, TITLE + " = ?", new String[]{title});
    }

    public boolean checkTvShow(String title) {
        database = dataBaseHelperTvShow.getWritableDatabase();
        String selectString = "SELECT * FROM " + TvShowDatabaseContract.FavouriteTvShowsColumns.TABLE_NAME + " WHERE " + TITLE + " =?";
        Cursor cursor = database.rawQuery(selectString, new String[]{title});
        boolean checkTvShow = false;
        if (cursor.moveToFirst()) {
            checkTvShow = true;
            int count = 0;
            while (cursor.moveToNext()) {
                count++;
            }
            Log.d(TAG, String.format("%d records found", count));
        }
        cursor.close();
        return checkTvShow;
    }

}
