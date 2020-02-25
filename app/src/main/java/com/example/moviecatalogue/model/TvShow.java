package com.example.moviecatalogue.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class TvShow implements Parcelable {

    public static final Parcelable.Creator<TvShow> CREATOR = new Parcelable.Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel source) {
            return new TvShow(source);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };
    private String poster;
    private String posterBg;
    private String title;
    private String releaseDate;
    private String description;
    private Double voteAverage;

    private TvShow(Parcel in) {
        this.title = in.readString();
        this.releaseDate = in.readString();
        this.description = in.readString();
        this.poster = in.readString();
        this.posterBg = in.readString();
        this.voteAverage = in.readDouble();
    }


    public TvShow(JSONObject object) {
        try {
            String poster = object.getString("poster_path");
            String posterBg = object.getString("background_path");
            String title = object.getString("title");
            String releaseDate = object.getString("release_date");
            String description = object.getString("overview");
            Double voteAverage = object.getDouble("vote_average");

            setPoster(poster);
            setTitle(title);
            setPosterBg(posterBg);
            setDescription(description);
            setReleaseDate(releaseDate);
            setVoteAverage(voteAverage);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public TvShow() {

    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPosterBg() {
        return posterBg;
    }

    public void setPosterBg(String posterBg) {
        this.posterBg = posterBg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getVoteAverage() { return voteAverage; }

    public void setVoteAverage(Double voteAverage) { this.voteAverage = voteAverage; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.title);
        parcel.writeString(this.releaseDate);
        parcel.writeString(this.description);
        parcel.writeString(this.poster);
        parcel.writeString(this.posterBg);
        parcel.writeDouble(this.voteAverage);
    }

}