package movies.training.udacity.com.popularmovies.listener;

import movies.training.udacity.com.popularmovies.model.Movie;

/**
 * Created by root on 06/11/16.
 */

public interface OnMovieItemSelectedListener {

    // called when user selects a movie from the list
    void onMovieItemSelected(Movie movie, int position);

}
