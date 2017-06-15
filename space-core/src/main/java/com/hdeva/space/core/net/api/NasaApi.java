package com.hdeva.space.core.net.api;

import com.hdeva.space.core.model.apod.Apod;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NasaApi {

    @GET("planetary/apod")
    Call<Apod> getAstronomerPictureOfTheDay(@Query("date") String date, @Query("hd") boolean hd);
}
