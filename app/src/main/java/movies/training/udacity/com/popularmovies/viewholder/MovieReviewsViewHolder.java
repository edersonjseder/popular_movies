package movies.training.udacity.com.popularmovies.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import movies.training.udacity.com.popularmovies.R;

public class MovieReviewsViewHolder extends RecyclerView.ViewHolder {

    private TextView textViewReviewAuthor;
    private TextView textViewDescReview;

    public MovieReviewsViewHolder(View itemView) {
        super(itemView);

        textViewReviewAuthor = itemView.findViewById(R.id.textViewReviewAuthor);
        textViewDescReview = itemView.findViewById(R.id.textViewDescReview);

    }

    public TextView getTextViewReviewAuthor() {
        return textViewReviewAuthor;
    }

    public TextView getTextViewDescReview() {
        return textViewDescReview;
    }
}
