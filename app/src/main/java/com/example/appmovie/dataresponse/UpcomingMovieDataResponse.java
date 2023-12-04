package com.example.appmovie.dataresponse;

import com.example.appmovie.model.MovieModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpcomingMovieDataResponse {
    @SerializedName("results")

    private List<MovieModel> results;

    public List<MovieModel> getData() {
        return results;
    }
}
