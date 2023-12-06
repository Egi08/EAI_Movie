package com.eai.appmovie.api;

import com.eai.appmovie.dataresponse.MovieDataResponse;
import com.eai.appmovie.dataresponse.MovieDetailDataResponse;
import com.eai.appmovie.dataresponse.UpcomingMovieDataResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("movie/now_playing")
    Call<MovieDataResponse> getNowPlayingMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<MovieDetailDataResponse> getMovieDetails(@Path("id") int movieId, @Query("api_key") String apiKey);

    // Get Movies from query  user
    @GET("search/movie")
    Call<MovieDataResponse> getMoviesFromQuery(@Query("api_key") String apiKey, @Query("query") String query);

    // Get Upcoming Movies
    @GET("movie/upcoming")
    Call<UpcomingMovieDataResponse> getUpcomingMovies(@Query("api_key") String apiKey);
}
