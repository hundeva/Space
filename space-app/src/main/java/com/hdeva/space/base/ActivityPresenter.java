package com.hdeva.space.base;

import android.support.annotation.CallSuper;

public class ActivityPresenter<A extends BaseActivity> implements Presenter<A> {

    protected A activity;

    @Override
    @CallSuper
    public void attach(A activity) {
        this.activity = activity;
    }

    @CallSuper
    @Override
    public void detach() {
        this.activity = null;
    }

    @Override
    public boolean isAttached() {
        return activity != null;
    }
}
