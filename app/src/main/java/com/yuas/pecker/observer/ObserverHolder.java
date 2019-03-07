package com.yuas.pecker.observer;

import io.reactivex.disposables.Disposable;
import org.reactivestreams.Subscription;

public interface ObserverHolder {

    /**
     * 添加Disposable
     */

     void addDisposable(Disposable disposable);

    /**
     * 添加Subcription
     */

    void addSubscription(Subscription subscription);

    /**
     * 移除 subscription
     */

    void removeDisposable(Disposable disposable);
}
