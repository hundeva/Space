package com.hdeva.space.ui.viewer.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.hdeva.space.R;

import butterknife.ButterKnife;

public class YouTubeContentFragment extends YouTubePlayerSupportFragment implements ReleasableContentPlayer {

    private static final String VIDEO = "video";
    private static final String PLAYING = "playing";

    private String video;
    private boolean playing;
    private YouTubePlayer youTubePlayer;

    private YouTubePlayer.OnInitializedListener listener = new YouTubePlayer.OnInitializedListener() {

        @Override
        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer newYouTubePlayer, boolean wasRestored) {
            youTubePlayer = newYouTubePlayer;
            handleYouTubePlayerInitialized(wasRestored);
        }

        @Override
        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            Toast.makeText(getContext(), R.string.failed_to_start_youtube_video, Toast.LENGTH_SHORT).show();
        }
    };

    public YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {
            playing = true;
        }

        @Override
        public void onPaused() {
            playing = false;
        }

        @Override
        public void onStopped() {
            playing = false;
        }

        @Override
        public void onBuffering(boolean b) {
        }

        @Override
        public void onSeekTo(int i) {
        }
    };

    public static YouTubeContentFragment forVideo(String video) {
        YouTubeContentFragment fragment = new YouTubeContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString(VIDEO, video);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        video = getArguments().getString(VIDEO);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        playing = savedInstanceState != null && savedInstanceState.getBoolean(PLAYING);
        ButterKnife.bind(this, view);
        initialize(getString(R.string.youtube_api_key), listener);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean(PLAYING, playing);
    }

    @Override
    public void release() {
        if (youTubePlayer != null) {
            youTubePlayer.release();
        }
    }

    private void handleYouTubePlayerInitialized(boolean wasRestored) {
        youTubePlayer.setPlaybackEventListener(playbackEventListener);
        if (!wasRestored) {
            youTubePlayer.loadVideo(video);
        } else if (playing) {
            youTubePlayer.play();
        }
    }
}
