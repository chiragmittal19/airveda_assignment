package com.cm.airvedaasmt.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cm.airvedaasmt.R;
import com.cm.airvedaasmt.callback.OnItemClickListener;
import com.cm.airvedaasmt.callback.OnResponseCallback;
import com.cm.airvedaasmt.data.DataManager;
import com.cm.airvedaasmt.data.model.Feed;
import com.cm.airvedaasmt.event.FeedLikeStatusChangedEvent;
import com.cm.airvedaasmt.event.NetworkConnectivityChangeEvent;
import com.cm.airvedaasmt.ui.adapter.FeedsAdapter;
import com.cm.airvedaasmt.util.Constants;
import com.cm.airvedaasmt.util.NetworkUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class FeedsActivity extends AppCompatActivity {

    private DataManager dataManager;

    private RecyclerView recyclerViewFeeds;
    private ProgressBar loadingIndicator;
    private TextView textViewError;
    private TextView textViewNoInternet;

    private FeedsAdapter adapter;

    private List<Feed> feeds;
    private List<Integer> likedFeedPositions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds);
        setTitle(getString(R.string.feeds));

        dataManager = DataManager.getInstance(this);
        feeds = new ArrayList<>();
        likedFeedPositions = new ArrayList<>();
        adapter = new FeedsAdapter(feeds, likedFeedPositions, new OnItemClickListener<Feed>() {
            @Override
            public void onItemClick(View view, Feed feed, int position) {
                switch (view.getId()){
                    case R.id.btn_feed_like:
                        if (likedFeedPositions.contains(position)){
                            likedFeedPositions.remove(Integer.valueOf(position));
                        } else {
                            likedFeedPositions.add(position);
                        }
                        adapter.notifyItemChanged(position);
                        break;
                    default:
                        Intent intent = new Intent(FeedsActivity.this, FeedDetailActivity.class);
                        intent.putExtra(Constants.KEY_FEED, feed);
                        intent.putExtra(Constants.KEY_FEED_LIKE_STATUS, likedFeedPositions.contains(position));
                        intent.putExtra(Constants.KEY_FEED_POSITION, position);
                        startActivity(intent);
                }
            }
        });

        loadingIndicator = findViewById(R.id.loading_indicator);
        textViewError = findViewById(R.id.text_view_error);
        textViewNoInternet = findViewById(R.id.text_view_no_internet);
        recyclerViewFeeds = findViewById(R.id.recycler_view_feeds);
        recyclerViewFeeds.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewFeeds.setAdapter(adapter);

        fetchFeeds();

        EventBus.getDefault().register(this);
        onNetworkConnectivityChangeEvent(new NetworkConnectivityChangeEvent());
    }

    private void fetchFeeds(){

        loadingIndicator.setVisibility(View.VISIBLE);
        textViewError.setVisibility(View.GONE);
        recyclerViewFeeds.setVisibility(View.GONE);
        dataManager.getFeeds(this, new OnResponseCallback<List<Feed>>() {
            @Override
            public void onSuccess(List<Feed> feeds) {
                loadingIndicator.setVisibility(View.GONE);
                textViewError.setVisibility(View.GONE);
                recyclerViewFeeds.setVisibility(View.VISIBLE);

                FeedsActivity.this.feeds.addAll(feeds);
                adapter.refresh();
            }

            @Override
            public void onFailure(Exception e) {
                loadingIndicator.setVisibility(View.GONE);
                textViewError.setVisibility(View.VISIBLE);
                recyclerViewFeeds.setVisibility(View.GONE);
                textViewError.setText(e.getLocalizedMessage());
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFeedLikeStatusChangedEvent(FeedLikeStatusChangedEvent event) {
        if (feeds != null && likedFeedPositions != null && adapter != null){
            int position = event.getPosition();
            if (position < 0){
                return;
            }
            if (event.isLiked()){
                if (!likedFeedPositions.contains(position)){
                    likedFeedPositions.add(position);
                    adapter.notifyItemChanged(position);
                }
            } else {
                if (likedFeedPositions.contains(position)){
                    likedFeedPositions.remove(Integer.valueOf(position));
                    adapter.notifyItemChanged(position);
                }
            }
        }
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
