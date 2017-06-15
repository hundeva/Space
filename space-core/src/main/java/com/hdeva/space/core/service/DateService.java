package com.hdeva.space.core.service;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.format.DateTimeFormatter;

import javax.inject.Inject;

public class DateService {

    private static final String APOD_FORMAT = "YYYY-MM-dd";
    private static final String APOD_PARSE_FORMAT = "yyyy-MM-dd";
    private static final String READABLE_YYYY_MMM_DD = "YYYY MMM d";

    @Inject
    public DateService() {
    }

    public LocalDate today() {
        return LocalDate.now();
    }

    public String getApodDatePattern(LocalDate localDate) {
        return format(localDate, APOD_FORMAT);
    }

    public String parseIsoDateTimeFormat(String date) {
        return LocalDateTime
                .parse(date, DateTimeFormatter.ISO_DATE_TIME)
                .format(DateTimeFormatter.ofPattern(READABLE_YYYY_MMM_DD));
    }

    public String parseApodDateFormat(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern(READABLE_YYYY_MMM_DD));
    }

    public String parseApodDateFormat(String date) {
        return LocalDate
                .parse(date, DateTimeFormatter.ofPattern(APOD_PARSE_FORMAT))
                .format(DateTimeFormatter.ofPattern(READABLE_YYYY_MMM_DD));
    }

    public long getNotificationSchedulerDateInMillis(int hour, int minute) {
        LocalDateTime localDateTime = LocalDateTime
                .now()
                .withHour(hour)
                .withMinute(minute);

        if (localDateTime.isBefore(LocalDateTime.now()))
            localDateTime = localDateTime.plusDays(1);

        return localDateTime
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
    }

    private String format(LocalDate localDate, String pattern) {
        return localDate.format(DateTimeFormatter.ofPattern(pattern));
    }

    private String format(LocalDateTime localDateTime, String pattern) {
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

}
