package com.fouomene.popularmovies.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fouomene.popularmovies.app.model.Movie;
import com.fouomene.popularmovies.app.utils.Utility;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @BindView(R.id.title_movie) TextView titleMovie;
    @BindView(R.id.vote_average_movie) TextView  voteAverageMovie;
    @BindView(R.id.popularity_movie) TextView popularityMovie;
    @BindView(R.id.adulte_movie) TextView adulteMovie;
    @BindView(R.id.release_date_movie) TextView releaseDateMovie;
    @BindView(R.id.overview_movie) TextView overviewMovie;
    @BindView(R.id.poster_detail_movie) ImageView posterDetailMovie;
    @BindView(R.id.backdrop_detail_movie) ImageView backdropDetailMovie;

    private Movie movieSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        if (savedInstanceState == null) {
            movieSelect = (Movie) intent.getSerializableExtra(Utility.EXTRA_DETAIL_MOVIE) ;
        }

        if (savedInstanceState != null && savedInstanceState.containsKey(Utility.EXTRA_DETAIL_MOVIE)) {
            movieSelect = (Movie) savedInstanceState.getSerializable(Utility.EXTRA_DETAIL_MOVIE);
        }


        if (movieSelect == null) {
            // movie data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(Utility.getFinalUrl("w780",movieSelect.getBackdrop_path()))
                .placeholder(R.drawable.noposterdetail780)
                .error(R.drawable.noposterdetail780)
                .into(backdropDetailMovie);
        Picasso.with(this)
                .load(Utility.getFinalUrl("w92",movieSelect.getPoster_path()))
                .placeholder(R.drawable.noposterdetail780)
                .error(R.drawable.noposterdetail780)
                .into(posterDetailMovie);

        setTitle(movieSelect.getTitle());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // When tablets rotate, the currently selected movie needs to be saved.
        outState.putSerializable(Utility.EXTRA_DETAIL_MOVIE,movieSelect);
        super.onSaveInstanceState(outState);
    }
    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        titleMovie.setText(movieSelect.getTitle());
        voteAverageMovie.setText(movieSelect.getVote_average()+"/10");
        popularityMovie.setText(movieSelect.getPopularity()+"");
        adulteMovie.setText(movieSelect.getAdult()+"");
        releaseDateMovie.setText(movieSelect.getRelease_date());
        overviewMovie.setText(movieSelect.getOverview());

    }
}
