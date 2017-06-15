package com.hdeva.space.core.net.module;

import android.content.Context;

import com.squareup.moshi.Moshi;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

@Module
public class NetworkModule {

    private static final long CACHE_SIZE_LIMIT = 10 * 1024 * 1024;

    private static final long CONNECTION_TIMEOUT = 25;
    private static final long READ_TIMEOUT = 90;
    private static final long WRITE_TIMEOUT = 90;

    @Provides
    Moshi.Builder provideMoshiBuilder() {
        return new Moshi.Builder();
    }

    @Provides
    Cache provideCache(Context context) {
        return new Cache(context.getCacheDir(), CACHE_SIZE_LIMIT);
    }

    @Provides
    OkHttpClient.Builder provideOkHttpClientBuilder(Cache cache) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
    }
}
