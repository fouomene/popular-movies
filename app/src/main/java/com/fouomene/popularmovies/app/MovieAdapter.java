package com.fouomene.popularmovies.app;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.fouomene.popularmovies.app.model.Movie;
import com.fouomene.popularmovies.app.utils.Utility;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {
    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();

    private static class ViewHolder {
        public final ImageView mPoster;
        public ViewHolder(ImageView poster) {
            this.mPoster = poster;
        }
    }

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the List is the data we want
     * to populate into the lists
     *
     * @param context        The current context. Used to inflate the layout file.
     * @param moviesList A List of movie objects to display in a list
     */
    public MovieAdapter(Activity context, List<Movie> moviesList) {
        super(context, 0, moviesList);
    }

    /**
     * Provides a view for an AdapterView GridView
     *
     * @param position    The AdapterView position that is requesting a view
     * @param convertView The recycled view to populate.
     *                    (search online for "android view recycling" to learn more)
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView poster;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_item, parent, false);
            poster = (ImageView) convertView.findViewById(R.id.movie_poster);
            convertView.setTag(new ViewHolder(poster));
        } else {
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            poster = viewHolder.mPoster;
        }

        Movie movie = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_item, parent, false);
        }

        Picasso.with(getContext())
                .load(Utility.getFinalUrl("w185",movie.getPoster_path()))
                .placeholder(R.drawable.noposter)
                .error(R.drawable.noposter)
                .into(poster);

        return convertView;
    }
}
