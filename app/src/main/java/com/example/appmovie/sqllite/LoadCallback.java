package com.example.appmovie.sqllite;

import com.example.appmovie.model.FavoriteModel;

import java.util.ArrayList;

public interface LoadCallback {
    void postExecute(ArrayList<FavoriteModel> items);
}
