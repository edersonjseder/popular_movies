package movies.training.udacity.com.popularmovies.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.net.URL;

import movies.training.udacity.com.popularmovies.model.MovieListReviews;
import movies.training.udacity.com.popularmovies.model.MovieListTrailer;

public class FetchMoviesListTrailerTaskLoader extends AsyncTaskLoader<MovieListTrailer> {
    private static final String TAG = FetchMoviesListTrailerTaskLoader.class.getSimpleName();

    private MovieListTrailer listTrailer;
    private MovieListReviews listReviews;
    private ParseJSONToJava parseJSONToJava;
    private ProgressBar mLoadingIndicator;

    private String videoId;

    public FetchMoviesListTrailerTaskLoader(@NonNull Context context, String videoId, ProgressBar loadingIndicator) {
        super(context);
        this.videoId = videoId;
        this.mLoadingIndicator = loadingIndicator;
    }

    @Override
    protected void onStartLoading() {

        if (listTrailer != null) {

            Log.i(TAG, "onStartLoading() inside IF - String: " + videoId);
            mLoadingIndicator.setVisibility(View.VISIBLE);
            deliverResult(listTrailer);

        } else {
            Log.i(TAG, "onStartLoading() inside ELSE - starting progress bar...");
            mLoadingIndicator.setVisibility(View.VISIBLE);
            forceLoad();
        }

    }

    @Nullable
    @Override
    public MovieListTrailer loadInBackground() {

        Log.i(TAG, "loadInBackground() inside method");

        listTrailer = new MovieListTrailer();
        listReviews = new MovieListReviews();
        parseJSONToJava = new ParseJSONToJava();
        URL movieTrailersRequestUrl;
        URL movieReviewsRequestUrl;

        Log.i(TAG, "doInBackground() inside method - before if");

        movieTrailersRequestUrl = ConnectionPathUtils.buildUrlVideos(videoId);

        movieReviewsRequestUrl = ConnectionPathUtils.buildUrlReviews(videoId);

        try {

            // This does the query to the API to retrieve the JSON result
            String jsonMovieResponse = ConnectionPathUtils.doQuery(movieTrailersRequestUrl);

            //Execute method to get the JSON Object and convert it to Java Object
            listTrailer = parseJSONToJava.convertJsonToMovieListTrailerJavaClass(jsonMovieResponse);

            // This does the query to the API to retrieve the JSON result
            String jsonMovieReviewResponse = ConnectionPathUtils.doQuery(movieReviewsRequestUrl);

            //Execute method to get the JSON Object and convert it to Java Object
            listReviews = parseJSONToJava.convertJsonToMovieListReviewsJavaClass(jsonMovieReviewResponse);

            // Add List of reviews into the list of trailers class
            listTrailer.setMovieReviewInfList(listReviews.getMovieReviewInf());

            Log.i(TAG, "doInBackground() inside method - Pages: " + listTrailer.getId());

        } catch (Exception e) {
            e.printStackTrace();
            listTrailer = null;

        }

        return listTrailer;
    }

    /**
     * Sends the result of the load to the registered listener.
     *
     * @param data The result of the load
     */
    @Override
    public void deliverResult(@Nullable MovieListTrailer data) {
        listTrailer = data;
        super.deliverResult(data);
    }

}
