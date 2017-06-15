package com.hdeva.space.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.hdeva.space.R;
import com.hdeva.space.ui.helper.AdHelper;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.DaggerAppCompatActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class BaseActivity<P extends Presenter> extends DaggerAppCompatActivity {

    public P presenter;

    @Nullable
    @BindView(R.id.support_toolbar)
    Toolbar supportToolbar;
    @Nullable
    @BindView(R.id.ad_view)
    AdView adView;

    @Inject
    DispatchingAndroidInjector<Fragment> injector;
    @Inject
    Provider<P> provider;
    @Inject
    AdHelper adHelper;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        setupPresenter();
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        presenter.detach();
        super.onDestroy();
    }

    @Override
    public final Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }

    @Override
    public final Object getLastCustomNonConfigurationInstance() {
        return super.getLastCustomNonConfigurationInstance();
    }

    @CallSuper
    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        setSupportActionBar(supportToolbar);
        adHelper.setupAd(adView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean ret;
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            ret = true;
        } else {
            ret = super.onOptionsItemSelected(item);
        }
        return ret;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Nullable
    public Toolbar getSupportToolbar() {
        return supportToolbar;
    }

    protected void enableActionBarBack() {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @SuppressWarnings("unchecked")
    private void setupPresenter() {
        presenter = (P) getLastCustomNonConfigurationInstance();
        if (presenter == null) {
            presenter = provider.get();
        }

        presenter.attach(this);
    }
}
