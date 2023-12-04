package com.example.appmovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appmovie.R;
import com.example.appmovie.activity.MovieDetailActivity;
import com.example.appmovie.model.FavoriteModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private final String imgBaseUrl = "https://image.tmdb.org/t/p/w500";
    private ArrayList<FavoriteModel> favoriteModels;


    private ActivityResultLauncher<Intent> resultLauncher;

    public FavoriteAdapter(ArrayList<FavoriteModel> favoriteModels, ActivityResultLauncher<Intent> resultLauncher) {
        this.favoriteModels = favoriteModels;
        this.resultLauncher = resultLauncher;
    }



    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.favorite_item, parent, false);
        return new ViewHolder(view);


    }


    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        FavoriteModel favoriteModel = favoriteModels.get(position);
        holder.setDataMovie(favoriteModel, context);
    }

//    public FavoriteAdapter(ArrayList<FavoriteModel> favoriteModels) {
//        this.favoriteModels = favoriteModels;
//    }
//
//    public void setResultLauncher(ActivityResultLauncher<Intent> resultLauncher) {
//        this.resultLauncher = resultLauncher;
//    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, icon;
        TextView title, releaseDate;
        CardView itemCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            icon = itemView.findViewById(R.id.icon_type_iv);
            title = itemView.findViewById(R.id.title_tv);
            releaseDate = itemView.findViewById(R.id.release_date);
            itemCard = itemView.findViewById(R.id.item_cv);
        }

        public void setDataMovie(FavoriteModel favoriteModel, Context context) {
            title.setText(favoriteModel.getTitle());
            releaseDate.setText(formatDate(favoriteModel.getDate()));
            Glide.with(itemView.getContext())
                    .load(imgBaseUrl + favoriteModel.getPoster_path())
                    .centerCrop()
                    .placeholder(R.drawable.no_img)
                    .into(imageView);

            if (favoriteModel.getType() == MovieDetailActivity.TYPE) {
                icon.setImageResource(R.drawable.baseline_movie_24);
            } else {
                icon.setImageResource(R.drawable.baseline_tv_24);
            }

            itemCard.setOnClickListener(view -> {
                Intent intent = new Intent(itemView.getContext(), MovieDetailActivity.class);
                intent.putExtra("title", favoriteModel.getTitle());
            });
        }
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
