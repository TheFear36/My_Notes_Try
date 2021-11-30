package com.thefear.seconttrymynotes.domain;

public interface Callback<T> {
    void onSuccess(T result);

    void onError(Throwable error);
}
