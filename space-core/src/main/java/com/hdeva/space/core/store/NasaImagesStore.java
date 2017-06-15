package com.hdeva.space.core.store;

import android.support.annotation.Nullable;

import com.hdeva.space.core.model.images.AssetCollection;
import com.hdeva.space.core.model.images.ImageSearchResponse;
import com.hdeva.space.core.model.images.NasaItem;

import javax.inject.Inject;
import javax.inject.Named;

import io.paperdb.Book;

public class NasaImagesStore {

    private static final String MOST_POPULAR_ITEMS = "mostPopularItems";
    private static final String RECENT_ITEMS = "recentItems";

    private Book book;

    @Inject
    public NasaImagesStore(@Named(StoreModule.POPULAR) Book book) {
        this.book = book;
    }

    @Nullable
    public ImageSearchResponse getMostPopularItems() {
        return book.read(MOST_POPULAR_ITEMS);
    }

    public void insertMostPopularItems(ImageSearchResponse response) {
        book.write(MOST_POPULAR_ITEMS, response);
    }

    public boolean hasPopularItemsStored() {
        return book.exist(MOST_POPULAR_ITEMS);
    }

    public ImageSearchResponse getRecentItems() {
        return book.read(RECENT_ITEMS);
    }

    public void insertRecentItems(ImageSearchResponse response) {
        book.write(RECENT_ITEMS, response);
    }

    public boolean hasRecentItemsStored() {
        return book.exist(RECENT_ITEMS);
    }

    @Nullable
    public AssetCollection getAssetCollection(NasaItem item) {
        return book.read(assetCollectionKey(item));
    }

    public void insertCollection(NasaItem item, AssetCollection collection) {
        book.write(assetCollectionKey(item), collection);
    }

    private String assetCollectionKey(NasaItem item) {
        return item.data.get(0).nasaId;
    }

}
