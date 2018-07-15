package movies.training.udacity.com.popularmovies.listener;

import movies.training.udacity.com.popularmovies.model.MoviesInfo;

/**
 * Created by ederson.js on 14/07/2018.
 */
public interface OnPostTaskListener {
    public void onTaskCompleted(MoviesInfo moviesInfo);
}
