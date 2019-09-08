package com.cm.airvedaasmt.data.remote;

import com.cm.airvedaasmt.data.model.Feed;
import com.cm.airvedaasmt.util.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AppService {

    @GET(Constants.URL_GET_FEEDS)
    Call<List<Feed>> getFeeds();

}
