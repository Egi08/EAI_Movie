package com.eai.appmovie.dataresponse;

import com.eai.appmovie.model.MovieModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpcomingMovieDataResponse {
    @SerializedName("results")

    private List<MovieModel> results;

    public List<MovieModel> getData() {
        return results;
    }
}
