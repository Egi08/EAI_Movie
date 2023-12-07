package com.eai.appmovie.sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.eai.appmovie.model.FavoriteModel;

public class FavoriteHelper {
    private static final String TABLE_NAME = DatabaseContract.TABLE_NAME;
    private static DatabaseHelper databaseHelper;
    private static SQLiteDatabase database;
    private static com.eai.appmovie.sqllite.FavoriteHelper INSTANCE;


    private FavoriteHelper(Context context) {
        // initialize your database here
        databaseHelper = new DatabaseHelper(context);
    }

    public static synchronized FavoriteHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new FavoriteHelper(context);
        }
        return INSTANCE;
    }

    public void open() {
        database = databaseHelper.getWritableDatabase();
    }

    public long insertFavorite(FavoriteModel favoriteModel) {
        Log.d("FavoriteHelper", "insertFavorite: " + favoriteModel.getId());
        ContentValues args = new ContentValues();
        args.put(DatabaseContract.FavoriteColumns.ID, favoriteModel.getId());
        args.put(DatabaseContract.FavoriteColumns.TITLE, favoriteModel.getTitle());
        args.put(DatabaseContract.FavoriteColumns.DATE, favoriteModel.getDate());
        args.put(DatabaseContract.FavoriteColumns.OVERVIEW, favoriteModel.getOverview());
        args.put(DatabaseContract.FavoriteColumns.POSTER_PATH, favoriteModel.getPoster_path());
        args.put(DatabaseContract.FavoriteColumns.BACKDROP_PATH, favoriteModel.getBackdrop_path());
        args.put(DatabaseContract.FavoriteColumns.VOTE_AVERAGE, favoriteModel.getVote_average());
        return database.insert(DatabaseContract.TABLE_NAME, null, args);
    }

    public int deleteFavorite(int id) {
        return database.delete(DatabaseContract.TABLE_NAME, DatabaseContract.FavoriteColumns.ID + " = ?", new String[]{String.valueOf(id)});
    }

    public Cursor getAllFavorites() {
        return database.query(
                DatabaseContract.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                DatabaseContract.FavoriteColumns.ID + " ASC");
    }

    public boolean isFavorite(int id) {
        open();
        String selectQuery = "SELECT * FROM " + DatabaseContract.TABLE_NAME + " WHERE " + DatabaseContract.FavoriteColumns.ID + " = " + id;
        Cursor cursor = database.rawQuery(selectQuery, null);
        boolean exist = (cursor.getCount() > 0);
        cursor.close();
        return exist;
    }
}