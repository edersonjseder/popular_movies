package movies.training.udacity.com.popularmovies.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;
import java.util.List;

import movies.training.udacity.com.popularmovies.R;
import movies.training.udacity.com.popularmovies.adapter.MovieAdapter;
import movies.training.udacity.com.popularmovies.database.AppMovieDatabase;
import movies.training.udacity.com.popularmovies.detail.MovieDetailActivity;
import movies.training.udacity.com.popularmovies.listener.OnMovieItemSelectedListener;
import movies.training.udacity.com.popularmovies.model.Movie;
import movies.training.udacity.com.popularmovies.model.MoviesInfo;
import movies.training.udacity.com.popularmovies.utils.AlertDialogManager;
import movies.training.udacity.com.popularmovies.utils.ConnectionDetector;
import movies.training.udacity.com.popularmovies.utils.ConnectionPathUtils;
import movies.training.udacity.com.popularmovies.utils.ParseJSONToJava;
import movies.training.udacity.com.popularmovies.viewmodel.MoviesViewModel;

public class MainMoviesActivity extends AppCompatActivity implements
        OnMovieItemSelectedListener, LoaderManager.LoaderCallbacks<MoviesInfo> {
    private static final String TAG = MainMoviesActivity.class.getSimpleName();

    ConnectionDetector detector;

    AlertDialogManager dialogManager;

    private RecyclerView mRecyclerView;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    private MovieAdapter movieAdapter;

    private static final int MOVIE_LOADER_ID = 0;

    private int menu;

    private String flagPopularityOrTopRated;

    private LoaderManager.LoaderCallbacks<MoviesInfo> callback;

    private Bundle bundleForLoader;

    // Member variable for the database
    private AppMovieDatabase mDb;

//  private FetchMoviesTaskLoader mFetchMoviesTaskLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_movies);

        // Columns for the grid
        int numColumns = 2;

        // Dialog to show the message if there is no connection available
        dialogManager = new AlertDialogManager();

        // Detects if there is connection available
        detector = new ConnectionDetector(getApplicationContext());

        Log.i(TAG, "onCreate() internet detector: " + detector.isConnectingToInternet());

        // Check for internet connection
        if (!detector.isConnectingToInternet()) {
            // Internet Connection is not present
            dialogManager.showAlertDialog(MainMoviesActivity.this, "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movies);

        /* This TextView is used to display errors and will be hidden if there are no errors */
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        /*
         * GridLayoutManager to show the movie data received from API
         */
        GridLayoutManager gridLayoutManager
                = new GridLayoutManager(this, numColumns);

        mRecyclerView.setLayoutManager(gridLayoutManager);

        mRecyclerView.setHasFixedSize(true);

        /*
         * This ID will uniquely identify the Loader. We can use it, for example, to get a handle
         * on our Loader at a later point in time through the support LoaderManager.
         */
       int loaderId = MOVIE_LOADER_ID;

        /*
         * From MainActivity, we have implemented the LoaderCallbacks interface with the type of
         * String array. (implements LoaderCallbacks<String[]>) The variable callback is passed
         * to the call to initLoader below. This means that whenever the loaderManager has
         * something to notify us of, it will do so through this callback.
         */
        callback = MainMoviesActivity.this;

        /*
         * The second parameter of the initLoader method below is a Bundle. Optionally, you can
         * pass a Bundle to initLoader that you can then access from within the onCreateLoader
         * callback. In our case, we don't actually use the Bundle, but it's here in case we wanted
         * to.
         */
        bundleForLoader = null;

        /*
         * Ensures a loader is initialized and active. If the loader doesn't already exist, one is
         * created and (if the activity/fragment is currently started) starts the loader. Otherwise
         * the last created loader is re-used.
         */
        getSupportLoaderManager().initLoader(loaderId, bundleForLoader, callback);

        mDb = AppMovieDatabase.getsInstance(getApplicationContext());

    }

    private void getFavoriteMoviesFromModelView() {
        Log.i(TAG, "getFavoriteMoviesFromModelView() inside method" );

        final MoviesInfo moviesInfo = new MoviesInfo();

        MoviesViewModel viewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);

        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {

            @Override
            public void onChanged(@Nullable List<Movie> movies) {

                moviesInfo.setMovieList(movies);

                try {

                    showMoviesInfo(moviesInfo);

                } catch (Exception e) {
                    Log.i(TAG, "getFavoriteMoviesFromModelView() inside catch block - message: " + e.getMessage());
                    showErrorMessage();
                }

            }

        });
    }

    private void loadMostPopularMovieData() {

        getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, bundleForLoader, callback);

    }

    private void loadTopRatedMovieData() {

        getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, bundleForLoader, callback);

    }

    @Override
    public void onMovieItemSelected(Movie movie, int position) {
        Log.i(TAG, "onMovieItemSelected() inside method: " + movie.getReleaseDate());

        Context context = this;
        Class destinationClass = MovieDetailActivity.class;

        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, movie);
        startActivity(intentToStartDetailActivity);

    }

    public void showMoviesInfo(MoviesInfo moviesInfo) throws Exception {

        /*
         * The MovieAdapter is responsible for linking our movie data with the Views that
         * will end up displaying our movie data.
         */
        movieAdapter = new MovieAdapter(moviesInfo, this, getApplicationContext());

        /* Setting the adapter attaches it to the RecyclerView in our layout. */
        mRecyclerView.setAdapter(movieAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(TAG, "onCreateOptionsMenu() inside method - internet detector: " + detector.isConnectingToInternet());

        if (detector.isConnectingToInternet()) {

            MenuInflater inflater = getMenuInflater();

            inflater.inflate(R.menu.movies_menu, menu);

            return true;

        }

        return false;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, "onOptionsItemSelected() inside method - internet detector: " + detector.isConnectingToInternet());

        int id = item.getItemId();


        if (id == R.id.action_most_popular) {

            menu = 1;
            flagPopularityOrTopRated = "popularity.desc";

            loadMostPopularMovieData();

            return true;

        }

        if (id == R.id.action_top_rated) {

            menu = 2;
            flagPopularityOrTopRated = "top_rated";

            loadTopRatedMovieData();

            return true;

        }

        if (id == R.id.action_favorite_movies) {

            getFavoriteMoviesFromModelView();

            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    @NonNull
    @Override
    public Loader<MoviesInfo> onCreateLoader(int id, @Nullable Bundle args) {

        return new AsyncTaskLoader<MoviesInfo>(this) {

            MoviesInfo moviesInfo = null;
            ParseJSONToJava parseJSONToJava;

            @Override
            protected void onStartLoading() {

                if ((moviesInfo != null) && (menu == 0)) {

                    Log.i(TAG, "onStartLoading() inside IF - menu: " + menu + " String: " + flagPopularityOrTopRated);
                    deliverResult(moviesInfo);

                } else if ((moviesInfo != null) && (menu > 0)) {

                    Log.i(TAG, "onStartLoading() inside ELSE IF - menu: " + menu + " String: " + flagPopularityOrTopRated);
                    mLoadingIndicator.setVisibility(View.VISIBLE);
                    deliverResult(moviesInfo);

                } else {
                    Log.i(TAG, "onStartLoading() inside ELSE - starting progress bar...");
                    mLoadingIndicator.setVisibility(View.VISIBLE);
                    forceLoad();
                }

            }

            @Nullable
            @Override
            public MoviesInfo loadInBackground() {

                Log.i(TAG, "loadInBackground() inside method - menu: " + menu);

                moviesInfo = new MoviesInfo();
                parseJSONToJava = new ParseJSONToJava();
                URL moviesRequestUrl = null;

                // Condition for choosing options on menu, which is most popular movies and top rated movies
                if (menu == 1){

                    Log.i(TAG, "doInBackground() inside IF " + menu);
                    moviesRequestUrl = ConnectionPathUtils.buildMostPopularUrl(flagPopularityOrTopRated);

                } else if (menu == 2){

                    Log.i(TAG, "doInBackground() inside ELSE IF " + menu);
                    moviesRequestUrl = ConnectionPathUtils.buildHighestRatedUrl(flagPopularityOrTopRated);

                } else {

                    Log.i(TAG, "doInBackground() inside ELSE " + menu);
                    moviesRequestUrl = ConnectionPathUtils.buildUrl();

                }


                try {

                    // This does the query to the API to retrieve the JSON result
                    String jsonMovieResponse = ConnectionPathUtils.doQuery(moviesRequestUrl);

                    //Execute method to get the JSON Object and convert it to Java Object
                    moviesInfo = parseJSONToJava.convertJsonToMoviesInfoJavaClass(jsonMovieResponse);

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

        };
/**
        Log.i(TAG, "onCreateLoader() inside method - param values: menu -> " + menu +
                " - flagString -> " + flagPopularityOrTopRated);

        if ((menu > 0) && (!flagPopularityOrTopRated.equals(""))) {

            Log.i(TAG, "onCreateLoader() - inside if");
            mFetchMoviesTaskLoader = new FetchMoviesTaskLoader(this, mLoadingIndicator, menu, flagPopularityOrTopRated);

        } else {

            Log.i(TAG, "onCreateLoader() - inside else");
            mFetchMoviesTaskLoader = new FetchMoviesTaskLoader(this, mLoadingIndicator);

        }


        return mFetchMoviesTaskLoader;
 **/

    }

    @Override
    public void onLoadFinished(@NonNull Loader<MoviesInfo> loader, MoviesInfo data) {
        Log.i(TAG, "onLoadFinished() inside method");

        mLoadingIndicator.setVisibility(View.INVISIBLE);

        try {

            if (data != null){

                showMoviesInfo(data);

            }else{
                throw new Exception();
            }

        } catch (Exception e){
            Log.i(TAG, "onLoadFinished() inside catch block - message: " + e.getMessage());
            showErrorMessage();
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<MoviesInfo> loader) {

    }

    /**
     * This method will make the error message visible and hide the weather
     * View.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }
}
