package com.example.appmovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appmovie.R;
import com.example.appmovie.activity.TvShowDetailActivity;
import com.example.appmovie.model.Tvshow;

import java.util.List;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.ViewHolder> {

    Context context;
    private List<Tvshow> dataPerson;

    public TvShowAdapter(Context context, List<Tvshow> dataPerson) {
        this.context = context;
        this.dataPerson = dataPerson;
    }

    @NonNull
    @Override
    public TvShowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowAdapter.ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Tvshow tvResponse = dataPerson.get(position);
        holder.setData(tvResponse, context);

    }

    @Override
    public int getItemCount() {
        return dataPerson.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Name, date;
        ImageView Profile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.tv1);
            date = itemView.findViewById(R.id.tv2);
            Profile = itemView.findViewById(R.id.img1);
        }

        public void setData(Tvshow tv, Context context) {
            String name = tv.getName();
            Name.setText(name);
            date.setText(tv.getFirstAirDate().substring(0, 4));
            Glide.with(itemView.getContext()).load("https://image.tmdb.org/t/p/w500" + tv.getPosterPath())
                    .into(Profile);

            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(itemView.getContext(), TvShowDetailActivity.class);
                intent.putExtra("tv_id", tv.getId());
                intent.putExtra("rating", tv.getRating());
                intent.putExtra("synopsis", tv.getOverview());
                intent.putExtra("backdrop", tv.getBackdropPath());
                intent.putExtra("judul", tv.getName());
                intent.putExtra("poster", tv.getPosterPath());
                intent.putExtra("date", tv.getFirstAirDate());
                itemView.getContext().startActivity(intent);
            });
        }

    }
}
