package com.hdeva.space.ui.viewer;

import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;

import com.hdeva.space.base.ActivityPresenter;
import com.hdeva.space.core.helper.GlideHelper;
import com.hdeva.space.core.model.images.MediaType;
import com.hdeva.space.core.service.extractor.VideoLinkHelper;
import com.hdeva.space.service.ContentShareHelper;
import com.hdeva.space.service.FullScreenHelper;
import com.hdeva.space.ui.home.HomeActivity;
import com.hdeva.space.ui.navigator.MediaViewerNavigator;

import javax.inject.Inject;

class MediaViewerActivityPresenter extends ActivityPresenter<MediaViewerActivity> {

    private FullScreenHelper fullScreenHelper;
    private GlideHelper glideHelper;
    private ContentShareHelper contentShareHelper;
    private VideoLinkHelper videoLinkHelper;
    private MediaViewerNavigator mediaViewerNavigator;

    private AlertDialog dialog;
    private boolean overlayHidden;

    private boolean isDialogShowing;

    @Inject
    public MediaViewerActivityPresenter(FullScreenHelper fullScreenHelper, GlideHelper glideHelper, ContentShareHelper contentShareHelper, VideoLinkHelper videoLinkHelper, MediaViewerNavigator mediaViewerNavigator) {
        this.fullScreenHelper = fullScreenHelper;
        this.glideHelper = glideHelper;
        this.contentShareHelper = contentShareHelper;
        this.videoLinkHelper = videoLinkHelper;
        this.mediaViewerNavigator = mediaViewerNavigator;
    }

    @Override
    public void attach(MediaViewerActivity activity) {
        super.attach(activity);
        setupItem();
        enablePlayButtonIfNeeded();
        activity.close.setOnClickListener(v -> handleCloseAction());
        activity.share.setOnClickListener(v -> contentShareHelper.shareContent(activity.descriptor));
        activity.photoView.setOnClickListener(v -> toggleOverlay());
        activity.playButton.setOnClickListener(v -> mediaViewerNavigator.openMedia(activity, activity.descriptor));
        activity.bottomOverlay.setOnClickListener(v -> dialog.show());
        activity.root.post(() -> refreshUi(false));
    }

    @Override
    public void detach() {
        if (dialog != null) {
            isDialogShowing = dialog.isShowing();
            dialog.dismiss();
            dialog = null;
        }
        super.detach();
    }

    public void setImmersiveFullScreen() {
        fullScreenHelper.setImmersiveFullScreen(activity);
    }

    private void handleCloseAction() {
        if (activity.fromNotification) {
            activity.finish();
            activity.startActivity(new Intent(activity, HomeActivity.class));
        } else {
            activity.onBackPressed();
        }
    }

    private void toggleOverlay() {
        overlayHidden = !overlayHidden;
        refreshUi(true);
    }

    private void setupItem() {
        activity.title.setText(activity.descriptor.title);
        activity.subTitle.setText(activity.descriptor.subTitle);
        activity.description.setText(activity.descriptor.description);
        glideHelper.setImage(activity.photoView, activity.descriptor.thumbnail);

        dialog = new AlertDialog.Builder(activity)
                .setTitle(activity.descriptor.title)
                .setMessage(activity.descriptor.description)
                .create();

        if (isDialogShowing) dialog.show();
    }

    private void refreshUi(boolean shouldAnimate) {
        float offset = overlayHidden ? 1.0f : 0.0f;
        float topOffset = -offset * activity.topOverlay.getMeasuredHeight();
        float bottomOffset = offset * activity.bottomOverlay.getMeasuredHeight();
        float alphaOffset = 1 - offset;

        if (shouldAnimate) {
            ViewCompat.animate(activity.topOverlay).translationY(topOffset).alpha(alphaOffset).start();
            ViewCompat.animate(activity.bottomOverlay).translationY(bottomOffset).alpha(alphaOffset).start();
        } else {
            activity.topOverlay.setTranslationY(topOffset);
            activity.topOverlay.setAlpha(alphaOffset);
            activity.bottomOverlay.setTranslationY(bottomOffset);
            activity.bottomOverlay.setAlpha(alphaOffset);
        }
    }

    private void enablePlayButtonIfNeeded() {
        boolean shouldShow;
        if (videoLinkHelper.isYouTubeVideo(activity.descriptor.media)) {
            shouldShow = true;
        } else if (videoLinkHelper.isVideo(activity.descriptor.media)) {
            shouldShow = true;
        } else if (TextUtils.equals(MediaType.AUDIO, activity.descriptor.mediaType)) {
            shouldShow = true;
        } else {
            shouldShow = false;
        }

        activity.playButton.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
    }
}
