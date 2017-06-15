package com.hdeva.space.core.service.extractor;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.hdeva.space.core.model.images.AssetCollection;

import java.util.List;

import javax.inject.Inject;

class AudioLinkExtractor {

    @Inject
    public AudioLinkExtractor() {
    }

    private boolean isAudio(String url) {
        return url.contains(".wav")
                || url.contains(".m4a")
                || url.contains(".mp3");
    }

    public List<String> extractLinks(AssetCollection collection) {
        return Stream.of(collection.items)
                .filter(this::isAudio)
                .collect(Collectors.toList());
    }

    public String getPreferredLink(List<String> links) {
        return links != null && links.size() > 0 ? links.get(0) : null;
    }
}
