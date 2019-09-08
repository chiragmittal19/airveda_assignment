package com.cm.airvedaasmt;

import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;

import com.cm.airvedaasmt.receiver.NetworkConnectivityReceiver;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        registerReceiver(new NetworkConnectivityReceiver(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        registerReceiver(new NetworkConnectivityReceiver(), new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));
    }
}
