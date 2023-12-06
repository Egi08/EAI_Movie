package com.eai.appmovie.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eai.appmovie.R;
import com.eai.appmovie.adapter.MovieAdapter;
import com.eai.appmovie.adapter.SearchHistoryAdapter;
import com.eai.appmovie.api.ApiConfig;
import com.eai.appmovie.dataresponse.MovieDataResponse;
import com.eai.appmovie.model.MovieModel;

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
        historyRecyclerView = view.findViewById(R.id.historyRecyclerView);

        // Set layout manager for RecyclerView
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize handler
        handler = new Handler();

        // Set onClickListener for search button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchEditText.getText().toString().trim();
                performSearch(query);
                Log.d("SearchFragment", "onClick: " + query);
                if (!TextUtils.isEmpty(query)) {
                    saveSearchHistory(query);
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
            public void onResponse(@NonNull Call<MovieDataResponse> call, @NonNull Response<MovieDataResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<MovieModel> movieResponse = response.body().getData();
                        MovieAdapter adapter = new MovieAdapter(getContext(), movieResponse);
                        recyclerView.setAdapter(adapter);
                    } else {
                        if (response.errorBody() != null) {
                            Toast.makeText(getContext(), "Search failed", Toast.LENGTH_SHORT).show();
                        }
                    }
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

