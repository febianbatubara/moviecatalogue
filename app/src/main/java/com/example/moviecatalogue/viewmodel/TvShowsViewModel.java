package com.example.moviecatalogue.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviecatalogue.BuildConfig;
import com.example.moviecatalogue.model.TvShow;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TvShowsViewModel extends ViewModel {
    private MutableLiveData<ArrayList<TvShow>> listTvShows = new MutableLiveData<>();

    public LiveData<ArrayList<TvShow>> getTvShow() {
        return listTvShows;
    }

    public void setTvShow(String tvShow) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TvShow> listItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/tv?api_key=" + BuildConfig.API_KEY + "&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject tvShow = list.getJSONObject(i);
                        TvShow tvShowItems = new TvShow(tvShow);
                        tvShowItems.setPoster(tvShow.getString("poster_path"));
                        tvShowItems.setTitle(tvShow.getString("original_name"));
                        tvShowItems.setReleaseDate(tvShow.getString("first_air_date"));
                        tvShowItems.setDescription(tvShow.getString("overview"));
                        tvShowItems.setPosterBg(tvShow.getString("backdrop_path"));
                        tvShowItems.setVoteAverage(tvShow.getDouble("vote_average"));
                        listItems.add(tvShowItems);
                    }
                    listTvShows.postValue(listItems);
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

    public void setSearchedTvShow(String title) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TvShow> listItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/tv?api_key=" + BuildConfig.API_KEY + "&language=en-US&query=" + title;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject tvShow = list.getJSONObject(i);
                        TvShow tvShowItems = new TvShow(tvShow);
                        tvShowItems.setPoster(tvShow.getString("poster_path"));
                        tvShowItems.setTitle(tvShow.getString("original_name"));
                        tvShowItems.setReleaseDate(tvShow.getString("first_air_date"));
                        tvShowItems.setDescription(tvShow.getString("overview"));
                        tvShowItems.setPosterBg(tvShow.getString("backdrop_path"));
                        tvShowItems.setVoteAverage(tvShow.getDouble("vote_average"));
                        listItems.add(tvShowItems);
                    }
                    listTvShows.postValue(listItems);
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
