package com.cm.airvedaasmt.data;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import com.cm.airvedaasmt.R;
import com.cm.airvedaasmt.callback.OnResponseCallback;
import com.cm.airvedaasmt.data.local.AppDatabase;
import com.cm.airvedaasmt.data.model.Feed;
import com.cm.airvedaasmt.data.remote.AppApi;
import com.cm.airvedaasmt.util.Constants;
import com.cm.airvedaasmt.util.NetworkUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataManager {

    private static DataManager dataManager;

    private AppDatabase db;
    private AppApi api;

    private DataManager(Context context) {
        db = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                Constants.DATABASE_NAME).build();
        api = new AppApi();
    }

    public static DataManager getInstance(Context context){
        if (dataManager == null){
            dataManager = new DataManager(context);
        }
        return dataManager;
    }

    public void getFeeds(final Context context, final OnResponseCallback<List<Feed>> callback){

        if (NetworkUtil.isConnected(context)){

            api.getFeeds().enqueue(new Callback<List<Feed>>() {
                @Override
                public void onResponse(Call<List<Feed>> call, Response<List<Feed>> response) {
                    final List<Feed> feeds = response.body();
                    if (feeds == null || feeds.isEmpty()){
                        callback.onFailure(new Exception(context.getString(R.string.feeds_not_available)));
                    } else {

                        Collections.sort(feeds, new Comparator<Feed>() {
                            @Override
                            public int compare(Feed o1, Feed o2) {
                                return Long.compare(o1.getTime(), o2.getTime());
                            }
                        });

                        new Thread(){
                            @Override
                            public void run() {
                                db.feedDao().deleteAll();
                                db.feedDao().insert(feeds);
                            }
                        }.start();

                        callback.onSuccess(feeds);
                    }
                }

                @Override
                public void onFailure(Call<List<Feed>> call, Throwable t) {
                    getFeedsFromLocal(callback, new Exception(t.getMessage()));
                }
            });

        } else {
            getFeedsFromLocal(callback, new Exception(context.getString(R.string.no_internet)));
        }

    }

    private void getFeedsFromLocal(final OnResponseCallback<List<Feed>> callback, final Exception e){

        new AsyncTask<Void, Void, List<Feed>>() {
            @Override
            protected List<Feed> doInBackground(Void... voids) {
                return db.feedDao().getAll();
            }

            @Override
            protected void onPostExecute(List<Feed> feeds) {
                super.onPostExecute(feeds);

                if (feeds == null || feeds.isEmpty()){
                    callback.onFailure(e);
                } else {
                    Collections.sort(feeds, new Comparator<Feed>() {
                        @Override
                        public int compare(Feed o1, Feed o2) {
                            return Long.compare(o1.getTime(), o2.getTime());
                        }
                    });
                    callback.onSuccess(feeds);
                }
            }
        }.execute();

    }

}
