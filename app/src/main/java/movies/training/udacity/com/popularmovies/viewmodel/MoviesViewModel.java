package movies.training.udacity.com.popularmovies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import movies.training.udacity.com.popularmovies.database.AppMovieDatabase;
import movies.training.udacity.com.popularmovies.model.Movie;

public class MoviesViewModel extends AndroidViewModel {
    // Constant for logging
    private static final String TAG = MoviesViewModel.class.getSimpleName();

    private LiveData<List<Movie>> movies;

    public MoviesViewModel(@NonNull Application application) {
        super(application);

        AppMovieDatabase database = AppMovieDatabase.getsInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the database");
        movies = database.movieDao().loadAllMovies();

    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }
}
