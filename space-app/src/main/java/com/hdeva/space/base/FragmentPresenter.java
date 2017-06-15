package com.hdeva.space.base;

import android.support.annotation.CallSuper;

public class FragmentPresenter<F extends BaseFragment> implements Presenter<F> {

    protected F fragment;

    @CallSuper
    @Override
    public void attach(F fragment) {
        this.fragment = fragment;
    }

    @CallSuper
    @Override
    public void detach() {
        this.fragment = null;
    }

    @Override
    public final boolean isAttached() {
        return fragment != null;
    }
}
