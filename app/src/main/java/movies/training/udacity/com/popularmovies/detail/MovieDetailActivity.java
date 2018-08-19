package movies.training.udacity.com.popularmovies.detail;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.net.URL;

import movies.training.udacity.com.popularmovies.R;
import movies.training.udacity.com.popularmovies.adapter.MovieReviewsAdapter;
import movies.training.udacity.com.popularmovies.adapter.MovieTrailerAdapter;
import movies.training.udacity.com.popularmovies.database.AppMovieDatabase;
import movies.training.udacity.com.popularmovies.executors.AppExecutors;
import movies.training.udacity.com.popularmovies.listener.OnMovieTrailerSelectedListener;
import movies.training.udacity.com.popularmovies.main.MainMoviesActivity;
import movies.training.udacity.com.popularmovies.model.Movie;
import movies.training.udacity.com.popularmovies.model.MovieListTrailer;
import movies.training.udacity.com.popularmovies.model.MoviesInfo;
import movies.training.udacity.com.popularmovies.utils.ConnectionPathUtils;
import movies.training.udacity.com.popularmovies.utils.FetchMoviesListTrailerTaskLoader;

public class MovieDetailActivity extends AppCompatActivity implements
        OnMovieTrailerSelectedListener, LoaderManager.LoaderCallbacks<MovieListTrailer> {
    private static final String TAG = MovieDetailActivity.class.getSimpleName();

    private TextView textViewMovieOriginalTitleDetail;
    private TextView textViewMovieReleaseDateDetail;
    private TextView textViewMovieVoteAverageDetail;
    private TextView textViewMovieOverviewDetail;
    private TextView textViewNoReviewsMessage;
    private ImageView imageMoviePictureDetail;
    private ProgressBar mLoadingIndicator;
    private CheckBox cbFavoriteMovie;
    private Movie movie;
    private static final int MOVIE_TRAILER_LOADER_ID = 0;
    private LoaderManager.LoaderCallbacks<MovieListTrailer> callback;
    private Bundle bundleForLoader;
    private FetchMoviesListTrailerTaskLoader trailerTaskLoader;
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerReviewsView;
    private MovieTrailerAdapter movieTrailerAdapter;
    private MovieReviewsAdapter movieReviewsAdapter;
    private ScrollView mScrollViewParent;
    private ScrollView mScrollViewChild;

    // Member variable for the database
    private AppMovieDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Log.i(TAG, "onCreate() inside method");

        initViews();

        mDb = AppMovieDatabase.getsInstance(getApplicationContext());

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                Log.i(TAG, "onCreate() inside if");

                movie = (Movie) intentThatStartedThisActivity.getSerializableExtra(Intent.EXTRA_TEXT);

                Log.i(TAG, "onCreate() inside if - Movie object value: " + movie.getOriginalTitle());

                setDetailsFields(movie);

            }
        }
    }

    private void initViews() {

        textViewMovieOriginalTitleDetail = findViewById(R.id.textview_movie_original_title_detail);
        textViewMovieReleaseDateDetail = findViewById(R.id.textview_movie_release_date_detail);
        textViewMovieVoteAverageDetail = findViewById(R.id.textview_movie_vote_average_detail);
        textViewMovieOverviewDetail = findViewById(R.id.textview_movie_overview_detail);
        imageMoviePictureDetail = findViewById(R.id.image_movie_picture_detail);
        mLoadingIndicator = findViewById(R.id.pb_loading_trailer_indicator);
        cbFavoriteMovie = findViewById(R.id.cb_favorite_movie);

        textViewNoReviewsMessage = findViewById(R.id.tv_no_reviews_message_display);

        mScrollViewParent = findViewById(R.id.scrollViewParent);
        mScrollViewChild = findViewById(R.id.scrollViewChild);

        mRecyclerView = findViewById(R.id.recyclerview_movie_trailers);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        mRecyclerReviewsView = findViewById(R.id.recyclerview_movie_reviews);
        mRecyclerReviewsView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        /**
         * this snippet allows the parent scroll view to work properly
         */
        mScrollViewParent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                findViewById(R.id.scrollViewChild).getParent().requestDisallowInterceptTouchEvent(false);

                return false;
            }
        });


        /**
         * this snippet allows the child scroll view to work properly
         */
        mScrollViewChild.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                v.getParent().requestDisallowInterceptTouchEvent(true);

                return false;
            }
        });

    }

    private void setDetailsFields(Movie movie) {

        textViewMovieOriginalTitleDetail.setText(movie.getOriginalTitle());
        textViewMovieReleaseDateDetail.setText(movie.getReleaseDate());
        textViewMovieVoteAverageDetail.setText(String.valueOf(movie.getVoteAverage()));
        textViewMovieOverviewDetail.setText(movie.getOverview());
        cbFavoriteMovie.setChecked(movie.isFavorite());

        Uri imageUrl = ConnectionPathUtils.buildImageUrl(movie.getPosterPath());

        Log.i(TAG, "onBindViewHolder() inside method - Movie Picture Uri: " + imageUrl);

        Glide.with(getApplicationContext())
                .load(imageUrl)
                .into(imageMoviePictureDetail);


        int loaderId = MOVIE_TRAILER_LOADER_ID;

        callback = MovieDetailActivity.this;

        bundleForLoader = null;

        getSupportLoaderManager().initLoader(loaderId, bundleForLoader, callback);

    }

    public void onCheckboxClicked(View view) {
        Log.i(TAG, "onCheckboxClicked() inside method");

        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        if (checked) {

            movie.setFavorite(checked);

            AppExecutors.getsInstance().getDiskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDb.movieDao().insertMovie(movie);
                }
            });

        } else {

            AppExecutors.getsInstance().getDiskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDb.movieDao().deleteMovie(movie);
                }
            });

        }

    }

    @NonNull
    @Override
    public Loader<MovieListTrailer> onCreateLoader(int id, @Nullable Bundle args) {

        trailerTaskLoader = new FetchMoviesListTrailerTaskLoader(this, movie.getId().toString(), mLoadingIndicator);

        return trailerTaskLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<MovieListTrailer> loader, MovieListTrailer data) {

        mLoadingIndicator.setVisibility(View.INVISIBLE);

        try {

            if (data != null){

                Log.i(TAG, "Json Result:\nTrailers: " + data.getMovieTrailerInfList().size() + "\nReviews: " + data.getMovieReviewInfList().size());
                showMovieTrailersInfo(data);

                if (data.getMovieReviewInfList().size() > 0) {
                    showMovieReviewsInfo(data);
                } else {
                    showNoReviewsMessage();
                }


            }else{
                throw new Exception();
            }

        } catch (Exception e){
            Log.i(TAG, "onLoadFinished() inside catch block - message: " + e.getMessage());
            showErrorMessage();
        }

    }

    private void showNoReviewsMessage() {

        textViewNoReviewsMessage.setVisibility(View.VISIBLE);

    }

    private void showMovieReviewsInfo(MovieListTrailer movieListTrailer) {

        movieReviewsAdapter = new MovieReviewsAdapter(movieListTrailer, getApplicationContext());

        mRecyclerReviewsView.setAdapter(movieReviewsAdapter);

    }

    private void showMovieTrailersInfo(MovieListTrailer movieListTrailer) {

        movieTrailerAdapter = new MovieTrailerAdapter(movieListTrailer, this, getApplicationContext());

        mRecyclerView.setAdapter(movieTrailerAdapter);
        
    }

    @Override
    public void onLoaderReset(@NonNull Loader<MovieListTrailer> loader) {

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
//      mRecyclerView.setVisibility(View.INVISIBLE);
        Log.e(TAG, "Error during loading data.");
        /* Then, show the error */
//      mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMovieTrailerSelected(String movieTrailerId, int position) {
        Log.i(TAG, "onMovieTrailerSelected() inside method: " + movieTrailerId);

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + movieTrailerId));

        // Check if the youtube app exists on the device
        if (intent.resolveActivity(getPackageManager()) == null) {
            // If the youtube app doesn't exist, then use the browser
            intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + movieTrailerId));
        }

        startActivity(intent);

    }
}
