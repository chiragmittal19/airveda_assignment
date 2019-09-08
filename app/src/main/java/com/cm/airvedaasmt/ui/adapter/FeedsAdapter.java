package com.cm.airvedaasmt.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cm.airvedaasmt.R;
import com.cm.airvedaasmt.callback.OnItemClickListener;
import com.cm.airvedaasmt.data.model.Feed;
import com.cm.airvedaasmt.ui.viewholder.FeedImageTextViewHolder;
import com.cm.airvedaasmt.ui.viewholder.FeedImageViewHolder;
import com.cm.airvedaasmt.ui.viewholder.FeedTextViewHolder;
import com.cm.airvedaasmt.ui.viewholder.FeedTimeViewHolder;

import java.util.ArrayList;
import java.util.List;

public class FeedsAdapter extends RecyclerView.Adapter {

    private static final int TYPE_IMAGE_TEXT = 1;
    private static final int TYPE_IMAGE = 2;
    private static final int TYPE_TEXT = 3;
    private static final int TYPE_TIME = 4;

    private List<Feed> feeds;
    private List<Integer> likedFeedIds;
    private List<Object> objects;

    private OnItemClickListener<Feed> listener;

    public FeedsAdapter(List<Feed> feeds, List<Integer> likedFeedIds, OnItemClickListener<Feed> listener) {
        this.feeds = feeds;
        this.likedFeedIds = likedFeedIds;
        this.listener = listener;
        objects = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i){
            case TYPE_IMAGE_TEXT:
                return new FeedImageTextViewHolder(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.row_feed_image_text, viewGroup, false));
            case TYPE_IMAGE:
                return new FeedImageViewHolder(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.row_feed_image, viewGroup, false));
            case TYPE_TEXT:
                return new FeedTextViewHolder(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.row_feed_text, viewGroup, false));
            default:
                return new FeedTimeViewHolder(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.row_feed_time, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Object object = objects.get(i);

        if (viewHolder instanceof FeedImageTextViewHolder){
            final FeedImageTextViewHolder holder = (FeedImageTextViewHolder) viewHolder;
            final Feed feed = (Feed) object;

            holder.setTitle(feed.getTitle());
            holder.setImage(feed.getImageUrl());
            holder.setText(feed.getText());
            holder.setLike(likedFeedIds.contains(i));
            holder.setName(feed.getName());

            holder.getBtnFeedLike().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        listener.onItemClick(v, feed, holder.getAdapterPosition());
                    }
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        listener.onItemClick(v, feed, holder.getAdapterPosition());
                    }
                }
            });

        } else if (viewHolder instanceof FeedImageViewHolder){
            final FeedImageViewHolder holder = (FeedImageViewHolder) viewHolder;
            final Feed feed = (Feed) object;

            holder.setTitle(feed.getTitle());
            holder.setImage(feed.getImageUrl());
            holder.setLike(likedFeedIds.contains(i));
            holder.setName(feed.getName());

            holder.getBtnFeedLike().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        listener.onItemClick(v, feed, holder.getAdapterPosition());
                    }
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        listener.onItemClick(v, feed, holder.getAdapterPosition());
                    }
                }
            });

        } else if (viewHolder instanceof FeedTextViewHolder){
            final FeedTextViewHolder holder = (FeedTextViewHolder) viewHolder;
            final Feed feed = (Feed) object;

            holder.setTitle(feed.getTitle());
            holder.setText(feed.getText());
            holder.setLike(likedFeedIds.contains(i));
            holder.setName(feed.getName());

            holder.getBtnFeedLike().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        listener.onItemClick(v, feed, holder.getAdapterPosition());
                    }
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        listener.onItemClick(v, feed, holder.getAdapterPosition());
                    }
                }
            });

        } else {
            FeedTimeViewHolder holder = (FeedTimeViewHolder) viewHolder;
            holder.setTime(object.toString());
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object object = objects.get(position);
        if (object instanceof Feed) {
            Feed feed = (Feed) object;
            boolean imageEmpty = TextUtils.isEmpty(feed.getImageUrl());
            boolean textEmpty = TextUtils.isEmpty(feed.getText());
            if (!imageEmpty && !textEmpty){
                return TYPE_IMAGE_TEXT;
            } else if (!imageEmpty){
                return TYPE_IMAGE;
            } else {
                return TYPE_TEXT;
            }
        } else {
            return TYPE_TIME;
        }
    }

    @Override
    public int getItemCount() {
        return objects == null ? 0 : objects.size();
    }

    public void refresh(){
        if (feeds != null && !feeds.isEmpty()){
            for (Feed feed: feeds){
                if (!objects.contains(String.valueOf(feed.getTime()))){
                    objects.add(String.valueOf(feed.getTime()));
                }
                objects.add(feed);
            }
        }

        notifyDataSetChanged();
    }
}
