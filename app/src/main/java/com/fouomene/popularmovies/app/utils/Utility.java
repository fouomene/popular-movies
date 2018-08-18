package com.fouomene.popularmovies.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.fouomene.popularmovies.app.R;

public class Utility {

    public static final String API_URL = "https://api.themoviedb.org/3/";
    public static final String EXTRA_DETAIL_MOVIE = "com.fouomene.popularmovies.app.model.movie";
    public static String getFinalUrl(String size, String posterPath){
        return  "http://image.tmdb.org/t/p/"+size+"/"+posterPath;
    }

    public static void setPreferredSortOrder(Context context, String sortOrder) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(context.getString(R.string.pref_sort_order_key), sortOrder);
        editor.commit();
    }

    public static String getPreferredSortOrder(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(context.getString(R.string.pref_sort_order_key),
                context.getString(R.string.pref_sort_order_default));
    }
}
