package movies.training.udacity.com.popularmovies.database;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

import movies.training.udacity.com.popularmovies.dao.MovieDao;
import movies.training.udacity.com.popularmovies.model.Movie;
import movies.training.udacity.com.popularmovies.typeconverter.ListConverter;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
@TypeConverters(ListConverter.class)
public abstract class AppMovieDatabase extends RoomDatabase {

    private static final String LOG_TAG = AppMovieDatabase.class.getSimpleName();

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "moviedb";
    private static AppMovieDatabase sInstance;

    public static AppMovieDatabase getsInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance..");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppMovieDatabase.class, AppMovieDatabase.DATABASE_NAME)
                        .build();
            }
        }

        Log.d(LOG_TAG, "Getting the database instance");

        return sInstance;
    }

    public abstract MovieDao movieDao();

}
