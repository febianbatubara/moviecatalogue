package com.example.moviecatalogue.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.adapter.CardViewFavTvShowsAdapter;
import com.example.moviecatalogue.db.FavouriteTvShowsHelper;
import com.example.moviecatalogue.model.TvShow;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavTvShowsFragment extends Fragment {
    private RecyclerView rvTvShows;
    private ArrayList<TvShow> list = new ArrayList<>();
    private CardViewFavTvShowsAdapter adapter;
    private FavouriteTvShowsHelper favouriteTvShowsHelper;
    private TextView emptyView;

    public FavTvShowsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_tv_shows, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        rvTvShows = view.findViewById(R.id.rv_tv_shows);
        rvTvShows.setHasFixedSize(true);
        emptyView = view.findViewById(R.id.empty_view);

        favouriteTvShowsHelper = FavouriteTvShowsHelper.getInstance(view.getContext());
        favouriteTvShowsHelper.open();

        showRecyclerCardView();
    }

    private void showRecyclerCardView() {
        rvTvShows.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter = new CardViewFavTvShowsAdapter(list);
        rvTvShows.setAdapter(adapter);

        if(favouriteTvShowsHelper.getAllTvShows().isEmpty() ){
            emptyView.setVisibility(View.VISIBLE);
        }
        else{
            emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        list = favouriteTvShowsHelper.getAllTvShows();
        adapter.setData(list);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        favouriteTvShowsHelper.close();
    }

}
