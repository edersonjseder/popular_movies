package movies.training.udacity.com.popularmovies.utils;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.net.URL;

import movies.training.udacity.com.popularmovies.listener.OnPostTaskListener;
import movies.training.udacity.com.popularmovies.model.MoviesInfo;

public class FetchMoviesTask extends AsyncTask<String, Void, MoviesInfo> {
    private static final String TAG = FetchMoviesTask.class.getSimpleName();

    private OnPostTaskListener mOnPostTaskListener;
    private MoviesInfo moviesInfo;
    private ParseJSONToJava parseJSONToJava;
    private ProgressBar progress;
    private int menu;

    /**
     * Constructor to initiate the listener interface for clicking the image and show the details and
     * use the progress bar
     *
     * @param mOnPostTaskListener
     * @param progress
     */
    public FetchMoviesTask(OnPostTaskListener mOnPostTaskListener, ProgressBar progress){

        this.mOnPostTaskListener = mOnPostTaskListener;
        this.progress = progress;

    }

    /**
     * Second constructor to utilize conditions to use one of the menu options for most popular or
     * the top rated movies to be reached
     *
     * @param mOnPostTaskListener
     * @param progress
     * @param menu
     */
    public FetchMoviesTask(OnPostTaskListener mOnPostTaskListener, ProgressBar progress, int menu){

        this.mOnPostTaskListener = mOnPostTaskListener;
        this.progress = progress;
        this.menu = menu;

    }

    /**
     * This method execute the progress bar on layout while process the user request
     */
    @Override
    protected void onPreExecute() {
        Log.i(TAG, "onPreExecute() inside method - starting progress bar...");
        progress.setVisibility(View.VISIBLE);

    }

    @Override
    protected MoviesInfo doInBackground(String... strings) {

        Log.i(TAG, "doInBackground() inside method " + strings);

        moviesInfo = new MoviesInfo();
        parseJSONToJava = new ParseJSONToJava();
        URL moviesRequestUrl = null;

        Log.i(TAG, "doInBackground() inside method - before if " + menu);

        // Condition for choosing options on menu, which is most popular movies and top rated movies
        if (menu == 1){

            moviesRequestUrl = ConnectionPathUtils.buildMostPopularUrl(strings);

        } else if (menu == 2){

            moviesRequestUrl = ConnectionPathUtils.buildHighestRatedUrl(strings);

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
     * Method that gets the result of processed data by the doInBackground method
     *
     * @param moviesInfo
     */
    @Override
    protected void onPostExecute(MoviesInfo moviesInfo) {
        Log.i(TAG, "onPostExecute() inside method - param: " + moviesInfo.getTotalResults());
        // Stops the progress bar
        progress.setVisibility(View.INVISIBLE);

        // The data is passed to the Activity class through here
        mOnPostTaskListener.onTaskCompleted(moviesInfo);
    }
}
