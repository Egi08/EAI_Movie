package com.example.appmovie.sqllite;

import android.database.Cursor;

import com.example.appmovie.model.FavoriteModel;

import java.util.ArrayList;

public class MappingHelper {
    public static ArrayList<FavoriteModel> cursorToArraylist (Cursor cursor) {
        ArrayList<FavoriteModel> favoriteModels = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.ItemColumns._ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ItemColumns.TITLE));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ItemColumns.DATE));
            String overview = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ItemColumns.OVERVIEW));
            String poster_path = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ItemColumns.POSTER_PATH));
            String backdrop_path = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ItemColumns.BACKDROP_PATH));
            String vote_average = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ItemColumns.VOTE_AVERAGE));
            int type = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.ItemColumns.TYPE));
            favoriteModels.add(new FavoriteModel(id, title, date, overview, poster_path, backdrop_path, vote_average, type));
        }
        return favoriteModels;
    }
}
