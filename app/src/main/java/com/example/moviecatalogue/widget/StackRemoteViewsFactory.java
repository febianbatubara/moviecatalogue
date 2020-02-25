package com.example.moviecatalogue.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.db.FavouriteMoviesHelper;
import com.example.moviecatalogue.db.MovieDatabaseHelper;
import com.example.moviecatalogue.model.Movie;

import java.util.ArrayList;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final ArrayList<Movie> mWidgetItems = new ArrayList<>();
    private FavouriteMoviesHelper favouriteMoviesHelper = new FavouriteMoviesHelper();
    private final Context mContext;
    private static MovieDatabaseHelper dataBaseHelperMovie;

    StackRemoteViewsFactory(Context context) {
        dataBaseHelperMovie = new MovieDatabaseHelper(context);
        mContext = context;
    }

    @Override
    public void onCreate() {
        if (dataBaseHelperMovie != null){
            favouriteMoviesHelper.open();
        }
    }

    @Override
    public void onDataSetChanged() {
        mWidgetItems.clear();
        mWidgetItems.addAll(favouriteMoviesHelper.getAllMovies());
    }

    @Override
    public void onDestroy() {
        favouriteMoviesHelper.close();
    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        favouriteMoviesHelper.open();
        Movie itemMovie = mWidgetItems.get(position);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        String urlImage = "https://image.tmdb.org/t/p/w500" + itemMovie.getPoster();
        try {
            Bitmap bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(urlImage)
                    .apply(new RequestOptions().fitCenter())
                    .submit(800, 550)
                    .get();

            rv.setImageViewBitmap(R.id.imageView, bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bundle extras = new Bundle();
        extras.putInt(FavouriteWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

}
