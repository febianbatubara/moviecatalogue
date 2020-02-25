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
import com.example.moviecatalogue.activity.TvShowDetailActivity;
import com.example.moviecatalogue.model.TvShow;

import java.util.ArrayList;

public class CardViewTvShowAdapter extends RecyclerView.Adapter<CardViewTvShowAdapter.CardViewViewHolder> {
    private ArrayList<TvShow> listTvShow;

    public CardViewTvShowAdapter(ArrayList<TvShow> list) {
        this.listTvShow = list;
    }

    public ArrayList<TvShow> getListTvShow() {
        return listTvShow;
    }

    public void setData(ArrayList<TvShow> items) {
        listTvShow.clear();
        listTvShow.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CardViewTvShowAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_tv_show, parent, false);
        return new CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewViewHolder holder, int position) {
        TvShow tvShow = listTvShow.get(position);

        String urlImage = "https://image.tmdb.org/t/p/w500" + tvShow.getPoster();
        Glide.with(holder.itemView.getContext())
                .load(urlImage)
                .apply(new RequestOptions().override(350, 550))
                .placeholder(new ColorDrawable(Color.rgb(44, 83, 100)))
                .error(new ColorDrawable(Color.DKGRAY))
                .into(holder.imgPoster);
        holder.tvTitle.setText(tvShow.getTitle());
        holder.tvRelDate.setText(tvShow.getReleaseDate());
        holder.tvDesc.setText(tvShow.getDescription());
        holder.tvVoteAvg.setText(tvShow.getVoteAverage().toString());
    }

    @Override
    public int getItemCount() {
        return listTvShow.size();
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
            TvShow tvShow = getListTvShow().get(position);

            tvShow.setTitle(tvShow.getTitle());
            tvShow.setDescription(tvShow.getDescription());

            Intent moveWithObjectIntent = new Intent(itemView.getContext(), TvShowDetailActivity.class);
            moveWithObjectIntent.putExtra(TvShowDetailActivity.EXTRA_TV_SHOW, tvShow);
            itemView.getContext().startActivity(moveWithObjectIntent);
        }
    }

}
