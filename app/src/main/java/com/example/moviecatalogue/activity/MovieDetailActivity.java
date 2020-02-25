package com.example.moviecatalogue.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.db.FavouriteMoviesHelper;
import com.example.moviecatalogue.model.Movie;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_MOVIE = "extra_movie";
    private FloatingActionButton favButton;
    private FloatingActionButton favRemoveButton;
    private Movie movie;
    private FavouriteMoviesHelper favouriteMoviesHelper;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.transition.fadein, R.transition.fadeout);
        setContentView(R.layout.activity_movie_detail);

        favouriteMoviesHelper = FavouriteMoviesHelper.getInstance(getApplicationContext());
        favouriteMoviesHelper.open();

        movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        ImageView detailPoster = findViewById(R.id.tv_item_poster);
        ImageView detailPosterBg = findViewById(R.id.tv_item_posterBg);
        TextView detailTitle = findViewById(R.id.detail_title);
        TextView detailRelDate = findViewById(R.id.detail_rel_date);
        TextView detailDesc = findViewById(R.id.detail_desc);
        TextView detailVoteAvg = findViewById(R.id.vote_average);
        RatingBar mRatingBar = findViewById(R.id.rating);

        String poster = "https://image.tmdb.org/t/p/w500" + movie.getPoster();
        String posterBg = "https://image.tmdb.org/t/p/w780" + movie.getPosterBg();
        String title = movie.getTitle();
        String desc = movie.getDescription();
        String relDate = movie.getReleaseDate();
        Double voteAverage = movie.getVoteAverage();

        Glide.with(this)
                .asBitmap()
                .load(poster)
                .into(detailPoster);

        Glide.with(this)
                .asBitmap()
                .load(posterBg)
                .placeholder(new ColorDrawable(Color.LTGRAY))
                .into(detailPosterBg);

        detailTitle.setText(title);
        detailRelDate.setText(relDate);
        detailDesc.setText(desc);
        detailVoteAvg.setText(voteAverage.toString());
        mRatingBar.setRating(voteAverage.floatValue()/2);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.movie_detail_title));
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.color_primary_gradient));
            actionBar.setElevation(0);
        }

        favButton = findViewById(R.id.fav_button);
        favButton.setOnClickListener(this);
        favRemoveButton = findViewById(R.id.fav_remove_button);
        favRemoveButton.setOnClickListener(this);

        if (favouriteMoviesHelper.checkMovie(title)) {
            favButton.setVisibility(View.GONE);
            favRemoveButton.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View view) {
        Movie selectedMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        if (view.getId() == R.id.fav_button){
            long result = favouriteMoviesHelper.insertMovie(selectedMovie);
            if (result > 0) {
                favButton.setVisibility(View.GONE);
                favRemoveButton.setVisibility(View.VISIBLE);
                Toast.makeText(MovieDetailActivity.this, "Successfully added to favourite", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MovieDetailActivity.this, "Failed adding to favourite", Toast.LENGTH_SHORT).show();
            }

        }
        else if (view.getId() == R.id.fav_remove_button){
            favouriteMoviesHelper.deleteMovieById(selectedMovie.getTitle());
            favRemoveButton.setVisibility(View.GONE);
            favButton.setVisibility(View.VISIBLE);
            Toast.makeText(MovieDetailActivity.this, "Successfully removed from favourite", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                overridePendingTransition(R.transition.fadein, R.transition.fadeout);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.transition.fadein, R.transition.fadeout);
    }
}
