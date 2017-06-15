package com.hdeva.space.service.notification;

import android.app.AlarmManager;
import android.content.Context;
import android.support.v4.app.NotificationManagerCompat;

import dagger.Module;
import dagger.Provides;

@Module
public class NotificationModule {

    @Provides
    AlarmManager provideAlarmManager(Context context) {
        return (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    @Provides
    NotificationManagerCompat provideNotificationManagerCompat(Context context) {
        return NotificationManagerCompat.from(context);
    }
}
