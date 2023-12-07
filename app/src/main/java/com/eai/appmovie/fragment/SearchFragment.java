package com.eai.appmovie.fragment;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.MatrixCursor;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.provider.SearchRecentSuggestions;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.eai.appmovie.R;
import com.eai.appmovie.adapter.MovieAdapter;
import com.eai.appmovie.adapter.SearchHistoryAdapter;
import com.eai.appmovie.api.ApiConfig;
import com.eai.appmovie.dataresponse.MovieDataResponse;
import com.eai.appmovie.model.MovieModel;
import com.eai.appmovie.provider.FilmSuggestionProvider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private SearchView svSearchFilm;
    private List<String> items;

    private Menu menu;
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
        svSearchFilm = view.findViewById(R.id.sv_search_film);
        recyclerView = view.findViewById(R.id.recyclerView);

        // Set layout manager for RecyclerView
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Initialize handler
        handler = new Handler();

        // create searchable info
//        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
//        svSearchFilm.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));


        // Set searchable configuration
        svSearchFilm.setSubmitButtonEnabled(true);

        // Inisialisasi set riwayat pencarian
        searchHistorySet = new HashSet<>();

// Muat riwayat pencarian sebelumnya dari Shared Preferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREF_SEARCH_HISTORY, Context.MODE_PRIVATE);
        searchHistorySet.addAll(sharedPreferences.getStringSet(PREF_SEARCH_HISTORY, new HashSet<>()));

// Muat riwayat pencarian ke SearchView
loadHistory("");


        // Set query text listener
        svSearchFilm.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                loadHistory(query);
                return true;
            }



            @Override
            public boolean onQueryTextChange(String newText) {
                loadHistory(newText);
                return true;
            }

        });

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private void performSearch(String query) {
        try {
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
        } catch (Exception e) {
            // Log the exception and display an error message
            Log.e("SearchFragment", "Error performing search", e);
            Toast.makeText(getContext(), "An error occurred while performing the search", Toast.LENGTH_SHORT).show();
        }

        // Tambahkan query pencarian ke dalam riwayat pencarian
        searchHistorySet.add(query);
        saveSearchHistory();

    }

    private void loadHistory(String query) {
        // Buat cursor untuk riwayat pencarian
        String[] columns = new String[]{"_id", "text"};
        Object[] temp = new Object[]{0, "default"};
        MatrixCursor cursor = new MatrixCursor(columns);

        // Tambahkan setiap riwayat pencarian ke dalam cursor
        int id = 0;
        for (String history : searchHistorySet) {
            temp[0] = id++;
            temp[1] = history;
            cursor.addRow(temp);
        }

        // Set adapter cursor ke SearchView
        svSearchFilm.setSuggestionsAdapter(new SearchHistoryAdapter(getActivity(), cursor, new ArrayList<>(searchHistorySet)));

    }

    private void saveSearchHistory() {
    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREF_SEARCH_HISTORY, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putStringSet(PREF_SEARCH_HISTORY, searchHistorySet);
    editor.apply();
}


}

