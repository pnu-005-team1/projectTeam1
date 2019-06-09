package kr.ac.pnu.cse.menumapproject;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kr.ac.pnu.cse.menumapproject.model.Review;

public class ReviewRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Review> reviewList = new ArrayList<>();

    ReviewRecyclerAdapter(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
        Log.d("LOG_TAG", "ADD");
        for(Review review: reviewList) {
            Log.d("LOG_TAG", "review : " + review.restname);
            Log.d("LOG_TAG", "value : " + review.value);
        }
        notifyDataSetChanged();
    }

    public void addReview(Review review){
        reviewList.add(review);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReviewRecyclerViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.review_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof ReviewRecyclerViewHolder) {
            ((ReviewRecyclerViewHolder) viewHolder).setViewHolderItems(reviewList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    class ReviewRecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView idTextView;
        TextView reviewValueTextView;

        public ReviewRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            this.idTextView = itemView.findViewById(R.id.review_id_text_view);
            this.reviewValueTextView = itemView.findViewById(R.id.review_value_text_view);
        }

        public void setViewHolderItems(Review review) {
            idTextView.setText(review.id);
            reviewValueTextView.setText(review.value);
        }
    }
}
