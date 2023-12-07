package com.eai.appmovie.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eai.appmovie.R;
import com.eai.appmovie.adapter.FavoriteAdapter;
import com.eai.appmovie.model.FavoriteModel;
import com.eai.appmovie.model.MovieModel;
import com.eai.appmovie.sqllite.DatabaseContract;
import com.eai.appmovie.sqllite.FavoriteHelper;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    private TextView tvNoFavorites;
    private RecyclerView recyclerView;
    private FavoriteAdapter favoriteAdapter;
    private FavoriteHelper favoriteHelper;
    private ArrayList<FavoriteModel> favoriteList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        tvNoFavorites = view.findViewById(R.id.tv_no_favorites);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        favoriteHelper = FavoriteHelper.getInstance(getActivity());
        favoriteHelper.open();
        loadFavorites();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFavorites();
    }

    private void loadFavorites() {
        Cursor cursor = favoriteHelper.getAllFavorites();
        ArrayList<FavoriteModel>  favoriteList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex(DatabaseContract.FavoriteColumns.ID);
            String id = idIndex != -1 ? cursor.getString(idIndex) : null;

            int titleIndex = cursor.getColumnIndex(DatabaseContract.FavoriteColumns.TITLE);
            String title = titleIndex != -1 ? cursor.getString(titleIndex) : null;

            int dateIndex = cursor.getColumnIndex(DatabaseContract.FavoriteColumns.DATE);
            String date = dateIndex != -1 ? cursor.getString(dateIndex) : null;

            int overviewIndex = cursor.getColumnIndex(DatabaseContract.FavoriteColumns.OVERVIEW);
            String overview = overviewIndex != -1 ? cursor.getString(overviewIndex) : null;

            int posterPathIndex = cursor.getColumnIndex(DatabaseContract.FavoriteColumns.POSTER_PATH);
            String posterPath = posterPathIndex != -1 ? cursor.getString(posterPathIndex) : null;

            int backdropPathIndex = cursor.getColumnIndex(DatabaseContract.FavoriteColumns.BACKDROP_PATH);
            String backdropPath = backdropPathIndex != -1 ? cursor.getString(backdropPathIndex) : null;

            int voteAverageIndex = cursor.getColumnIndex(DatabaseContract.FavoriteColumns.VOTE_AVERAGE);
            String voteAverage = voteAverageIndex != -1 ? cursor.getString(voteAverageIndex) : null;

            favoriteList.add(new FavoriteModel(id, title, date, overview, posterPath, backdropPath, voteAverage));
        }

        if (favoriteList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            tvNoFavorites.setVisibility(View.VISIBLE);
        } else {
            favoriteAdapter = new FavoriteAdapter(getActivity(), favoriteList);
            recyclerView.setAdapter(favoriteAdapter);
        }
    }
}