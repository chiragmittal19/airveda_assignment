package com.cm.airvedaasmt.data.remote;

import com.cm.airvedaasmt.data.model.Feed;

import java.util.List;

import retrofit2.Call;

public class AppApi {

    private AppService appService;

    public AppApi() {
        appService = RetrofitClient.getInstance()
                .create(AppService.class);
    }

    public Call<List<Feed>> getFeeds(){
        return appService.getFeeds();
    }
}
