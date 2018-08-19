package movies.training.udacity.com.popularmovies.listener;

public interface OnMovieTrailerSelectedListener {

    // called when user selects a movie from the list
    void onMovieTrailerSelected(String movieTrailerId, int position);

}
