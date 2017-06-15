package com.hdeva.space.service.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.hdeva.space.core.service.DateService;
import com.hdeva.space.core.service.notification.NotificationPolicy;
import com.hdeva.space.core.service.notification.NotificationService;

import javax.inject.Inject;

public class NotificationSchedulerService {

    private static final int NOTIFICATION_REQUEST = 1337;

    private Context context;
    private NotificationService notificationService;
    private AlarmManager alarmManager;
    private DateService dateService;

    @Inject
    public NotificationSchedulerService(Context context, NotificationService notificationService, AlarmManager alarmManager, DateService dateService) {
        this.context = context;
        this.notificationService = notificationService;
        this.alarmManager = alarmManager;
        this.dateService = dateService;
    }

    public void schedule() {
        switch (notificationService.getNotificationPolicy()) {
            case NotificationPolicy.LATE_IN_THE_DAY:
                doSchedule(18, 0);
                break;
            case NotificationPolicy.EARLY_IN_THE_DAY:
                doSchedule(7, 30);
                break;
            case NotificationPolicy.NEVER:
                cancel();
                break;
        }
    }

    private void doSchedule(int hour, int minute) {
        alarmManager.setInexactRepeating(AlarmManager.RTC, dateService.getNotificationSchedulerDateInMillis(hour, minute), AlarmManager.INTERVAL_DAY, createAlarmPendingIntent());
    }

    private void cancel() {
        alarmManager.cancel(createAlarmPendingIntent());
    }

    private PendingIntent createAlarmPendingIntent() {
        return PendingIntent.getService(context, NOTIFICATION_REQUEST, new Intent(context, NotificationIntentService.class), PendingIntent.FLAG_CANCEL_CURRENT);
    }

}
