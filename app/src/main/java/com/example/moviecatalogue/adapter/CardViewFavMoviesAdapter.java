package com.example.moviecatalogue.adapter;

import android.content.Intent;
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
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.activity.MovieDetailActivity;
import com.example.moviecatalogue.model.Movie;

import java.util.ArrayList;

public class CardViewFavMoviesAdapter extends RecyclerView.Adapter<CardViewFavMoviesAdapter.CardViewViewHolder> {
    private ArrayList<Movie> listMovie;

    public CardViewFavMoviesAdapter(ArrayList<Movie> list) {
        this.listMovie = list;
    }

    public ArrayList<Movie> getListMovie() {
        return listMovie;
    }

    public void setData(ArrayList<Movie> items) {
        listMovie.clear();
        listMovie.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CardViewFavMoviesAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_movie, parent, false);
        return new CardViewFavMoviesAdapter.CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewFavMoviesAdapter.CardViewViewHolder holder, int position) {
        Movie movie = listMovie.get(position);

        String urlImage = "https://image.tmdb.org/t/p/w500" + movie.getPoster();
        Glide.with(holder.itemView.getContext())
                .load(urlImage)
                .apply(new RequestOptions().override(350, 550))
                .placeholder(new ColorDrawable(Color.rgb(44, 83, 100)))
                .error(new ColorDrawable(Color.DKGRAY))
                .into(holder.imgPoster);
        holder.tvTitle.setText(movie.getTitle());
        holder.tvRelDate.setText(movie.getReleaseDate());
        holder.tvDesc.setText(movie.getDescription());
        holder.tvVoteAvg.setText(movie.getVoteAverage().toString());
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Movie movie = getListMovie().get(position);

            movie.setTitle(movie.getTitle());
            movie.setDescription(movie.getDescription());

            Intent moveWithObjectIntent = new Intent(itemView.getContext(), MovieDetailActivity.class);
            moveWithObjectIntent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
            itemView.getContext().startActivity(moveWithObjectIntent);
        }
    }
}
