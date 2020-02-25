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
import com.example.moviecatalogue.adapter.CardViewMovieAdapter;
import com.example.moviecatalogue.model.Movie;
import com.example.moviecatalogue.viewmodel.MoviesViewModel;

import java.util.ArrayList;

public class SearchMovieFragment extends Fragment {
    private RecyclerView rvMovies;
    private ArrayList<Movie> list = new ArrayList<>();
    private CardViewMovieAdapter adapter;
    private ProgressBar progressBar;

    public SearchMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rvMovies = container.findViewById(R.id.rv_movies);
        showRecyclerCardView();
        progressBar = container.findViewById(R.id.progressBar);
        showLoading(true);

        String query = getArguments().getString("query");
        MoviesViewModel moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        moviesViewModel.setSearchedMovie(query);
        moviesViewModel.getMovie().observe(getViewLifecycleOwner(), getListMovies);

        return inflater.inflate(R.layout.fragment_search_movies, container, false);
    }

    private Observer<ArrayList<Movie>> getListMovies = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movies) {
            if (movies != null) {
                adapter.setData(movies);
            }
            else {
                Toast.makeText(getContext(), R.string.no_result, Toast.LENGTH_LONG).show();
            }
            showLoading(false);
        }
    };

    private void showRecyclerCardView() {
        rvMovies.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter = new CardViewMovieAdapter(list);
        rvMovies.setAdapter(adapter);
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
