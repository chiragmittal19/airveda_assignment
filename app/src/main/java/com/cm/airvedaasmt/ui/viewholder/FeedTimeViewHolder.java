package com.cm.airvedaasmt.ui.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cm.airvedaasmt.R;

public class FeedTimeViewHolder extends RecyclerView.ViewHolder {

    private TextView textFeedTime;

    public FeedTimeViewHolder(@NonNull View itemView) {
        super(itemView);

        textFeedTime = itemView.findViewById(R.id.text_feed_time);
    }

    public void setTime(String time){
        textFeedTime.setText(time);
    }
}
