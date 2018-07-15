package movies.training.udacity.com.popularmovies.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import movies.training.udacity.com.popularmovies.R;
import movies.training.udacity.com.popularmovies.listener.OnItemClickListener;

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView imageViewMoviePicture;

    private OnItemClickListener onItemClickListener;

    public MovieViewHolder(View itemView) {
        super(itemView);

        imageViewMoviePicture = (ImageView) itemView.findViewById(R.id.image_movie_picture);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        onItemClickListener.onItemClick(v, getAdapterPosition());
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ImageView getImageViewMoviePicture() {
        return imageViewMoviePicture;
    }
}
