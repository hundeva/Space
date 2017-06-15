package com.hdeva.space.core.repository;

import android.util.Log;

import com.hdeva.space.core.model.images.AssetCollection;
import com.hdeva.space.core.model.images.ImageSearchResponse;
import com.hdeva.space.core.model.images.NasaItem;
import com.hdeva.space.core.model.images.SearchRequest;
import com.hdeva.space.core.net.api.NasaImagesApi;
import com.hdeva.space.core.store.NasaImagesStore;

import javax.inject.Inject;

import io.reactivex.Observable;

public class NasaImagesRepository extends BaseRepository {

    private static final String TAG = "NasaImagesRepository";

    private NasaImagesApi api;
    private NasaImagesStore nasaImagesStore;

    @Inject
    public NasaImagesRepository(NasaImagesApi api, NasaImagesStore nasaImagesStore) {
        this.api = api;
        this.nasaImagesStore = nasaImagesStore;
    }

    public Observable<ImageSearchResponse> getMostPopularImages() {
        Observable<ImageSearchResponse> observable = queue(api.getMostPopularMedia())
                .publish()
                .autoConnect(2);
        observable.subscribe(response -> nasaImagesStore.insertMostPopularItems(response), throwable -> Log.e(TAG, "Error getting popular response", throwable));
        return observable;
    }

    public Observable<ImageSearchResponse> getRecentItems() {
        Observable<ImageSearchResponse> observable = queue(api.getRecentMedia())
                .publish()
                .autoConnect(2);
        observable.subscribe(response -> nasaImagesStore.insertRecentItems(response), throwable -> Log.e(TAG, "Error getting recent response", throwable));
        return observable;
    }

    public Observable<AssetCollection> getAssetForItem(NasaItem item) {
        AssetCollection storedCollection = nasaImagesStore.getAssetCollection(item);
        Observable<AssetCollection> observable;
        if (storedCollection == null) {
            observable = queue(api.getNasaItemAssets(nasaId(item)))
                    .publish()
                    .autoConnect(2);
            observable.subscribe(collection -> nasaImagesStore.insertCollection(item, collection), throwable -> Log.e(TAG, "Error getting asset collection", throwable));
        } else {
            observable = Observable.just(storedCollection);
        }
        return observable;
    }

    public Observable<ImageSearchResponse> searchMedia(SearchRequest request) {
        return queue(api.searchMedia(request.toRequestParameters()));
    }

    private String nasaId(NasaItem item) {
        return item.data.get(0).nasaId;
    }

}
