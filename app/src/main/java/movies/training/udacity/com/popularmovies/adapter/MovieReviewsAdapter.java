package movies.training.udacity.com.popularmovies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import movies.training.udacity.com.popularmovies.R;
import movies.training.udacity.com.popularmovies.model.MovieListTrailer;
import movies.training.udacity.com.popularmovies.model.MovieReviewInf;
import movies.training.udacity.com.popularmovies.viewholder.MovieReviewsViewHolder;

public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsViewHolder> {

    private List<MovieReviewInf> movieReviewsInfList;
    private Context context;

    public MovieReviewsAdapter (MovieListTrailer mMovieListTrailer,
                                Context mContext) {
        this.context = mContext;
        this.movieReviewsInfList = mMovieListTrailer.getMovieReviewInfList();

    }

    @NonNull
    @Override
    public MovieReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_reviews_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        return new MovieReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieReviewsViewHolder movieReviewsViewHolder, int position) {

        MovieReviewInf movieReviewInf = movieReviewsInfList.get(position);

        movieReviewsViewHolder.getTextViewReviewAuthor().setText(movieReviewInf.getAuthor());
        movieReviewsViewHolder.getTextViewDescReview().setText(movieReviewInf.getContent());

    }

    @Override
    public int getItemCount() {
        return (movieReviewsInfList != null) ? movieReviewsInfList.size() : 0;
    }
}
