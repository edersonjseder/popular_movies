package movies.training.udacity.com.popularmovies.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.net.URL;

import movies.training.udacity.com.popularmovies.model.MoviesInfo;

public class FetchMoviesTaskLoader extends AsyncTaskLoader<MoviesInfo> {
    private static final String TAG = FetchMoviesTaskLoader.class.getSimpleName();

    private MoviesInfo moviesInfo = null;
    private Context mContext;
    private ProgressBar mProgressBar;
    private ParseJSONToJava parseJSONToJava;
    private int menu;
    private String flagPopularityOrTopRated;

    public FetchMoviesTaskLoader(@NonNull Context context, ProgressBar mLoadingIndicator) {
        super(context);
        this.mContext = context;
        this.mProgressBar = mLoadingIndicator;
    }


    public FetchMoviesTaskLoader(@NonNull Context context, ProgressBar mLoadingIndicator, int menu, String flag) {
        super(context);
        this.mContext = context;
        this.mProgressBar = mLoadingIndicator;
        this.menu = menu;
        this.flagPopularityOrTopRated = flag;
    }

    @Override
    protected void onStartLoading() {

        if ((moviesInfo != null) && (menu == 0)) {

            Log.i(TAG, "onStartLoading() inside IF - menu: " + menu + " String: " + flagPopularityOrTopRated);
            deliverResult(moviesInfo);

        } else if ((moviesInfo != null) && (menu > 0)) {

            Log.i(TAG, "onStartLoading() inside ELSE IF - menu: " + menu + " String: " + flagPopularityOrTopRated);
            deliverResult(moviesInfo);

        } else {
            Log.i(TAG, "onStartLoading() inside ELSE - starting progress bar...");
            mProgressBar.setVisibility(View.VISIBLE);
            forceLoad();
        }

    }

    @Nullable
    @Override
    public MoviesInfo loadInBackground() {

        Log.i(TAG, "loadInBackground() inside method");

        moviesInfo = new MoviesInfo();
        parseJSONToJava = new ParseJSONToJava();
        URL moviesRequestUrl = null;

        Log.i(TAG, "doInBackground() inside method - before if " + menu);

        // Condition for choosing options on menu, which is most popular movies and top rated movies
        if (menu == 1){

            moviesRequestUrl = ConnectionPathUtils.buildMostPopularUrl(flagPopularityOrTopRated);

        } else if (menu == 2){

            moviesRequestUrl = ConnectionPathUtils.buildHighestRatedUrl(flagPopularityOrTopRated);

        } else {

            moviesRequestUrl = ConnectionPathUtils.buildUrl();

        }


        try {

            // This does the query to the API to retrieve the JSON result
            String jsonMovieResponse = ConnectionPathUtils.doQuery(moviesRequestUrl);

            //Execute method to get the JSON Object and convert it to Java Object
            moviesInfo = (MoviesInfo) parseJSONToJava.convertJsonToMoviesInfoJavaClass(jsonMovieResponse);

            Log.i(TAG, "doInBackground() inside method - Pages: " + moviesInfo.getPage());

        } catch (Exception e) {
            e.printStackTrace();
            moviesInfo = null;

        }

        return moviesInfo;
    }

    /**
     * Sends the result of the load to the registered listener.
     *
     * @param data The result of the load
     */
    @Override
    public void deliverResult(@Nullable MoviesInfo data) {
        moviesInfo = data;
        super.deliverResult(data);
    }

}
