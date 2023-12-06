package com.eai.appmovie.model;

import com.google.gson.annotations.SerializedName;

public class MovieModel {

    @SerializedName("id")
    private String id;
    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("title")
    private String title;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("backdrop_path")
    private String BackdropPath;

    @SerializedName("vote_average")
    private String rating;

    @SerializedName("overview")
    private String synopsis;

    public MovieModel(String id, String posterPath, String title, String releaseDate, String backdropPath, String rating, String synopsis) {
        this.id = id;
        this.posterPath = posterPath;
        this.title = title;
        this.releaseDate = releaseDate;
        BackdropPath = backdropPath;
        this.rating = rating;
        this.synopsis = synopsis;
    }

    public String getId() {
        return id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getBackdropPath() {
        return BackdropPath;
    }

    public String getRating() {
        return rating;
    }

    public String getSynopsis() {
        return synopsis;
    }
}
