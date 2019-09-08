package com.cm.airvedaasmt.event;

import com.cm.airvedaasmt.data.model.Feed;

public class FeedLikeStatusChangedEvent {

    private Feed feed;
    private boolean liked;
    private int position;

    public FeedLikeStatusChangedEvent(Feed feed, boolean liked, int position) {
        this.feed = feed;
        this.liked = liked;
        this.position = position;
    }

    public Feed getFeed() {
        return feed;
    }

    public boolean isLiked() {
        return liked;
    }

    public int getPosition() {
        return position;
    }
}
