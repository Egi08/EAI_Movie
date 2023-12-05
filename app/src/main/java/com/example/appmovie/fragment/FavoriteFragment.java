package com.example.appmovie.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmovie.R;
import com.example.appmovie.adapter.FavoriteAdapter;
import com.example.appmovie.adapter.MovieAdapter;
import com.example.appmovie.model.FavoriteModel;
import com.example.appmovie.sqllite.DatabaseContract;
import com.example.appmovie.sqllite.FavoriteHelper;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {

    private RecyclerView recyclerView;
    private FavoriteAdapter adapter;
    private FavoriteHelper favoriteHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        favoriteHelper = FavoriteHelper.getInstance(getContext());
        favoriteHelper.open();

        loadFavoriteMovies();

        return view;
    }

    private void loadFavoriteMovies() {
        ArrayList<FavoriteModel> favoriteMovies = convertCursorToFavoriteList(favoriteHelper.queryAll());
        adapter = new FavoriteAdapter(getContext(), favoriteMovies);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        favoriteHelper.close();
    }

    private ArrayList<FavoriteModel> convertCursorToFavoriteList(Cursor cursor) {
        ArrayList<FavoriteModel> favoriteMovies = new ArrayList<>();

        while (cursor.moveToNext()) {
            FavoriteModel favoriteModel = new FavoriteModel();
            favoriteModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.ItemColumns._ID)));
            favoriteModel.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ItemColumns.TITLE)));
            favoriteModel.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ItemColumns.DATE)));
            favoriteModel.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ItemColumns.OVERVIEW)));
            favoriteModel.setPoster_path(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ItemColumns.POSTER_PATH)));
            favoriteModel.setBackdrop_path(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ItemColumns.BACKDROP_PATH)));
            favoriteModel.setVote_average(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ItemColumns.VOTE_AVERAGE)));
            favoriteModel.setType(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ItemColumns.TYPE))));
            favoriteMovies.add(favoriteModel);
        }

        return favoriteMovies;
    }
}