package com.example.moviecatalogue.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.moviecatalogue.model.Movie;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static android.provider.MediaStore.Audio.Playlists.Members._ID;
import static com.example.moviecatalogue.db.MovieDatabaseContract.FavouriteMoviesColumns.DESCRIPTION;
import static com.example.moviecatalogue.db.MovieDatabaseContract.FavouriteMoviesColumns.POSTER;
import static com.example.moviecatalogue.db.MovieDatabaseContract.FavouriteMoviesColumns.POSTER_BACKGROUND;
import static com.example.moviecatalogue.db.MovieDatabaseContract.FavouriteMoviesColumns.RELEASE_DATE;
import static com.example.moviecatalogue.db.MovieDatabaseContract.FavouriteMoviesColumns.TITLE;
import static com.example.moviecatalogue.db.MovieDatabaseContract.FavouriteMoviesColumns.VOTE_AVERAGE;
import static com.example.moviecatalogue.db.MovieDatabaseContract.TABLE_NAME;

public class FavouriteMoviesHelper {

    private static final String DATABASE_TABLE = TABLE_NAME;
    private static MovieDatabaseHelper dataBaseHelperMovie;
    private static FavouriteMoviesHelper INSTANCE;
    private static SQLiteDatabase database;

    public FavouriteMoviesHelper(Context context){
        dataBaseHelperMovie = new MovieDatabaseHelper(context);
    }

    public FavouriteMoviesHelper() {

    }

    public static FavouriteMoviesHelper getInstance(Context context){
        if(INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new FavouriteMoviesHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelperMovie.getWritableDatabase();
    }
    public void close() {
        dataBaseHelperMovie.close();

        if (database.isOpen())
            database.close();
    }

    public ArrayList<Movie> getAllMovies() {
        ArrayList<Movie> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        Movie movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new Movie();
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movie.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                movie.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                movie.setPosterBg(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_BACKGROUND)));
                movie.setVoteAverage(cursor.getDouble(cursor.getColumnIndexOrThrow(VOTE_AVERAGE)));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));

                arrayList.add(movie);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertMovie (Movie values) {
        ContentValues args = new ContentValues();
        args.put(TITLE, values.getTitle());
        args.put(DESCRIPTION, values.getDescription());
        args.put(RELEASE_DATE, values.getReleaseDate());
        args.put(POSTER, values.getPoster());
        args.put(POSTER_BACKGROUND, values.getPosterBg());
        args.put(VOTE_AVERAGE, values.getVoteAverage());

        return database.insert(DATABASE_TABLE, null, args);
    }


    public int deleteMovieById(String title) {
        return database.delete(DATABASE_TABLE, TITLE + " = ?", new String[]{title});
    }

    public boolean checkMovie(String title) {
        database = dataBaseHelperMovie.getWritableDatabase();
        String selectString = "SELECT * FROM " + MovieDatabaseContract.FavouriteMoviesColumns.TABLE_NAME + " WHERE " + TITLE + " =?";
        Cursor cursor = database.rawQuery(selectString, new String[]{title});
        boolean checkMovie = false;
        if (cursor.moveToFirst()) {
            checkMovie = true;
            int count = 0;
            while (cursor.moveToNext()) {
                count++;
            }
            Log.d(TAG, String.format("%d records found", count));
        }
        cursor.close();
        return checkMovie;
    }

    public long insertProvider(ContentValues contentValues){
        return database.insert(DATABASE_TABLE, null, contentValues);
    }

    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE,null, BaseColumns._ID+"=?",new String[]{id},null,null,null,null);
    }

    public int updateProvider(String id, ContentValues contentValues){
        return database.update(DATABASE_TABLE, contentValues, BaseColumns._ID+"=?",new String[]{id});
    }

    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE, BaseColumns._ID+"=?",new String[]{id});
    }

    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE, null,null,null,null,null, BaseColumns._ID+" DESC");
    }
}
