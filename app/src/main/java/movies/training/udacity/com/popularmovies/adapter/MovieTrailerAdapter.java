package movies.training.udacity.com.popularmovies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import movies.training.udacity.com.popularmovies.R;
import movies.training.udacity.com.popularmovies.listener.OnItemClickListener;
import movies.training.udacity.com.popularmovies.listener.OnMovieTrailerSelectedListener;
import movies.training.udacity.com.popularmovies.model.MovieListTrailer;
import movies.training.udacity.com.popularmovies.model.MovieTrailerInf;
import movies.training.udacity.com.popularmovies.viewholder.MovieTrailerViewHolder;

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerViewHolder> {

    private static final String TAG = MovieTrailerAdapter.class.getSimpleName();

    private OnMovieTrailerSelectedListener mOnMovieTrailerSelectedListener;
    private List<MovieTrailerInf> movieTrailerInfList;
    private Context context;

    public MovieTrailerAdapter(MovieListTrailer mMovieListTrailer,
                               OnMovieTrailerSelectedListener mOnMovieTrailerSelectedListener,
                               Context mContext) {

        this.mOnMovieTrailerSelectedListener = mOnMovieTrailerSelectedListener;
        this.context = mContext;
        this.movieTrailerInfList = mMovieListTrailer.getMovieTrailerInfList();

    }

    @NonNull
    @Override
    public MovieTrailerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_trailer_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        return new MovieTrailerViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MovieTrailerViewHolder movieViewHolder, int position) {

        MovieTrailerInf movieTrailer = movieTrailerInfList.get(position);

        movieViewHolder.getImageViewMovieTrailerPicture().setImageResource(R.drawable.youtube_icon);
        movieViewHolder.getTextViewMovieTrailerName().setText(movieTrailer.getName());

        movieViewHolder.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mOnMovieTrailerSelectedListener.onMovieTrailerSelected(movieTrailer.getVideoId(), position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (movieTrailerInfList != null) ? movieTrailerInfList.size() : 0;
    }
}
