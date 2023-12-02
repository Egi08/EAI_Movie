package com.example.appmovie.dataresponse;

import com.example.appmovie.model.Tvshow;
import com.google.gson.annotations.SerializedName;

public class TvDetailDataResponse {
    @SerializedName("results")

    private Tvshow results;

    public Tvshow getData4() {
        return results;
    }
}
