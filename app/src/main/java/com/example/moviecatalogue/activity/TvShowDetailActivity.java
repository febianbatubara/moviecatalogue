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
import com.example.moviecatalogue.db.FavouriteTvShowsHelper;
import com.example.moviecatalogue.model.TvShow;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TvShowDetailActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_TV_SHOW = "extra_tv_show";
    private FloatingActionButton favButton;
    private FloatingActionButton favRemoveButton;
    private FavouriteTvShowsHelper favouriteTvShowsHelper;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.transition.fadein, R.transition.fadeout);
        setContentView(R.layout.activity_tv_show_detail);

        TvShow tvShow = getIntent().getParcelableExtra(EXTRA_TV_SHOW);
        String poster = "https://image.tmdb.org/t/p/w500" + tvShow.getPoster();
        String posterBg = "https://image.tmdb.org/t/p/w780" + tvShow.getPosterBg();
        String title = tvShow.getTitle();
        String desc = tvShow.getDescription();
        String relDate = tvShow.getReleaseDate();
        Double voteAverage = tvShow.getVoteAverage();

        setDetail(poster, posterBg, title, desc, relDate, voteAverage);

        favouriteTvShowsHelper = FavouriteTvShowsHelper.getInstance(getApplicationContext());
        favouriteTvShowsHelper.open();

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.tv_detail_title));
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.color_primary_gradient));
            actionBar.setElevation(0);
        }

        favButton = findViewById(R.id.fav_button);
        favButton.setOnClickListener(this);
        favRemoveButton = findViewById(R.id.fav_remove_button);
        favRemoveButton.setOnClickListener(this);

        if (favouriteTvShowsHelper.checkTvShow(title)) {
            favButton.setVisibility(View.GONE);
            favRemoveButton.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("ResourceAsColor")
    protected void setDetail(String poster, String posterBg, String title, String desc, String relDate, Double voteAverage) {
        ImageView detail_poster = findViewById(R.id.tv_item_poster);
        Glide.with(this)
                .asBitmap()
                .load(poster)
                .into(detail_poster);

        ImageView detail_posterBg = findViewById(R.id.tv_item_posterBg);
        Glide.with(this)
                .asBitmap()
                .load(posterBg)
                .placeholder(new ColorDrawable(Color.LTGRAY))
                .into(detail_posterBg);

        TextView detail_title = findViewById(R.id.detail_title);
        detail_title.setText(title);

        TextView detail_rel_date = findViewById(R.id.detail_rel_date);
        detail_rel_date.setText(relDate);

        TextView detail_desc = findViewById(R.id.detail_desc);
        detail_desc.setText(desc);

        TextView detail_vote_avg = findViewById(R.id.vote_average);
        detail_vote_avg.setText(voteAverage.toString());

        RatingBar mRatingBar = findViewById(R.id.rating);
        mRatingBar.setRating(voteAverage.floatValue() / 2);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View view) {
        TvShow selectedTvShow = getIntent().getParcelableExtra(EXTRA_TV_SHOW);
        if (view.getId() == R.id.fav_button) {
            long result = favouriteTvShowsHelper.insertTvShow(selectedTvShow);
            if (result > 0) {
                favButton.setVisibility(View.GONE);
                favRemoveButton.setVisibility(View.VISIBLE);
                Toast.makeText(TvShowDetailActivity.this, "Successfully added to favourite", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(TvShowDetailActivity.this, "Failed adding to favourite", Toast.LENGTH_SHORT).show();
            }

        } else if (view.getId() == R.id.fav_remove_button) {
            favouriteTvShowsHelper.deleteTvShow(selectedTvShow.getTitle());
            favRemoveButton.setVisibility(View.GONE);
            favButton.setVisibility(View.VISIBLE);
            Toast.makeText(TvShowDetailActivity.this, "Successfully removed from favourite", Toast.LENGTH_SHORT).show();
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
