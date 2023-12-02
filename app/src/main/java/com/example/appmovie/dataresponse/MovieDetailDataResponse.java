package com.example.appmovie.dataresponse;

import com.example.appmovie.model.MovieModel;
import com.google.gson.annotations.SerializedName;

public class MovieDetailDataResponse {
    @SerializedName("results")

    private MovieModel results;

    public MovieModel getData2() {
        return results;
    }
}
