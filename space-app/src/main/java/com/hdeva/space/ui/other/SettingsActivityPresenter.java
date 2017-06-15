package com.hdeva.space.ui.other;

import com.hdeva.space.base.ActivityPresenter;
import com.hdeva.space.core.service.notification.NotificationPolicy;
import com.hdeva.space.core.service.notification.NotificationService;
import com.hdeva.space.core.service.quality.MediaQualityPolicy;
import com.hdeva.space.core.service.quality.MediaQualityService;
import com.hdeva.space.service.notification.NotificationSchedulerService;

import javax.inject.Inject;

class SettingsActivityPresenter extends ActivityPresenter<SettingsActivity> {

    private MediaQualityService mediaQualityService;
    private NotificationService notificationService;
    private NotificationSchedulerService notificationSchedulerService;

    @Inject
    public SettingsActivityPresenter(MediaQualityService mediaQualityService, NotificationService notificationService, NotificationSchedulerService notificationSchedulerService) {
        this.mediaQualityService = mediaQualityService;
        this.notificationService = notificationService;
        this.notificationSchedulerService = notificationSchedulerService;
    }

    @Override
    public void attach(SettingsActivity activity) {
        super.attach(activity);
        setupQualitySettings(activity);
        setupNotificationSettings(activity);
    }

    private void setupQualitySettings(SettingsActivity activity) {
        switch (mediaQualityService.getImageQualityPolicy()) {
            case MediaQualityPolicy.ORIGINAL:
                activity.original.setChecked(true);
                break;
            case MediaQualityPolicy.HIGH_DEFINITION:
                activity.highDefinition.setChecked(true);
                break;
            case MediaQualityPolicy.STANDARD:
                activity.standard.setChecked(true);
                break;
            case MediaQualityPolicy.LOW_DEFINITION:
                activity.lowQuality.setChecked(true);
                break;
        }

        activity.original.setOnCheckedChangeListener((cb, checked) -> setMediaQuality(checked, MediaQualityPolicy.ORIGINAL));
        activity.highDefinition.setOnCheckedChangeListener((cb, checked) -> setMediaQuality(checked, MediaQualityPolicy.HIGH_DEFINITION));
        activity.standard.setOnCheckedChangeListener((cb, checked) -> setMediaQuality(checked, MediaQualityPolicy.STANDARD));
        activity.lowQuality.setOnCheckedChangeListener((cb, checked) -> setMediaQuality(checked, MediaQualityPolicy.LOW_DEFINITION));
    }

    private void setMediaQuality(boolean checked, @MediaQualityPolicy int policy) {
        if (checked) mediaQualityService.setImageQualityPolicy(policy);
    }

    private void setupNotificationSettings(SettingsActivity activity) {
        switch (notificationService.getNotificationPolicy()) {
            case NotificationPolicy.LATE_IN_THE_DAY:
                activity.apodNotificationLate.setChecked(true);
                break;
            case NotificationPolicy.EARLY_IN_THE_DAY:
                activity.apodNotificationEarly.setChecked(true);
                break;
            case NotificationPolicy.NEVER:
                activity.apodNotificationNever.setChecked(true);
                break;
        }

        activity.apodNotificationLate.setOnCheckedChangeListener((cb, checked) -> setNotificaionPolicy(checked, NotificationPolicy.LATE_IN_THE_DAY));
        activity.apodNotificationEarly.setOnCheckedChangeListener((cb, checked) -> setNotificaionPolicy(checked, NotificationPolicy.EARLY_IN_THE_DAY));
        activity.apodNotificationNever.setOnCheckedChangeListener((cb, checked) -> setNotificaionPolicy(checked, NotificationPolicy.NEVER));
    }

    private void setNotificaionPolicy(boolean checked, @NotificationPolicy int policy) {
        if (checked) {
            notificationService.setNotificationPolicy(policy);
            notificationSchedulerService.schedule();
        }
    }
}
