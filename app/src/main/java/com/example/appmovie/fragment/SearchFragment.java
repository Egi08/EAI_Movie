package com.example.appmovie.fragment;

import android.graphics.Movie;
import android.os.Bundle;

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

import com.example.appmovie.R;
import com.example.appmovie.adapter.MovieAdapter;
import com.example.appmovie.api.ApiConfig;
import com.example.appmovie.api.ApiService;
import com.example.appmovie.dataresponse.MovieDataResponse;
import com.example.appmovie.model.MovieModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchFragment extends Fragment {

    private EditText searchEditText;
    private Button searchButton;
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private Handler handler;
    public static final String API_KEY = "35254a98cc59f9518caf1bacbf0f5792";


    public SearchFragment() {
        // Diperlukan konstruktor kosong
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // Inisialisasi tampilan
        searchEditText = view.findViewById(R.id.searchEditText);
        searchButton = view.findViewById(R.id.searchButton);
        recyclerView = view.findViewById(R.id.recyclerView);

        // Atur layout manager untuk RecyclerView
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        handler = new Handler();

        // Atur onClickListener untuk tombol pencarian
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchEditText.getText().toString().trim();
                if (!TextUtils.isEmpty(query)) {
                    performSearch(query);
                }
            }
        });

        return view;
    }

    private void performSearch(String query) {
        // Lakukan permintaan pencarian film populer
        Call<MovieDataResponse> call = ApiConfig.getApiService().getMoviesFromQuery(API_KEY, query);

        // Lakukan pemanggilan secara asynchronous
        call.enqueue(new Callback<MovieDataResponse>() {
            @Override
            public void onResponse(Call<MovieDataResponse> call, Response<MovieDataResponse> response) {
                if (response.isSuccessful()) {
                    // Peroleh data film dari respons
                    List<MovieModel> movies = response.body().getData();

                    // Update adapter RecyclerView dengan hasil pencarian
                    MovieAdapter adapter = new MovieAdapter(getContext(), movies);
                    recyclerView.setAdapter(adapter);
                } else {
                    // Handle respons gagal
                    // Misalnya, tampilkan pesan kesalahan
                    Toast.makeText(getContext(), "Pencarian gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieDataResponse> call, Throwable t) {
                // Handle kegagalan koneksi atau permintaan
                // Misalnya, tampilkan pesan kesalahan
                Toast.makeText(getContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
