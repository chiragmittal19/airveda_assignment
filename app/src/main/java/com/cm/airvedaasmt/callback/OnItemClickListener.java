package com.cm.airvedaasmt.callback;

import android.view.View;

public interface OnItemClickListener<T> {

    void onItemClick(View view, T t, int position);

}