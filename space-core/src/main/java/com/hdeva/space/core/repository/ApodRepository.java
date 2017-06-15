package com.hdeva.space.core.repository;

import android.util.Log;

import com.hdeva.space.core.model.apod.Apod;
import com.hdeva.space.core.net.api.NasaApi;
import com.hdeva.space.core.net.error.EndpointException;
import com.hdeva.space.core.service.DateService;
import com.hdeva.space.core.store.ApodStore;

import org.threeten.bp.LocalDate;

import javax.inject.Inject;

import io.reactivex.Observable;

public class ApodRepository extends BaseRepository {

    private static final String TAG = "ApodRepository";

    private NasaApi api;
    private ApodStore store;
    private DateService dateService;

    @Inject
    public ApodRepository(NasaApi api, ApodStore store, DateService dateService) {
        this.api = api;
        this.store = store;
        this.dateService = dateService;
    }

    public Observable<Apod> getApodForToday() {
        return getApodForDate(dateService.today());
    }

    public Observable<Apod> getApodForDate(LocalDate localDate) {
        Apod storedApod = store.getApod(dateService.getApodDatePattern(localDate));

        Observable<Apod> observable;
        if (storedApod == null) {
            observable = queue(api.getAstronomerPictureOfTheDay(dateService.getApodDatePattern(localDate), true))
                    .publish()
                    .autoConnect(2);
            observable.subscribe(apod -> store.insert(apod), throwable -> Log.e(TAG, "Error getting apod", throwable));
        } else {
            observable = Observable.just(storedApod);
        }
        return observable;
    }

    public Apod getApodForTodaySync() throws EndpointException {
        Apod apod;
        if (store.hasApodForToday()) {
            apod = store.getApod(dateService.getApodDatePattern(dateService.today()));
        } else {
            apod = execute(api.getAstronomerPictureOfTheDay(dateService.getApodDatePattern(dateService.today()), true));
        }
        return apod;
    }
}
