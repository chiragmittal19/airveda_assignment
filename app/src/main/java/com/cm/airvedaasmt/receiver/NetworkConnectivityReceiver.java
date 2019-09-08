package com.cm.airvedaasmt.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.cm.airvedaasmt.event.NetworkConnectivityChangeEvent;

import org.greenrobot.eventbus.EventBus;

public class NetworkConnectivityReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("_____TAG_____", "Connectivity changed");
        EventBus.getDefault().postSticky(new NetworkConnectivityChangeEvent());
    }
}
