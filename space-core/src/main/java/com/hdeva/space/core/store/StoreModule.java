package com.hdeva.space.core.store;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.paperdb.Book;
import io.paperdb.Paper;

@Module
public class StoreModule {

    static final String PREFERENCES = "preferences.db";
    static final String APOD = "apod.db";
    static final String POPULAR = "popular.db";

    @Provides
    Book provideBook() {
        return Paper.book(PREFERENCES);
    }

    @Provides
    @Named(APOD)
    Book provideApodBook() {
        return Paper.book(APOD);
    }

    @Provides
    @Named(POPULAR)
    Book providePopularBook() {
        return Paper.book(POPULAR);
    }
}
