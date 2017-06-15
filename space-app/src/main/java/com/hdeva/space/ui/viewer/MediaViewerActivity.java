package com.hdeva.space.ui.viewer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;
import com.hdeva.space.R;
import com.hdeva.space.base.BaseActivity;
import com.hdeva.space.core.model.MediaDescriptor;

import org.parceler.Parcels;

import butterknife.BindView;

public class MediaViewerActivity extends BaseActivity<MediaViewerActivityPresenter> {

    public static final String DESCRIPTOR = "descriptor";
    public static final String FROM_NOTIFICATION = "fromNotification";

    MediaDescriptor descriptor;
    boolean fromNotification;

    @BindView(R.id.media_viewer_root)
    ViewGroup root;
    @BindView(R.id.media_viewer_photo_view)
    PhotoView photoView;
    @BindView(R.id.media_viewer_play_button)
    ImageView playButton;
    @BindView(R.id.media_viewer_top_overlay)
    ViewGroup topOverlay;
    @BindView(R.id.media_viewer_close)
    ImageView close;
    @BindView(R.id.media_viewer_title)
    TextView title;
    @BindView(R.id.media_viewer_sub_title)
    TextView subTitle;
    @BindView(R.id.media_viewer_share)
    ImageView share;
    @BindView(R.id.media_viewer_bottom_overlay)
    ViewGroup bottomOverlay;
    @BindView(R.id.media_viewer_description)
    TextView description;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportPostponeEnterTransition();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_viewer);
        descriptor = Parcels.unwrap(getIntent().getParcelableExtra(DESCRIPTOR));
        fromNotification = getIntent().getBooleanExtra(FROM_NOTIFICATION, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setImmersiveFullScreen();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        supportStartPostponedEnterTransition();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) presenter.setImmersiveFullScreen();
    }
}
