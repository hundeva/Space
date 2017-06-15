package com.hdeva.space.core.store;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.hdeva.space.core.model.apod.Apod;
import com.hdeva.space.core.service.DateService;

import javax.inject.Inject;
import javax.inject.Named;

import io.paperdb.Book;

public class ApodStore {

    private static final String LAST_APOD_DATE = "lastApodDAte";

    private Book book;
    private DateService dateService;

    @Inject
    public ApodStore(@Named(StoreModule.APOD) Book book, DateService dateService) {
        this.book = book;
        this.dateService = dateService;
    }

    @Nullable
    public Apod getApod(String date) {
        return book.read(date);
    }

    @Nullable
    public Apod getLastKnownApod() {
        String key = book.read(LAST_APOD_DATE);
        return key == null ? null : getApod(key);
    }

    public void insert(Apod apod) {
        book.write(apod.date, apod);
        if (TextUtils.equals(apod.date, dateService.getApodDatePattern(dateService.today()))) {
            book.write(LAST_APOD_DATE, apod.date);
        }
    }

    public boolean hasApodForToday() {
        Apod lastApod = getLastKnownApod();
        return lastApod != null && TextUtils.equals(lastApod.date, dateService.getApodDatePattern(dateService.today()));
    }
}
