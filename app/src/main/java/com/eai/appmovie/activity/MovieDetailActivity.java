package com.eai.appmovie.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.eai.appmovie.model.FavoriteModel;
import com.eai.appmovie.R;
import com.eai.appmovie.api.ApiConfig;
import com.eai.appmovie.dataresponse.MovieDetailDataResponse;
import com.eai.appmovie.sqllite.FavoriteHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView imgFilmBanner, imgFilmCover, backBtn, favBtn;
    private TextView tvFilmTitle, tvRating, tvFilmSynopsis, tvReleaseDate;

    private FloatingActionButton fabShareBtn;
    boolean isFavorite = false;

    String movieId, judul, rating, synopsis, backdropPath, poster, dates;

    FavoriteHelper favoriteHelper;
    FavoriteModel favoriteModel = new FavoriteModel();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        favoriteHelper = FavoriteHelper.getInstance(this);
        favoriteHelper.open();

        setView();
    }

    private void setView() {
        imgFilmBanner = findViewById(R.id.img_film_banner);
        imgFilmCover = findViewById(R.id.img_film_cover);
        tvFilmTitle = findViewById(R.id.tv_film_title);
        tvRating = findViewById(R.id.tv_rating);
        tvFilmSynopsis = findViewById(R.id.tv_film_synopsis);
        tvReleaseDate = findViewById(R.id.tv_release_date);
        favBtn = findViewById(R.id.favbutton);
        backBtn = findViewById(R.id.backbutton);
        fabShareBtn = findViewById(R.id.fabShare);

        Intent intent = getIntent();
        movieId = intent.getStringExtra("movie_id");
        judul = intent.getStringExtra("judul");
        rating = intent.getStringExtra("rating");
        synopsis = intent.getStringExtra("synopsis");
        backdropPath = intent.getStringExtra("backdrop");
        poster = intent.getStringExtra("poster");
        dates = intent.getStringExtra("date");

        tvFilmTitle.setText(judul);
        tvRating.setText(rating);
        tvFilmSynopsis.setText(synopsis);
        tvReleaseDate.setText(formatDate(dates));

        Glide.with(MovieDetailActivity.this)
                .load("https://image.tmdb.org/t/p/w500" + backdropPath)
                .into(imgFilmBanner);
        Glide.with(MovieDetailActivity.this)
                .load("https://image.tmdb.org/t/p/w500" + poster)
                .into(imgFilmCover);


        backBtn.setOnClickListener(view -> {
            finish();
        });

        fabShareBtn.setOnClickListener(view -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String subject = "Check out this movie! " + judul + " on EAI Movie App";
            String description = "Title: " + judul + "\n" +
                    "Rating: " + rating + "\n" +
                    "Synopsis: " + synopsis + "\n" +
                    "Release Date: " + formatDate(dates) + "\n" +
                    "https://www.themoviedb.org/movie/" + movieId;
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
            shareIntent.putExtra(Intent.EXTRA_TEXT, description);
            startActivity(Intent.createChooser(shareIntent, "Share using"));
        });

        // Cek apakah film ini sudah favorit atau tidak
        isFavorite = favoriteHelper.isFavorite(Integer.parseInt(movieId));
        Log.d("isFavorite", String.valueOf(isFavorite));

        // Atur ikon favorit berdasarkan status favorit
        if (isFavorite) {
            favBtn.setImageResource(R.drawable.red_favorite);
        } else {
            favBtn.setImageResource(R.drawable.baseline_favorite_border_24);
        }

        favBtn.setOnClickListener(view -> {
            if (!isFavorite) {
                favBtn.setImageResource(R.drawable.red_favorite);
                favoriteModel = new FavoriteModel(
                        movieId,
                        judul,
                        dates,
                        synopsis,
                        poster,
                        backdropPath,
                        rating
                );
                favoriteHelper.insertFavorite(favoriteModel);
                isFavorite = true;
                Toast.makeText(MovieDetailActivity.this, "Added to Favorites", Toast.LENGTH_SHORT).show();
            } else {
                favBtn.setImageResource(R.drawable.baseline_favorite_border_24);
                favoriteHelper.deleteFavorite(Integer.parseInt(movieId));
                isFavorite = false;
                Toast.makeText(MovieDetailActivity.this, "Removed from Favorites", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static String formatDate(String inputDate) {
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        DateFormat outputFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        String formattedDate = "";

        try {
            Date date = inputFormat.parse(inputDate);
            formattedDate = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formattedDate;
    }
}
