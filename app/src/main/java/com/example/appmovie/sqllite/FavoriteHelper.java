package com.example.appmovie.sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavoriteHelper {
    private static final String TABLE_NAME = DatabaseContract.TABLE_NAME;
    private final DatabaseHelper databaseHelper;
    private static SQLiteDatabase database;
    private static volatile FavoriteHelper INSTANCE;

    public FavoriteHelper(Context context) {
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

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
        if (database.isOpen()) {
            database.close();
        }
    }

    public Cursor queryShowAll() {
        return database.query(
                DatabaseContract.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                DatabaseContract.ItemColumns._ID + " ASC"
        );
    }

    public int getFavouriteStatus(String id) {
        return database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + DatabaseContract.ItemColumns._ID + " = " + id, null).getCount();
    }

    public long insertData(ContentValues contentValues) {
        return database.insert(TABLE_NAME, null, contentValues);
    }

    public int updateData(String id, ContentValues contentValues) {
        return database.update(TABLE_NAME, contentValues, DatabaseContract.ItemColumns._ID + " = ?", new String[]{id});
    }

    public int deleteData(String id) {
        return database.delete(TABLE_NAME, DatabaseContract.ItemColumns._ID + " = " + id, null);
    }
}
