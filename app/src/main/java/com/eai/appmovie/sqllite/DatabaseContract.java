package com.eai.appmovie.sqllite;

import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String TABLE_NAME = "favorite_movies";

    public static final class FavoriteColumns implements BaseColumns {
        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String DATE = "date";
        public static final String OVERVIEW = "overview";
        public static final String POSTER_PATH = "poster_path";
        public static final String BACKDROP_PATH = "backdrop_path";
        public static final String VOTE_AVERAGE = "vote_average";
    }
}
