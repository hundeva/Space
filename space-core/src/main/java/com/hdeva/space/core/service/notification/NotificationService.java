package com.hdeva.space.core.service.notification;

import javax.inject.Inject;

import io.paperdb.Book;

public class NotificationService {

    private static final String NOTIFICATION_POLICY = "notificationPolicy";

    private Book book;

    @Inject
    public NotificationService(Book book) {
        this.book = book;
    }

    public void setNotificationPolicy(@NotificationPolicy int notificationPolicy) {
        book.write(NOTIFICATION_POLICY, notificationPolicy);
    }

    @NotificationPolicy
    public int getNotificationPolicy() {
        return book.read(NOTIFICATION_POLICY, NotificationPolicy.LATE_IN_THE_DAY);
    }
}
