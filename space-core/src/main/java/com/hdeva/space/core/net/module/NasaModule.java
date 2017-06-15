package com.hdeva.space.core.net.module;

import com.hdeva.space.core.net.api.NasaApi;
import com.hdeva.space.core.net.converter.ApodConverter;
import com.hdeva.space.core.net.interceptor.NasaApiKeyInterceptor;
import com.squareup.moshi.Moshi;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module(includes = {
        NetworkModule.class,
})
public class NasaModule {

    private static final String NASA_API = "https://api.nasa.gov";

    @Provides
    @Named(NASA_API)
    Moshi provideNasaMoshi(Moshi.Builder builder) {
        return builder
                .add(new ApodConverter())
                .build();
    }

    @Provides
    @Named(NASA_API)
    OkHttpClient provideNasaOkHttpClient(OkHttpClient.Builder builder, NasaApiKeyInterceptor nasaApiKeyInterceptor) {
        return builder
                .addInterceptor(nasaApiKeyInterceptor)
                .build();
    }

    @Provides
    @Named(NASA_API)
    Retrofit provideNasaRetrofit(@Named(NASA_API) OkHttpClient okHttpClient, @Named(NASA_API) Moshi moshi) {
        return new Retrofit.Builder()
                .baseUrl(NASA_API)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(okHttpClient)
                .build();
    }

    @Provides
    NasaApi provideNasaApi(@Named(NASA_API) Retrofit retrofit) {
        return retrofit.create(NasaApi.class);
    }
}
