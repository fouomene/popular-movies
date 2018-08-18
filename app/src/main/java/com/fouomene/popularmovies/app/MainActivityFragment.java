package com.fouomene.popularmovies.app;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.fouomene.popularmovies.app.api.TheMovieDBService;
import com.fouomene.popularmovies.app.model.Movie;
import com.fouomene.popularmovies.app.model.Movies;
import com.fouomene.popularmovies.app.utils.Utility;

import java.util.Collections;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivityFragment extends Fragment {

    private MovieAdapter movieAdapter;
    private List<Movie> moviesList = Collections.emptyList();
    private GridView gridView;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        gridView = (GridView) rootView.findViewById(R.id.movies_grid);

        //for log
       /* OkHttpClient.Builder client = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.addInterceptor(loggingInterceptor);*/

        if (isOnline()){

            if ("mostpopular".equals(Utility.getPreferredSortOrder(getActivity().getApplicationContext()))) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Utility.API_URL)
                        //.client(client.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                TheMovieDBService service = retrofit.create(TheMovieDBService.class);


                service.getMoviePopular(getResources().getString(R.string.api_key)).enqueue(new Callback<Movies>() {
                   @Override
                   public void onResponse(Call<Movies> call, Response<Movies> response) {

                       if (response.body()!= null){
                           moviesList = response.body().getResults();
                           movieAdapter = new MovieAdapter(getActivity(), moviesList);
                           gridView.setAdapter(movieAdapter);
                       }else {
                           Toast.makeText(getContext(), getResources().getString(R.string.data_unavailable), Toast.LENGTH_SHORT).show();
                       }
                   }

                   @Override
                   public void onFailure(Call<Movies> call, Throwable t) {
                       Toast.makeText(getContext(), getResources().getString(R.string.server_unavailable), Toast.LENGTH_SHORT).show();
                   }
               });

            }else{


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Utility.API_URL)
                        //.client(client.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                TheMovieDBService service = retrofit.create(TheMovieDBService.class);


                service.getMovieTopRated(getResources().getString(R.string.api_key)).enqueue(new Callback<Movies>() {
                    @Override
                    public void onResponse(Call<Movies> call, Response<Movies> response) {

                        if (response.body()!= null){
                            moviesList = response.body().getResults();
                            movieAdapter = new MovieAdapter(getActivity(), moviesList);
                            gridView.setAdapter(movieAdapter);
                        }else {
                            Toast.makeText(getContext(), getResources().getString(R.string.data_unavailable), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Movies> call, Throwable t) {
                        Toast.makeText(getContext(), getResources().getString(R.string.server_unavailable), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }else {
            Toast.makeText(getContext(), getResources().getString(R.string.check_network), Toast.LENGTH_SHORT).show();
        }
        // When the user clicks on the GridItem
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                Movie movieSelect = (Movie) gridView.getItemAtPosition(position);
                Intent intent = new Intent(getActivity().getApplicationContext(), DetailActivity.class);
                intent.putExtra(Utility.EXTRA_DETAIL_MOVIE, movieSelect);
                startActivity(intent);

            }
        });


        return rootView;
    }

    public boolean isOnline() {
        boolean status=false;
        try{
            ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState()==NetworkInfo.State.CONNECTED) {
                status= true;
            }else {
                netInfo = cm.getNetworkInfo(1);
                if(netInfo!=null && netInfo.getState()==NetworkInfo.State.CONNECTED)
                    status= true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return status;
    }
}