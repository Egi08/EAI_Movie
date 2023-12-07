package com.eai.appmovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.eai.appmovie.R;
import com.eai.appmovie.activity.MovieDetailActivity;
import com.eai.appmovie.model.FavoriteModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private final String imgBaseUrl = "https://image.tmdb.org/t/p/w500";
    Context context;
    private ArrayList<FavoriteModel> favoriteModels;

    public FavoriteAdapter(Context context, ArrayList<FavoriteModel> favoriteModels) {
        this.context = context;
        this.favoriteModels = favoriteModels;
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

    @Override
    public int getItemCount() {
        return favoriteModels != null ? favoriteModels.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, icon;
        TextView title, releaseDate;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            icon = itemView.findViewById(R.id.icon_type_iv);
            title = itemView.findViewById(R.id.title_tv);
            releaseDate = itemView.findViewById(R.id.release_date);
            cardView = itemView.findViewById(R.id.item_cv);
        }

        public void setDataMovie(FavoriteModel favoriteModel, Context context) {
            title.setText(favoriteModel.getTitle());
            releaseDate.setText(formatDate(favoriteModel.getDate()));
            Glide.with(itemView.getContext())
                    .load(imgBaseUrl + favoriteModel.getPoster_path())
                    .centerCrop()
                    .placeholder(R.drawable.no_img)
                    .into(imageView);

            cardView.setOnClickListener(view -> {
                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra("movie_id", favoriteModel.getId());
                intent.putExtra("judul", favoriteModel.getTitle());
                intent.putExtra("rating", favoriteModel.getVote_average());
                intent.putExtra("synopsis", favoriteModel.getOverview());
                intent.putExtra("backdrop", favoriteModel.getBackdrop_path());
                intent.putExtra("poster", favoriteModel.getPoster_path());
                intent.putExtra("date", favoriteModel.getDate());
                context.startActivity(intent);
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