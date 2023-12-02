package com.example.appmovie.sqllite;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;

import com.example.appmovie.model.FavoriteModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LoadAsyncData {
    private final WeakReference<Context> contextWeakReference;
    private final WeakReference<LoadCallback> weakCallback;

    public LoadAsyncData(Context contextWeakReference, LoadCallback weakCallback) {
        this.contextWeakReference = new WeakReference<>(contextWeakReference);
        this.weakCallback = new WeakReference<>(weakCallback);
    }

    public void execute() {
        Executor executors = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executors.execute(() -> {
            Context context = contextWeakReference.get();
            FavoriteHelper favoriteHelper = FavoriteHelper.getInstance(context);
            favoriteHelper.open();
            Cursor cursor = favoriteHelper.queryShowAll();
            ArrayList<FavoriteModel> favouriteModels = MappingHelper.cursorToArraylist(cursor);
            favoriteHelper.close();
            handler.post(() -> weakCallback.get().postExecute(favouriteModels));
        });
    }
}
