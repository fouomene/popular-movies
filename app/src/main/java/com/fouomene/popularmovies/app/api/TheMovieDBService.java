package com.fouomene.popularmovies.app.api;

import com.fouomene.popularmovies.app.model.Movies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TheMovieDBService {

    @GET("movie/popular")
    Call<Movies> getMoviePopular(@Query("api_key") String api_key);

    @GET("movie/top_rated")
    Call<Movies> getMovieTopRated(@Query("api_key") String api_key);

}
