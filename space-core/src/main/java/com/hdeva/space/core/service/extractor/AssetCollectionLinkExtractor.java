package com.hdeva.space.core.service.extractor;

import com.hdeva.space.core.helper.NasaItemHelper;
import com.hdeva.space.core.model.images.AssetCollection;
import com.hdeva.space.core.model.images.MediaType;
import com.hdeva.space.core.model.images.NasaItem;

import java.util.List;

import javax.inject.Inject;

class AssetCollectionLinkExtractor {

    private NasaItemHelper nasaItemHelper;
    private ImageLinkExtractor imageLinkExtractor;
    private VideoLinkExtractor videoLinkExtractor;
    private AudioLinkExtractor audioLinkExtractor;

    @Inject
    public AssetCollectionLinkExtractor(NasaItemHelper nasaItemHelper, ImageLinkExtractor imageLinkExtractor, VideoLinkExtractor videoLinkExtractor, AudioLinkExtractor audioLinkExtractor) {
        this.nasaItemHelper = nasaItemHelper;
        this.imageLinkExtractor = imageLinkExtractor;
        this.videoLinkExtractor = videoLinkExtractor;
        this.audioLinkExtractor = audioLinkExtractor;
    }

    public String extractMedia(NasaItem item, AssetCollection collection) {
        return getLinkForType(collection, nasaItemHelper.extractMediaType(item));
    }

    public String extractImage(AssetCollection collection) {
        return getLinkForType(collection, MediaType.IMAGE);
    }

    private String getLinkForType(AssetCollection collection, @MediaType String mediaType) {
        List<String> links;
        String link;
        switch (mediaType) {
            case MediaType.IMAGE:
                links = imageLinkExtractor.extractLinks(collection);
                link = imageLinkExtractor.getPreferredLink(links);
                break;
            case MediaType.VIDEO:
                links = videoLinkExtractor.extractLinks(collection);
                link = videoLinkExtractor.getPreferredLink(links);
                break;
            case MediaType.AUDIO:
                links = audioLinkExtractor.extractLinks(collection);
                link = audioLinkExtractor.getPreferredLink(links);
                break;
            default:
                throw new IllegalStateException("Invalid media type requested: " + mediaType);
        }

        return link;
    }
}
