package com.eai.appmovie.provider;

import android.content.SearchRecentSuggestionsProvider;

public class FilmSuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.eai.appmovie.provider.FilmSuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public FilmSuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
