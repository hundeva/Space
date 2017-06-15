package com.hdeva.space.ui.viewer.content;

import android.net.Uri;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.hdeva.space.base.FragmentPresenter;
import com.hdeva.space.core.service.AppInfoProvider;

import javax.inject.Inject;

class ContentPlayerFragmentPresenter extends FragmentPresenter<ContentPlayerFragment> {

    private SimpleExoPlayer exoPlayer;
    private MediaSource mediaSource;
    private AppInfoProvider appInfoProvider;

    @Inject
    public ContentPlayerFragmentPresenter(SimpleExoPlayer exoPlayer, AppInfoProvider appInfoProvider) {
        this.exoPlayer = exoPlayer;
        this.appInfoProvider = appInfoProvider;
    }

    @Override
    public void attach(ContentPlayerFragment fragment) {
        super.attach(fragment);
        attachPlayer();
    }

    @Override
    public void detach() {
        super.detach();
    }

    public void release() {
        exoPlayer.release();
    }

    private void attachPlayer() {
        if (mediaSource == null) {
            DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory(appInfoProvider.getUserAgent(), null, DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS, DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS, true);
            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(fragment.getContext(), null, httpDataSourceFactory);
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            mediaSource = new ExtractorMediaSource(Uri.parse(fragment.media), dataSourceFactory, extractorsFactory, null, null);
            exoPlayer.setPlayWhenReady(true);
            exoPlayer.prepare(mediaSource);
        }
        fragment.exoPlayerView.setPlayer(exoPlayer);
    }
}
