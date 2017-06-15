package com.hdeva.space.service.notification;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import javax.inject.Inject;

import dagger.android.DaggerBroadcastReceiver;

public class NotificationBootReceiver extends DaggerBroadcastReceiver {

    @Inject
    NotificationSchedulerService notificationSchedulerService;

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (TextUtils.equals(Intent.ACTION_BOOT_COMPLETED, intent.getAction())) {
            notificationSchedulerService.schedule();
        }
    }

}
