package com.example.appmovie.dataresponse;

import com.example.appmovie.model.Tvshow;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShowDataResponse {
    @SerializedName("results")

    private List<Tvshow> results;

    public List<Tvshow> getData3() {
        return results;
    }
}
