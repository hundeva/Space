package com.hdeva.space.ui.other;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.RadioButton;

import com.hdeva.space.R;
import com.hdeva.space.base.BaseActivity;

import butterknife.BindView;

public class SettingsActivity extends BaseActivity<SettingsActivityPresenter> {

    @BindView(R.id.settings_quality_original)
    RadioButton original;
    @BindView(R.id.settings_quality_high_definition)
    RadioButton highDefinition;
    @BindView(R.id.settings_quality_standard)
    RadioButton standard;
    @BindView(R.id.settings_quality_low_quality)
    RadioButton lowQuality;
    @BindView(R.id.settings_apod_notification_late)
    RadioButton apodNotificationLate;
    @BindView(R.id.settings_apod_notification_early)
    RadioButton apodNotificationEarly;
    @BindView(R.id.settings_apod_notification_never)
    RadioButton apodNotificationNever;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, SettingsActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        enableActionBarBack();
    }
}
