package movies.training.udacity.com.popularmovies.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import movies.training.udacity.com.popularmovies.R;
import movies.training.udacity.com.popularmovies.adapter.MovieAdapter;
import movies.training.udacity.com.popularmovies.detail.MovieDetailActivity;
import movies.training.udacity.com.popularmovies.listener.OnMovieItemSelectedListener;
import movies.training.udacity.com.popularmovies.listener.OnPostTaskListener;
import movies.training.udacity.com.popularmovies.model.Movie;
import movies.training.udacity.com.popularmovies.model.MoviesInfo;
import movies.training.udacity.com.popularmovies.utils.AlertDialogManager;
import movies.training.udacity.com.popularmovies.utils.ConnectionDetector;
import movies.training.udacity.com.popularmovies.utils.FetchMoviesTask;

public class MainMoviesActivity extends AppCompatActivity implements OnPostTaskListener, OnMovieItemSelectedListener {
    private static final String TAG = MainMoviesActivity.class.getSimpleName();

    ConnectionDetector detector;

    AlertDialogManager dialogManager;

    private RecyclerView mRecyclerView;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    private MovieAdapter movieAdapter;

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

        Log.i(TAG, "onCreate() detector: " + detector.isConnectingToInternet());

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

        loadMovieData();

    }

    private void loadMovieData() {
        new FetchMoviesTask(this, mLoadingIndicator).execute();
    }

    private void loadMostPopularMovieData(int popular) {
        String popularity = "popularity.desc";
        new FetchMoviesTask(this, mLoadingIndicator, popular).execute(popularity);
    }

    private void loadTopRatedMovieData(int topRated) {
        String vote = "top_rated";
        new FetchMoviesTask(this, mLoadingIndicator, topRated).execute(vote);
    }

    @Override
    public void onTaskCompleted(MoviesInfo moviesInfo) {
        try {
            if (moviesInfo != null){

                showMoviesInfo(moviesInfo);

            }else{
                throw new Exception();
            }

        } catch (Exception e){
            Log.i(TAG, "FragmentRequestWeb.onTaskCompleted() inside catch block - message: " + e.getMessage());
            mErrorMessageDisplay.setVisibility(View.VISIBLE);
        }
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
        Log.i(TAG, "onCreateOptionsMenu() inside method - detector: " + detector.isConnectingToInternet());

        if (detector.isConnectingToInternet()) {

            MenuInflater inflater = getMenuInflater();

            inflater.inflate(R.menu.movies_menu, menu);

            return true;

        }

        return false;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, "onOptionsItemSelected() inside method - detector: " + detector.isConnectingToInternet());

        int id = item.getItemId();

        if (id == R.id.action_most_popular) {

            loadMostPopularMovieData(1);

            return true;

        }

        if (id == R.id.action_top_rated) {

            loadTopRatedMovieData(2);

            return true;

        }

        return super.onOptionsItemSelected(item);

    }
}
