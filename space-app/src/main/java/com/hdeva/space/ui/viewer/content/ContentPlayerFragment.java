package com.hdeva.space.ui.viewer.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.hdeva.space.R;
import com.hdeva.space.base.BaseFragment;

import butterknife.BindView;

public class ContentPlayerFragment extends BaseFragment<ContentPlayerFragmentPresenter> implements ReleasableContentPlayer {

    private static final String MEDIA = "media";

    @BindView(R.id.content_exo_player_view)
    SimpleExoPlayerView exoPlayerView;

    String media;

    public static ContentPlayerFragment forMedia(String media) {
        ContentPlayerFragment fragment = new ContentPlayerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MEDIA, media);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        media = getArguments().getString(MEDIA);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content_player, container, false);
    }

    @Override
    public void release() {
        getPresenter().release();
    }
}
