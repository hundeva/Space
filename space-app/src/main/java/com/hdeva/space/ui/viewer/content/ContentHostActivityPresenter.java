package com.hdeva.space.ui.viewer.content;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;

import com.hdeva.space.R;
import com.hdeva.space.base.ActivityPresenter;
import com.hdeva.space.core.model.images.MediaType;
import com.hdeva.space.core.service.extractor.VideoLinkHelper;
import com.hdeva.space.service.FullScreenHelper;

import javax.inject.Inject;

class ContentHostActivityPresenter extends ActivityPresenter<ContentHostActivity> {

    private FullScreenHelper fullScreenHelper;
    private VideoLinkHelper videoLinkHelper;

    @Inject
    public ContentHostActivityPresenter(FullScreenHelper fullScreenHelper, VideoLinkHelper videoLinkHelper) {
        this.fullScreenHelper = fullScreenHelper;
        this.videoLinkHelper = videoLinkHelper;
    }

    @Override
    public void attach(ContentHostActivity activity) {
        super.attach(activity);
        setupTexts();
        setupContentFrame();
    }

    public void setImmersiveFullScreen() {
        fullScreenHelper.setImmersiveFullScreen(activity);
    }

    public void cleanup() {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.content_host_frame);
        if (fragment != null && fragment instanceof ReleasableContentPlayer) {
            ((ReleasableContentPlayer) fragment).release();
        }
    }

    private void setupTexts() {
        if (activity.title != null)
            activity.title.setText(activity.descriptor.title);

        if (activity.subTitle != null)
            activity.subTitle.setText(activity.descriptor.subTitle);

        if (activity.description != null)
            activity.description.setText(activity.descriptor.description);
    }

    private void setupContentFrame() {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        if (restoreContentFragmentIfAny(fragmentManager)) return;
        Fragment fragment = createContentFragment();
        if (fragment != null) pushContentFragment(fragmentManager, fragment);
    }

    private Fragment createContentFragment() {
        Fragment fragment;
        if (videoLinkHelper.isYouTubeVideo(activity.descriptor.media)) {
            fragment = createYouTubePlayerFragment();
        } else if (videoLinkHelper.isVideo(activity.descriptor.media)) {
            fragment = setupVideoPlayerFragment();
        } else if (TextUtils.equals(MediaType.AUDIO, activity.descriptor.mediaType)) {
            fragment = setupAudioPlayerFragment();
        } else {
            fragment = null;
        }
        return fragment;
    }

    private boolean restoreContentFragmentIfAny(FragmentManager fragmentManager) {
        boolean restored = false;
        Fragment fragment = fragmentManager.findFragmentById(R.id.content_host_frame);
        if (fragment != null) {
            restored = true;
        }
        return restored;
    }

    private Fragment createYouTubePlayerFragment() {
        String youTubeId = videoLinkHelper.extractYouTubeId(activity.descriptor.media);
        return YouTubeContentFragment.forVideo(youTubeId);
    }

    private Fragment setupVideoPlayerFragment() {
        return ContentPlayerFragment.forMedia(activity.descriptor.media);
    }

    private Fragment setupAudioPlayerFragment() {
        return ContentPlayerFragment.forMedia(activity.descriptor.media);
    }

    private void pushContentFragment(FragmentManager fragmentManager, Fragment fragment) {
        fragmentManager
                .beginTransaction()
                .replace(R.id.content_host_frame, fragment)
                .commitNowAllowingStateLoss();
    }
}
