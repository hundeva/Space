package com.hdeva.space.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

public abstract class BaseFragment<P extends FragmentPresenter> extends DaggerFragment {

    private static final String PRESENTER_TAG = "presenter";

    @Inject
    Provider<P> provider;

    PresenterHolder<P> fragment;

    @CallSuper
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setupPresenter();
    }

    @Override
    public void onDestroy() {
        fragment.presenter.detach();
        super.onDestroy();
    }

    @SuppressWarnings("unchecked")
    private void setupPresenter() {
        FragmentManager fragmentManager = getChildFragmentManager();
        fragment = (PresenterHolder<P>) fragmentManager.findFragmentByTag(PRESENTER_TAG);
        if (fragment == null) {
            fragment = new PresenterHolder<>();
            fragment.presenter = provider.get();

            fragmentManager
                    .beginTransaction()
                    .add(fragment, PRESENTER_TAG)
                    .commitNow();
        } else if (fragment.presenter == null) {
            fragment.presenter = provider.get();
        }

        fragment.presenter.attach(this);
    }

    protected P getPresenter() {
        return fragment.presenter;
    }

    public static class PresenterHolder<P> extends Fragment {

        public P presenter;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }

        @Nullable
        @Override
        public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return null;
        }
    }
}
