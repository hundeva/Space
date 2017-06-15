package com.hdeva.space.ui.helper;

import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.hdeva.space.BuildConfig;
import com.hdeva.space.ui.adapter.holder.LargeNativeAdViewHolder;

import javax.inject.Inject;

public class AdHelper {

    private static final int AD_FREQUENCY = 10;

    @Inject
    public AdHelper() {
    }

    public void setupAd(AdView adView) {
        if (adView != null) {
            if (shouldShowAds()) {
                adView.loadAd(new AdRequest.Builder().build());
            } else {
                adView.setVisibility(View.GONE);
            }
        }
    }

    public void bind(LargeNativeAdViewHolder holder) {
        if (shouldShowAds()) {
            holder.progressBar.setVisibility(View.VISIBLE);
            holder.adView.setVisibility(View.INVISIBLE);

            holder.adView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    holder.progressBar.setVisibility(View.GONE);
                    holder.adView.setVisibility(View.VISIBLE);
                }
            });

            holder.adView.loadAd(new AdRequest.Builder().build());
        } else {
            holder.container.setVisibility(View.GONE);
        }
    }

    public boolean shouldShowAds() {
        return !BuildConfig.DEBUG;
    }

    public boolean frequency(int index) {
        return index % AD_FREQUENCY == 0;
    }
}
