package com.example.appmovie.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appmovie.R;
import com.example.appmovie.adapter.MovieAdapter;
import com.example.appmovie.adapter.SearchHistoryAdapter;
import com.example.appmovie.api.ApiConfig;
import com.example.appmovie.dataresponse.MovieDataResponse;
import com.example.appmovie.model.MovieModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private EditText searchEditText;
    private Button searchButton;
    private RecyclerView recyclerView;
    private RecyclerView historyRecyclerView;
    private MovieAdapter movieAdapter;
    private Handler handler;
    private static final String API_KEY = "35254a98cc59f9518caf1bacbf0f5792";
    private static final String PREF_SEARCH_HISTORY = "search_history";
    private Set<String> searchHistorySet;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // Initialize views
        searchEditText = view.findViewById(R.id.searchEditText);
        searchButton = view.findViewById(R.id.searchButton);
        recyclerView = view.findViewById(R.id.recyclerView);
        historyRecyclerView = view.findViewById(R.id.historyRecyclerView);

        // Set layout manager for RecyclerView
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize handler
        handler = new Handler();

        // Set onClickListener for search button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchEditText.getText().toString().trim();
                if (!TextUtils.isEmpty(query)) {
                    saveSearchHistory(query);
                    performSearch(query);
                }
            }
        });

        // Load search history
        loadSearchHistory();

        return view;
    }

    private void performSearch(String query) {
        // Perform a search for movies based on the query
        Call<MovieDataResponse> call = ApiConfig.getApiService().getMoviesFromQuery(API_KEY, query);

        // Asynchronously make the call
        call.enqueue(new Callback<MovieDataResponse>() {
            @Override
            public void onResponse(Call<MovieDataResponse> call, Response<MovieDataResponse> response) {
                if (response.isSuccessful()) {
                    // Get the movie data from the response
                    List<MovieModel> movies = response.body().getData();

                    // Update RecyclerView adapter with search results
                    MovieAdapter adapter = new MovieAdapter(getContext(), movies);
                    recyclerView.setAdapter(adapter);
                } else {
                    // Handle unsuccessful response
                    // For example, display an error message
                    Toast.makeText(getContext(), "Search failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieDataResponse> call, Throwable t) {
                // Handle connection or request failure
                // For example, display an error message
                Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveSearchHistory(String query) {
        // Initialize the searchHistorySet if it's null
        if (searchHistorySet == null) {
            searchHistorySet = new HashSet<>();
        }

        // Save the search query to SharedPreferences
        searchHistorySet.add(query);
        SharedPreferences preferences = requireActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(PREF_SEARCH_HISTORY, searchHistorySet);
        editor.apply(); // Use apply() for asynchronous saving

        // Update the search history RecyclerView
        updateSearchHistoryRecyclerView();
    }

    private void loadSearchHistory() {
        // Load search history from SharedPreferences
        SharedPreferences preferences = requireActivity().getPreferences(Context.MODE_PRIVATE);
        searchHistorySet = preferences.getStringSet(PREF_SEARCH_HISTORY, new HashSet<>());

        // Update the search history RecyclerView
        updateSearchHistoryRecyclerView();
    }

    private void updateSearchHistoryRecyclerView() {
        // Update the search history RecyclerView with the current search history
        List<String> searchHistoryList = new ArrayList<>(searchHistorySet);
        SearchHistoryAdapter historyAdapter = new SearchHistoryAdapter(getContext(), searchHistoryList);
        historyRecyclerView.setAdapter(historyAdapter);
    }
}

