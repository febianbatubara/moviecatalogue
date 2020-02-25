package com.example.favouritemoviesapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.favouritemoviesapp.R;
import com.example.favouritemoviesapp.db.MovieDatabaseContract;

public class CardViewMovieAdapter extends RecyclerView.Adapter<CardViewMovieAdapter.CardViewViewHolder> {
    private Cursor cursor;
    private Context context;

    public CardViewMovieAdapter(Context context) {
        this.context = context;
    }

    public void setData(Cursor cursor) {
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CardViewMovieAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_movie, parent, false);
        return new CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewViewHolder holder, int position) {
        holder.bindView(cursor.moveToPosition(position));
    }

    @Override
    public int getItemCount() {
        return cursor == null ? 0 : cursor.getCount();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder{
        ImageView imgPoster;
        TextView tvTitle;
        TextView tvRelDate;
        TextView tvDesc;
        TextView tvVoteAvg;

        CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster);
            tvTitle = itemView.findViewById(R.id.txt_title);
            tvRelDate = itemView.findViewById(R.id.txt_rel_date);
            tvDesc = itemView.findViewById(R.id.txt_description);
            tvVoteAvg = itemView.findViewById(R.id.txt_vote_avg);
        }

        public void bindView(boolean position) {
            if (position) {
                tvTitle.setText(cursor.getString(cursor.getColumnIndexOrThrow(MovieDatabaseContract.FavouriteMoviesColumns.TITLE)));
                tvDesc.setText(cursor.getString(cursor.getColumnIndexOrThrow(MovieDatabaseContract.FavouriteMoviesColumns.DESCRIPTION)));
                tvRelDate.setText(cursor.getString(cursor.getColumnIndexOrThrow(MovieDatabaseContract.FavouriteMoviesColumns.RELEASE_DATE)));
                tvVoteAvg.setText(cursor.getString(cursor.getColumnIndexOrThrow(MovieDatabaseContract.FavouriteMoviesColumns.VOTE_AVERAGE)));
                String posterPath = cursor.getString(cursor.getColumnIndexOrThrow(MovieDatabaseContract.FavouriteMoviesColumns.POSTER));

                String urlImage = "https://image.tmdb.org/t/p/w500" + posterPath;
                Glide.with(itemView.getContext())
                        .load(urlImage)
                        .apply(new RequestOptions().override(350, 550))
                        .placeholder(new ColorDrawable(Color.rgb(44, 83, 100)))
                        .error(new ColorDrawable(Color.DKGRAY))
                        .into(imgPoster);
            }
        }

    }
}
