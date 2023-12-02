package com.example.appmovie.sqllite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String CREATE_TABLE_FAVOURITE_QUERY =
            String.format(
                    "CREATE TABLE %S"
                            + "(%s INTEGER NOT NULL PRIMARY KEY,"
                            + " %s TEXT NOT NULL,"
                            + " %s TEXT NOT NULL,"
                            + " %s TEXT,"
                            + " %s TEXT,"
                            + " %s TEXT,"
                            + " %s TEXT NOT NULL,"
                            + " %s INT NOT NULL)",
                    DatabaseContract.TABLE_NAME,
                    DatabaseContract.ItemColumns._ID,
                    DatabaseContract.ItemColumns.TITLE,
                    DatabaseContract.ItemColumns.DATE,
                    DatabaseContract.ItemColumns.OVERVIEW,
                    DatabaseContract.ItemColumns.POSTER_PATH,
                    DatabaseContract.ItemColumns.BACKDROP_PATH,
                    DatabaseContract.ItemColumns.VOTE_AVERAGE,
                    DatabaseContract.ItemColumns.TYPE

            );

    public DatabaseHelper(@Nullable Context context) {
        super(context, DatabaseContract.DATABASE_NAME, null, DatabaseContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDb) {
        sqLiteDb.execSQL(CREATE_TABLE_FAVOURITE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDb, int i, int i1) {
        sqLiteDb.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_NAME);
        onCreate(sqLiteDb);
    }
}
