package com.hdeva.space.ui.other;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.hdeva.space.R;
import com.hdeva.space.base.BaseActivity;

import butterknife.BindView;

public class AboutActivity extends BaseActivity<AboutActivityPresenter> {

    public static void launch(Context context) {
        context.startActivity(new Intent(context, AboutActivity.class));
    }

    @BindView(R.id.about_app_version)
    TextView appVersion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        enableActionBarBack();
    }
}
