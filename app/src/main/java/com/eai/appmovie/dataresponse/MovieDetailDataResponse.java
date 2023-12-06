package com.eai.appmovie.dataresponse;

import com.eai.appmovie.model.MovieModel;
import com.google.gson.annotations.SerializedName;

public class MovieDetailDataResponse {
    @SerializedName("results")

    private MovieModel results;

    public MovieModel getData2() {
        return results;
    }
}
