package com.eai.appmovie.sqllite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// DatabaseHelper.java
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "eaimovie.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_FAVORITE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " %s TEXT NOT NULL,"
                    + " %s TEXT NOT NULL,"
                    + " %s TEXT NOT NULL,"
                    + " %s TEXT NOT NULL,"
                    + " %s TEXT NOT NULL,"
                    + " %s TEXT NOT NULL,"
                    + " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_NAME,
            DatabaseContract.FavoriteColumns._ID,
            DatabaseContract.FavoriteColumns.ID,
            DatabaseContract.FavoriteColumns.TITLE,
            DatabaseContract.FavoriteColumns.DATE,
            DatabaseContract.FavoriteColumns.OVERVIEW,
            DatabaseContract.FavoriteColumns.POSTER_PATH,
            DatabaseContract.FavoriteColumns.BACKDROP_PATH,
            DatabaseContract.FavoriteColumns.VOTE_AVERAGE
    );

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_NAME);
        onCreate(db);
    }
}