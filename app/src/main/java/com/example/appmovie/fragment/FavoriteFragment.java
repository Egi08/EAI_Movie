package com.example.appmovie.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appmovie.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmovie.R;
import com.example.appmovie.adapter.FavoriteAdapter;
import com.example.appmovie.model.FavoriteModel;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {

    private RecyclerView favoriteRecyclerView;
    private TextView errorMessageTextView;
    private ProgressBar favProgressBar;
    private FavoriteAdapter favoriteAdapter;
    private ArrayList<FavoriteModel> favoriteList;

    ActivityResultLauncher<Intent> resultLauncher;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);


        // Initialize views
        favoriteRecyclerView = view.findViewById(R.id.favouriteList_rv);
        errorMessageTextView = view.findViewById(R.id.error_message_tv);
        favProgressBar = view.findViewById(R.id.fav_progressbar);

        // Set layout manager for RecyclerView
        favoriteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the list of favorites (you need to implement a method to fetch data)
        // For demonstration purposes, I'm creating a dummy list
        favoriteList = getFavoriteList();

        // Initialize and set the adapter
        favoriteAdapter = new FavoriteAdapter(favoriteList, resultLauncher);
        favoriteRecyclerView.setAdapter(favoriteAdapter);

        // Check if the list is empty and show/hide views accordingly
        checkEmptyList();

        return view;
    }

    // Dummy method to get a list of favorite items
    private ArrayList<FavoriteModel> getFavoriteList() {
        // Replace this with your logic to fetch favorite items from a database or other source
        // For demonstration, I'm creating a dummy list
        ArrayList<FavoriteModel> dummyList = new ArrayList<>();
        dummyList.add(new FavoriteModel(/* Add your parameters here */));
        // Add more items as needed
        return dummyList;
    }

    // Dummy method to simulate deleting a favorite item
    private void deleteFavoriteItem(int position) {
        // Replace this with your logic to delete the item from the database or other source
        favoriteList.remove(position);
        favoriteAdapter.notifyItemRemoved(position);
        checkEmptyList();
        Toast.makeText(getContext(), "Item deleted", Toast.LENGTH_SHORT).show();
    }

    // Dummy method to simulate sharing a favorite item
    private void shareFavoriteItem(int position) {
        // Replace this with your logic to share the item
        FavoriteModel favoriteModel = favoriteList.get(position);
        String shareText = "Check out this movie: " + favoriteModel.getTitle();
        // Implement the sharing mechanism (e.g., using Intent)
        // ...

        // For demonstration, showing a Toast
        Toast.makeText(getContext(), "Shared: " + shareText, Toast.LENGTH_SHORT).show();
    }

    // Check if the list is empty and show/hide views accordingly
    private void checkEmptyList() {
        if (favoriteList.isEmpty()) {
            favoriteRecyclerView.setVisibility(View.GONE);
            errorMessageTextView.setVisibility(View.VISIBLE);
            errorMessageTextView.setText("No favorites available.");
        } else {
            favoriteRecyclerView.setVisibility(View.VISIBLE);
            errorMessageTextView.setVisibility(View.GONE);
        }
    }
}
