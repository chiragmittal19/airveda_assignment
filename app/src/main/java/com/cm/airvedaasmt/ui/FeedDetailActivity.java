package com.cm.airvedaasmt.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cm.airvedaasmt.R;
import com.cm.airvedaasmt.data.model.Feed;
import com.cm.airvedaasmt.event.FeedLikeStatusChangedEvent;
import com.cm.airvedaasmt.event.NetworkConnectivityChangeEvent;
import com.cm.airvedaasmt.util.Constants;
import com.cm.airvedaasmt.util.NetworkUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class FeedDetailActivity extends AppCompatActivity {

    private Feed feed;
    private boolean liked;
    private int position;

    private TextView textFeedTitle;
    private TextView textFeedDescription;
    private LinearLayout layoutFeedRow;
    private TextView textViewNoInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_detail);
        setTitle(getString(R.string.feed));

        feed = getIntent().getParcelableExtra(Constants.KEY_FEED);
        liked = getIntent().getBooleanExtra(Constants.KEY_FEED_LIKE_STATUS, false);
        position = getIntent().getIntExtra(Constants.KEY_FEED_POSITION, -1);

        textFeedTitle = findViewById(R.id.text_feed_title);
        textFeedDescription = findViewById(R.id.text_feed_description);
        layoutFeedRow = findViewById(R.id.layout_feed_row);
        textViewNoInternet = findViewById(R.id.text_view_no_internet);

        textFeedTitle.setText(feed.getTitle());
        textFeedDescription.setText(feed.getDescription());

        setFeedView();

        EventBus.getDefault().register(this);
        onNetworkConnectivityChangeEvent(new NetworkConnectivityChangeEvent());
    }

    private void setFeedView() {

        boolean imageEmpty = TextUtils.isEmpty(feed.getImageUrl());
        boolean textEmpty = TextUtils.isEmpty(feed.getText());
        if (!imageEmpty && !textEmpty){

            View view = LayoutInflater.from(this)
                    .inflate(R.layout.row_feed_image_text, layoutFeedRow, false);
            view.setClickable(false);
            view.findViewById(R.id.text_feed_title).setVisibility(View.GONE);
            Glide.with(this)
                    .load(feed.getImageUrl())
                    .placeholder(R.drawable.placeholder_image)
                    .into((ImageView) view.findViewById(R.id.image_feed));
            ((TextView) view.findViewById(R.id.text_feed_text)).setText(feed.getText());
            ((TextView) view.findViewById(R.id.text_feed_name)).setText(String.format("From %s", feed.getName()));
            setLikeButton((Button) view.findViewById(R.id.btn_feed_like));

            view.findViewById(R.id.btn_feed_like).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    liked = !liked;
                    setLikeButton((Button) v);
                    EventBus.getDefault().post(new FeedLikeStatusChangedEvent(feed, liked, position));
                }
            });

            layoutFeedRow.addView(view);

        } else if (!imageEmpty){

            View view = LayoutInflater.from(this)
                    .inflate(R.layout.row_feed_image, layoutFeedRow, false);
            view.setClickable(false);
            view.findViewById(R.id.text_feed_title).setVisibility(View.GONE);
            Glide.with(this)
                    .load(feed.getImageUrl())
                    .placeholder(R.drawable.placeholder_image)
                    .into((ImageView) view.findViewById(R.id.image_feed));
            ((TextView) view.findViewById(R.id.text_feed_name)).setText(String.format("From %s", feed.getName()));
            setLikeButton((Button) view.findViewById(R.id.btn_feed_like));

            view.findViewById(R.id.btn_feed_like).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    liked = !liked;
                    setLikeButton((Button) v);
                    EventBus.getDefault().post(new FeedLikeStatusChangedEvent(feed, liked, position));
                }
            });

            layoutFeedRow.addView(view);

        } else {

            View view = LayoutInflater.from(this)
                    .inflate(R.layout.row_feed_text, layoutFeedRow, false);
            view.setClickable(false);
            view.findViewById(R.id.text_feed_title).setVisibility(View.GONE);
            ((TextView) view.findViewById(R.id.text_feed_text)).setText(feed.getText());
            ((TextView) view.findViewById(R.id.text_feed_name)).setText(String.format("From %s", feed.getName()));
            setLikeButton((Button) view.findViewById(R.id.btn_feed_like));

            view.findViewById(R.id.btn_feed_like).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    liked = !liked;
                    setLikeButton((Button) v);
                    EventBus.getDefault().post(new FeedLikeStatusChangedEvent(feed, liked, position));
                }
            });

            layoutFeedRow.addView(view);

        }

    }

    private void setLikeButton(Button likeButton) {
        likeButton.setSelected(liked);
        likeButton.setText(liked ? "Unlike" : "Like");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetworkConnectivityChangeEvent(NetworkConnectivityChangeEvent event){
        if (NetworkUtil.isConnected(this)){
            textViewNoInternet.setVisibility(View.GONE);
        } else {
            textViewNoInternet.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
