package com.hdeva.space.ui.adapter.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.gms.ads.NativeExpressAdView;
import com.hdeva.space.R;
import com.hdeva.space.base.BaseViewHolder;

import butterknife.BindView;

public class LargeNativeAdViewHolder extends BaseViewHolder {

    @BindView(R.id.ad_container)
    public ViewGroup container;
    @BindView(R.id.ad_progress)
    public ProgressBar progressBar;
    @BindView(R.id.ad_view)
    public NativeExpressAdView adView;

    public LargeNativeAdViewHolder(View itemView) {
        super(itemView);
    }
}
