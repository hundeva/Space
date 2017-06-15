package com.hdeva.space.core.net.module;

import com.hdeva.space.core.net.api.NasaImagesApi;
import com.hdeva.space.core.net.converter.AssetCollectionConverter;
import com.hdeva.space.core.net.converter.ImageSearchResponseConverter;
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
public class NasaImagesModule {

    private static final String NASA_IMAGES_API = "https://images-api.nasa.gov";

    @Provides
    @Named(NASA_IMAGES_API)
    Moshi provideNasaImagesMoshi(Moshi.Builder builder) {
        return builder
                .add(new ImageSearchResponseConverter())
                .add(new AssetCollectionConverter())
                .build();
    }

    @Provides
    @Named(NASA_IMAGES_API)
    OkHttpClient provideNasaImagesOkHttpClient(OkHttpClient.Builder builder) {
        return builder
                .build();
    }

    @Provides
    @Named(NASA_IMAGES_API)
    Retrofit provideNasaImagesRetrofit(@Named(NASA_IMAGES_API) OkHttpClient okHttpClient, @Named(NASA_IMAGES_API) Moshi moshi) {
        return new Retrofit.Builder()
                .baseUrl(NASA_IMAGES_API)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(okHttpClient)
                .build();
    }

    @Provides
    NasaImagesApi provideNasaImageApi(@Named(NASA_IMAGES_API) Retrofit retrofit) {
        return retrofit.create(NasaImagesApi.class);
    }
}
