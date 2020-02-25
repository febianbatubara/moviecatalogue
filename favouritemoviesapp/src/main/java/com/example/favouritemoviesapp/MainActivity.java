package com.example.favouritemoviesapp;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.favouritemoviesapp.adapter.CardViewMovieAdapter;
import com.example.favouritemoviesapp.db.MovieDatabaseContract;

import static com.example.favouritemoviesapp.R.id.rv_movies;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private RecyclerView rvMovies;
    private CardViewMovieAdapter adapter;
    private static final int CODE_MOVIE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvMovies = findViewById(rv_movies);
        rvMovies.setHasFixedSize(true);
        showRecyclerCardView();
        getSupportLoaderManager().initLoader(CODE_MOVIE, null, this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Consumer Favourite Movies");
        }
    }

    private void showRecyclerCardView() {
        adapter = new CardViewMovieAdapter(getApplicationContext());
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        rvMovies.setAdapter(adapter);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int position, @Nullable Bundle bundle) {
        switch (position) {
            case CODE_MOVIE:
                return new CursorLoader(getApplicationContext(), MovieDatabaseContract.FavouriteMoviesColumns.CONTENT_URI, null, null, null, null);
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
            adapter.setData(null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        if (loader.getId() == CODE_MOVIE) {
            try {
                adapter.setData(cursor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
