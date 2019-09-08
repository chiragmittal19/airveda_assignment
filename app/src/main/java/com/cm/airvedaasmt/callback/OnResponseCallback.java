package com.cm.airvedaasmt.callback;

public interface OnResponseCallback<T> {

    void onSuccess(T t);
    void onFailure(Exception e);

}
