package com.eai.appmovie.adapter;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.eai.appmovie.R;

import java.util.List;

public class SearchHistoryAdapter extends CursorAdapter {

    private List<String> items;

    private TextView tvSearchHistory;

    public SearchHistoryAdapter(Context context, Cursor cursor, List<String> items) {
        super(context, cursor, false);
        this.items = items;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        tvSearchHistory.setText(items.get(cursor.getPosition()));

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_search_history, parent, false);

        tvSearchHistory = (TextView) view.findViewById(R.id.tv_search_history);

        return view;
    }
}
