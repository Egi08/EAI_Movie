package com.example.appmovie.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.TextView;

import com.example.appmovie.R;
import com.example.appmovie.fragment.FavoriteFragment;
import com.example.appmovie.fragment.NowPlayingFragment;
import com.example.appmovie.fragment.SearchFragment;
import com.example.appmovie.fragment.UpcomingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    TextView barTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setView();

        replaceFragment(new NowPlayingFragment());

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            // Kondisi Navigasi -> tidak bisa pake switch, krn error "Non-constant fields in case"
            if (id == R.id.nowplaying_btn) {
                replaceFragment(new NowPlayingFragment());
                barTitle.setText("Now Playing");
            } else if (id == R.id.upcoming_btn) {
                replaceFragment(new UpcomingFragment());
                barTitle.setText("Upcoming");
            } else if (id == R.id.favorite_btn) {
                replaceFragment(new FavoriteFragment());
                barTitle.setText("Favorites");
            }else if (id == R.id.search_btn) {
                replaceFragment(new SearchFragment());
                barTitle.setText("Search");
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    public void setView() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        barTitle = findViewById(R.id.app_bar_name);
    }
}