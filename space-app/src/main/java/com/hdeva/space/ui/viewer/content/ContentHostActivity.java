package com.hdeva.space.ui.viewer.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.hdeva.space.R;
import com.hdeva.space.base.BaseActivity;
import com.hdeva.space.core.model.MediaDescriptor;

import org.parceler.Parcels;

import butterknife.BindView;

public class ContentHostActivity extends BaseActivity<ContentHostActivityPresenter> {

    public static final String DESCRIPTOR = "descriptor";

    @Nullable
    @BindView(R.id.content_host_title)
    TextView title;
    @Nullable
    @BindView(R.id.content_host_sub_title)
    TextView subTitle;
    @Nullable
    @BindView(R.id.content_host_description)
    TextView description;

    MediaDescriptor descriptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_host);
        descriptor = Parcels.unwrap(getIntent().getParcelableExtra(DESCRIPTOR));
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setImmersiveFullScreen();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) presenter.setImmersiveFullScreen();
    }

    @Override
    public void finish() {
        presenter.cleanup();
        super.finish();
    }
}
