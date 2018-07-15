package movies.training.udacity.com.popularmovies.detail;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import movies.training.udacity.com.popularmovies.R;
import movies.training.udacity.com.popularmovies.model.Movie;
import movies.training.udacity.com.popularmovies.utils.ConnectionPathUtils;

public class MovieDetailActivity extends AppCompatActivity {
    private static final String TAG = MovieDetailActivity.class.getSimpleName();

    private TextView textViewMovieOriginalTitleDetail;
    private TextView textViewMovieReleaseDateDetail;
    private TextView textViewMovieVoteAverageDetail;
    private TextView textViewMovieOverviewDetail;
    private ImageView imageMoviePictureDetail;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        textViewMovieOriginalTitleDetail = (TextView) findViewById(R.id.textview_movie_original_title_detail);
        textViewMovieReleaseDateDetail = (TextView) findViewById(R.id.textview_movie_release_date_detail);
        textViewMovieVoteAverageDetail = (TextView) findViewById(R.id.textview_movie_vote_average_detail);
        textViewMovieOverviewDetail = (TextView) findViewById(R.id.textview_movie_overview_detail);
        imageMoviePictureDetail = (ImageView) findViewById(R.id.image_movie_picture_detail);

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

    private void setDetailsFields(Movie movie) {

        textViewMovieOriginalTitleDetail.setText(movie.getOriginalTitle());
        textViewMovieReleaseDateDetail.setText(movie.getReleaseDate());
        textViewMovieVoteAverageDetail.setText(String.valueOf(movie.getVoteAverage()));
        textViewMovieOverviewDetail.setText(movie.getOverview());


        Uri uri = ConnectionPathUtils.buildImageUrl(movie.getPosterPath());

        Log.i(TAG, "onBindViewHolder() inside method - Movie Picture Uri: " + uri);

        Glide.with(getApplicationContext())
                .load(uri)
                .into(imageMoviePictureDetail);

    }
}
