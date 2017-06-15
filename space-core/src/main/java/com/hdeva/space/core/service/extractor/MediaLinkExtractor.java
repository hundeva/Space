package com.hdeva.space.core.service.extractor;

import com.annimon.stream.Stream;
import com.hdeva.space.core.model.apod.Apod;
import com.hdeva.space.core.model.images.AssetCollection;
import com.hdeva.space.core.model.images.NasaItem;

import javax.inject.Inject;

public class MediaLinkExtractor {

    private ApodLinkExtractor apodLinkExtractor;
    private AssetCollectionLinkExtractor assetCollectionLinkExtractor;
    private ImageLinkExtractor imageLinkExtractor;

    @Inject
    public MediaLinkExtractor(ApodLinkExtractor apodLinkExtractor, AssetCollectionLinkExtractor assetCollectionLinkExtractor, ImageLinkExtractor imageLinkExtractor) {
        this.apodLinkExtractor = apodLinkExtractor;
        this.assetCollectionLinkExtractor = assetCollectionLinkExtractor;
        this.imageLinkExtractor = imageLinkExtractor;
    }

    public String extractMediaUrl(Apod apod) {
        return apodLinkExtractor.extractMedia(apod);
    }

    public String extractThumbnailUrl(Apod apod) {
        return apodLinkExtractor.extractImage(apod);
    }

    public String extractMediaUrl(NasaItem item, AssetCollection collection) {
        return assetCollectionLinkExtractor.extractMedia(item, collection);
    }

    public String extractThumbnailUrl(AssetCollection collection) {
        return assetCollectionLinkExtractor.extractImage(collection);
    }

    public boolean hasValidImageUrl(AssetCollection collection) {
        return Stream.of(collection.items)
                .map(String::toLowerCase)
                .anyMatch(imageLinkExtractor::isImage);
    }

}
