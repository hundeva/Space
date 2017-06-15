package com.hdeva.space.core.service.extractor;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.hdeva.space.core.model.images.AssetCollection;

import java.util.List;

import javax.inject.Inject;

class ImageLinkExtractor {

    private PreferredLinkExtractor preferredLinkExtractor;

    @Inject
    public ImageLinkExtractor(PreferredLinkExtractor preferredLinkExtractor) {
        this.preferredLinkExtractor = preferredLinkExtractor;
    }

    public boolean isImage(String url) {
        return url.contains(".jpg")
                || url.contains(".jpeg")
                || url.contains(".png")
                || url.contains(".gif");
    }

    public List<String> extractLinks(AssetCollection collection) {
        return Stream.of(collection.items)
                .filter(this::isImage)
                .collect(Collectors.toList());
    }

    public String getPreferredLink(List<String> links) {
        return preferredLinkExtractor.getPreferredLink(links);
    }
}
