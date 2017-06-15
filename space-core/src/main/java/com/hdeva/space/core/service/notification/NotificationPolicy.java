package com.hdeva.space.core.service.notification;

import android.support.annotation.IntDef;

import static com.hdeva.space.core.service.notification.NotificationPolicy.EARLY_IN_THE_DAY;
import static com.hdeva.space.core.service.notification.NotificationPolicy.LATE_IN_THE_DAY;
import static com.hdeva.space.core.service.notification.NotificationPolicy.NEVER;

@IntDef({NEVER, EARLY_IN_THE_DAY, LATE_IN_THE_DAY})
public @interface NotificationPolicy {
    int NEVER = 0;
    int EARLY_IN_THE_DAY = 1;
    int LATE_IN_THE_DAY = 2;
}
