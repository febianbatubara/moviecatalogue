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
import com.example.moviecatalogue.adapter.CardViewFavMoviesAdapter;
import com.example.moviecatalogue.db.FavouriteMoviesHelper;
import com.example.moviecatalogue.model.Movie;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavMoviesFragment extends Fragment {
    private RecyclerView rvMovies;
    private ArrayList<Movie> list = new ArrayList<>();
    private CardViewFavMoviesAdapter adapter;
    private FavouriteMoviesHelper favouriteMoviesHelper;
    private TextView emptyView;

    public FavMoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        rvMovies = view.findViewById(R.id.rv_movies);
        rvMovies.setHasFixedSize(true);
        emptyView = view.findViewById(R.id.empty_view);

        favouriteMoviesHelper = FavouriteMoviesHelper.getInstance(view.getContext());
        favouriteMoviesHelper.open();

        showRecyclerCardView();
    }

    private void showRecyclerCardView() {
        rvMovies.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter = new CardViewFavMoviesAdapter(list);
        rvMovies.setAdapter(adapter);

        if(favouriteMoviesHelper.getAllMovies().isEmpty() ){
            emptyView.setVisibility(View.VISIBLE);
        }
        else{
            emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        list = favouriteMoviesHelper.getAllMovies();
        adapter.setData(list);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        favouriteMoviesHelper.close();
    }

}
