package movies.training.udacity.com.popularmovies.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import movies.training.udacity.com.popularmovies.R;
import movies.training.udacity.com.popularmovies.listener.OnItemClickListener;

public class MovieTrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView imageViewMovieTrailerPicture;
    private TextView textViewMovieTrailerName;

    private OnItemClickListener onItemClickListener;

    public MovieTrailerViewHolder(View itemView) {
        super(itemView);

        imageViewMovieTrailerPicture = itemView.findViewById(R.id.movie_trailer_photo);
        textViewMovieTrailerName = itemView.findViewById(R.id.movie_trailer_name);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        onItemClickListener.onItemClick(v, getAdapterPosition());
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ImageView getImageViewMovieTrailerPicture() {
        return imageViewMovieTrailerPicture;
    }

    public TextView getTextViewMovieTrailerName() {
        return textViewMovieTrailerName;
    }
}
