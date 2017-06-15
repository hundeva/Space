package com.hdeva.space.core.net.api;

import com.hdeva.space.core.model.images.AssetCollection;
import com.hdeva.space.core.model.images.ImageSearchResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface NasaImagesApi {

    @GET("asset/?orderby=popular")
    Call<ImageSearchResponse> getMostPopularMedia();

    @GET("asset/?orderby=recent")
    Call<ImageSearchResponse> getRecentMedia();

    @GET("asset/{nasaId}")
    Call<AssetCollection> getNasaItemAssets(@Path("nasaId") String nasaId);

    @GET("search")
    Call<ImageSearchResponse> searchMedia(@QueryMap Map<String, String> parameters);
}
