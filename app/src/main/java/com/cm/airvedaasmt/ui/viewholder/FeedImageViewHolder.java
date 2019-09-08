package com.cm.airvedaasmt.ui.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cm.airvedaasmt.R;

public class FeedImageViewHolder extends RecyclerView.ViewHolder {

    private TextView textFeedTitle;
    private ImageView imageFeed;
    private Button btnFeedLike;
    private TextView textFeedName;

    public FeedImageViewHolder(@NonNull View itemView) {
        super(itemView);

        textFeedTitle = itemView.findViewById(R.id.text_feed_title);
        imageFeed = itemView.findViewById(R.id.image_feed);
        btnFeedLike = itemView.findViewById(R.id.btn_feed_like);
        textFeedName = itemView.findViewById(R.id.text_feed_name);
    }

    public void setTitle(String title){
        textFeedTitle.setText(title);
    }

    public void setImage(String imageUrl){
        Glide.with(itemView.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.placeholder_image)
                .into(imageFeed);
    }

    public void setLike(boolean liked){
        btnFeedLike.setSelected(liked);
        btnFeedLike.setText(liked ? "Unlike" : "Like");
    }

    public void setName(String name){
        textFeedName.setText(String.format("From %s", name));
    }

    public Button getBtnFeedLike() {
        return btnFeedLike;
    }
}
