package com.hdeva.space.service;

import android.os.Build;
import android.view.View;

import com.hdeva.space.base.BaseActivity;

import javax.inject.Inject;

public class FullScreenHelper {

    @Inject
    public FullScreenHelper() {
    }

    public void setImmersiveFullScreen(BaseActivity activity) {
        int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            flags |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        activity.getWindow().getDecorView().setSystemUiVisibility(flags);
    }
}
