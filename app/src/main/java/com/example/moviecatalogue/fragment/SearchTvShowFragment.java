package com.example.moviecatalogue.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.adapter.CardViewTvShowAdapter;
import com.example.moviecatalogue.model.TvShow;
import com.example.moviecatalogue.viewmodel.TvShowsViewModel;

import java.util.ArrayList;

public class SearchTvShowFragment extends Fragment {
    private RecyclerView rvTvShows;
    private ArrayList<TvShow> list = new ArrayList<>();
    private CardViewTvShowAdapter adapter;
    private ProgressBar progressBar;
    private TvShowsViewModel tvShowsViewModel;

    public SearchTvShowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rvTvShows = container.findViewById(R.id.rv_tv_shows);
        showRecyclerCardView();
        progressBar = container.findViewById(R.id.progressBar);
        showLoading(true);

        String query = getArguments().getString("query");
        tvShowsViewModel = ViewModelProviders.of(this).get(TvShowsViewModel.class);
        tvShowsViewModel.setSearchedTvShow(query);
        tvShowsViewModel.getTvShow().observe(getViewLifecycleOwner(), getListTvShows);

        return inflater.inflate(R.layout.fragment_search_movies, container, false);
    }

    private Observer<ArrayList<TvShow>> getListTvShows = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(ArrayList<TvShow> tvShows) {
            if (tvShows != null) {
                adapter.setData(tvShows);
            }
            else {
                Toast.makeText(getContext(), R.string.no_result, Toast.LENGTH_LONG).show();
            }
            showLoading(false);
        }
    };

    private void showRecyclerCardView() {
        rvTvShows.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter = new CardViewTvShowAdapter(list);
        rvTvShows.setAdapter(adapter);
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
