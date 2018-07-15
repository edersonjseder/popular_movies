package movies.training.udacity.com.popularmovies.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.net.URI;
import java.net.URL;
import java.util.List;

import movies.training.udacity.com.popularmovies.R;
import movies.training.udacity.com.popularmovies.listener.OnItemClickListener;
import movies.training.udacity.com.popularmovies.listener.OnMovieItemSelectedListener;
import movies.training.udacity.com.popularmovies.model.Movie;
import movies.training.udacity.com.popularmovies.model.MoviesInfo;
import movies.training.udacity.com.popularmovies.utils.ConnectionPathUtils;
import movies.training.udacity.com.popularmovies.viewholder.MovieViewHolder;

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private static final String TAG = MovieAdapter.class.getSimpleName();

    private OnMovieItemSelectedListener mOnMovieItemSelectedListener;
    private List<Movie> movieList;
    private Context context;

    public MovieAdapter(MoviesInfo moviesInfo, OnMovieItemSelectedListener mOnMovieItemSelectedListener, Context mContext) {

        this.mOnMovieItemSelectedListener = mOnMovieItemSelectedListener;
        this.movieList = moviesInfo.getMovieList();
        this.context = mContext;

    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        return new MovieViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int position) {

        final Movie movie = movieList.get(position);

        Uri uri = ConnectionPathUtils.buildImageUrl(movie.getPosterPath());

        Log.i(TAG, "onBindViewHolder() inside method - Movie Picture Uri: " + uri);

        Glide.with(context)
                .load(uri)
                .into(movieViewHolder.getImageViewMoviePicture());

        movieViewHolder.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mOnMovieItemSelectedListener.onMovieItemSelected(movie, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (movieList != null) ? movieList.size() : 0;
    }
}
