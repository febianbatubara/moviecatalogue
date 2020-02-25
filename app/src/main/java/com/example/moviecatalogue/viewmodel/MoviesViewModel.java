package com.example.moviecatalogue.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviecatalogue.model.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MoviesViewModel extends ViewModel {
    private static final String API_KEY = "548278649ac84d14d07a3bce676eb02e";
    private MutableLiveData<ArrayList<Movie>> listMovies = new MutableLiveData<>();

    public LiveData<ArrayList<Movie>> getMovie() {
        return listMovies;
    }

    public void setMovie(final String movie) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listItems = new ArrayList<>();
        String url = "http://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject moviee = list.getJSONObject(i);
                        Movie movieItems = new Movie(moviee);
                        movieItems.setId(moviee.getInt("id"));
                        movieItems.setPoster(moviee.getString("poster_path"));
                        movieItems.setTitle(moviee.getString("title"));
                        movieItems.setReleaseDate(moviee.getString("release_date"));
                        movieItems.setDescription(moviee.getString("overview"));
                        movieItems.setPosterBg(moviee.getString("backdrop_path"));
                        movieItems.setVoteAverage(moviee.getDouble("vote_average"));
                        listItems.add(movieItems);
                    }
                    listMovies.postValue(listItems);
                } catch (JSONException e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    public void setSearchedMovie(String title){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&language=en-US&query=" + title;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject moviee = list.getJSONObject(i);
                        Movie movieItems = new Movie(moviee);
                        movieItems.setId(moviee.getInt("id"));
                        movieItems.setPoster(moviee.getString("poster_path"));
                        movieItems.setTitle(moviee.getString("title"));
                        movieItems.setReleaseDate(moviee.getString("release_date"));
                        movieItems.setDescription(moviee.getString("overview"));
                        movieItems.setPosterBg(moviee.getString("backdrop_path"));
                        movieItems.setVoteAverage(moviee.getDouble("vote_average"));
                        listItems.add(movieItems);
                    }
                    listMovies.postValue(listItems);
                } catch (JSONException e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

}
