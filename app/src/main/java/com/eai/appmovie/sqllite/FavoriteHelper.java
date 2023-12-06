package com.eai.appmovie.sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.eai.appmovie.model.FavoriteModel;

public class FavoriteHelper {
    private static final String TABLE_NAME = DatabaseContract.TABLE_NAME;
    private static DatabaseHelper databaseHelper;
    private static SQLiteDatabase database;
    private static FavoriteHelper INSTANCE;

    private FavoriteHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static FavoriteHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavoriteHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public long insertFavorite(FavoriteModel favoriteModel) {
        ContentValues args = new ContentValues();
        args.put(DatabaseContract.ItemColumns.TITLE, favoriteModel.getTitle());
        args.put(DatabaseContract.ItemColumns.DATE, favoriteModel.getDate());
        args.put(DatabaseContract.ItemColumns.OVERVIEW, favoriteModel.getOverview());
        args.put(DatabaseContract.ItemColumns.POSTER_PATH, favoriteModel.getPoster_path());
        args.put(DatabaseContract.ItemColumns.BACKDROP_PATH, favoriteModel.getBackdrop_path());
        args.put(DatabaseContract.ItemColumns.VOTE_AVERAGE, favoriteModel.getVote_average());
        args.put(DatabaseContract.ItemColumns.TYPE, favoriteModel.getType());
        return database.insert(DatabaseContract.TABLE_NAME, null, args);
    }

    public int deleteFavorite(int id) {
        return database.delete(DatabaseContract.TABLE_NAME, DatabaseContract.ItemColumns._ID + " = ?", new String[]{String.valueOf(id)});
    }

    public Cursor queryAll() {
        return database.query(
                DatabaseContract.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                DatabaseContract.ItemColumns._ID + " ASC");
    }
}