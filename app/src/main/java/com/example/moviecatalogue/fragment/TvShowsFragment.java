package com.example.moviecatalogue.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviecatalogue.adapter.CardViewTvShowAdapter;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.model.TvShow;
import com.example.moviecatalogue.viewmodel.TvShowsViewModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowsFragment extends Fragment {
    private RecyclerView rvTvShows;
    private ArrayList<TvShow> list = new ArrayList<>();
    private CardViewTvShowAdapter adapter;
    private ProgressBar progressBar;
    private TvShowsViewModel tvShowsViewModel;

    private Observer<ArrayList<TvShow>> getListTvShows = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(ArrayList<TvShow> tvShows) {
            if (tvShows != null) {
                adapter.setData(tvShows);
            }
            showLoading(false);
        }
    };


    public TvShowsFragment() {
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

        progressBar = view.findViewById(R.id.progressBar);

        tvShowsViewModel = ViewModelProviders.of(this).get(TvShowsViewModel.class);
        tvShowsViewModel.getTvShow().observe(getViewLifecycleOwner(), getListTvShows);
        tvShowsViewModel.setTvShow("EXTRA_TVSHOW");
        showLoading(true);
        showRecyclerCardView();
    }

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
