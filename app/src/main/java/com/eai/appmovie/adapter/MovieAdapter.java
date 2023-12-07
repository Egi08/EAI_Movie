package com.eai.appmovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.eai.appmovie.activity.MovieDetailActivity;
import com.eai.appmovie.model.FavoriteModel;
import com.eai.appmovie.model.MovieModel;
import com.eai.appmovie.R;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    private List<MovieModel> movieModel;

    private ArrayList<FavoriteModel> favoriteModels;

    public MovieAdapter(Context context, List<MovieModel> movieModel) {
        this.context = context;
        this.movieModel = movieModel;
    }

    public MovieAdapter(Context context, ArrayList<FavoriteModel> favoriteModels) {
        this.context = context;
        this.favoriteModels = favoriteModels;
    }

    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        MovieModel movieModelResponse = movieModel.get(position);
        holder.setData(movieModelResponse, context);

    }

    @Override
    public int getItemCount() {
        return movieModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, releaseDate;
        ImageView imageView;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv1);
            releaseDate = itemView.findViewById(R.id.tv2);
            imageView = itemView.findViewById(R.id.img1);
            cardView = itemView.findViewById(R.id.cv1);
        }

        public void setData(MovieModel movieModel, Context context) {
            title.setText(movieModel.getTitle());
            releaseDate.setText(movieModel.getReleaseDate());
            Glide.with(itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500" + movieModel.getPosterPath())
                    .into(imageView);

            cardView.setOnClickListener(view -> {
                Intent intent = new Intent(itemView.getContext(), MovieDetailActivity.class);
                intent.putExtra("movie_id", movieModel.getId());
                intent.putExtra("rating", movieModel.getRating());
                intent.putExtra("synopsis", movieModel.getSynopsis());
                intent.putExtra("backdrop", movieModel.getBackdropPath());
                intent.putExtra("judul", movieModel.getTitle());
                intent.putExtra("poster", movieModel.getPosterPath());
                intent.putExtra("date", movieModel.getReleaseDate());
                itemView.getContext().startActivity(intent);
            });
        }
    }
}
