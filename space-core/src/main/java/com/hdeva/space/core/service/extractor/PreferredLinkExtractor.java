package com.hdeva.space.core.service.extractor;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.hdeva.space.core.service.quality.MediaQualityPolicy;
import com.hdeva.space.core.service.quality.MediaQualityService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

class PreferredLinkExtractor {

    private static final String ORIGINAL = "~orig";
    private static final String PREVIEW = "~preview";
    private static final String LARGE = "~large";
    private static final String MEDIUM = "~medium";
    private static final String SMALL = "~small";
    private static final String MOBILE = "~mobile";
    private static final String THUMB = "~thumb";

    private MediaQualityService mediaQualityService;

    @Inject
    public PreferredLinkExtractor(MediaQualityService mediaQualityService) {
        this.mediaQualityService = mediaQualityService;
    }

    @Nullable
    public String getPreferredLink(List<String> links) {
        List<String> preferredLinks = getPreferredLinks(links);
        return preferredLinks == null ? null : preferredLinks.get(preferredLinks.size() - 1);
    }

    private List<String> getPreferredLinks(List<String> links) {
        @MediaQualityPolicy
        int policy = mediaQualityService.getImageQualityPolicy();

        List<String> preferredLinks;
        switch (policy) {
            case MediaQualityPolicy.ORIGINAL:
                preferredLinks = filterLinksForPreferred(links, ORIGINAL, PREVIEW, LARGE, MEDIUM, SMALL, MOBILE, THUMB);
                break;
            case MediaQualityPolicy.HIGH_DEFINITION:
                preferredLinks = filterLinksForPreferred(links, LARGE, PREVIEW, ORIGINAL, MEDIUM, SMALL, MOBILE, THUMB);
                break;
            case MediaQualityPolicy.STANDARD:
                preferredLinks = filterLinksForPreferred(links, MEDIUM, LARGE, PREVIEW, ORIGINAL, SMALL, MOBILE, THUMB);
                break;
            case MediaQualityPolicy.LOW_DEFINITION:
                preferredLinks = filterLinksForPreferred(links, SMALL, MOBILE, THUMB, MEDIUM, LARGE, PREVIEW, ORIGINAL);
                break;
            default:
                throw new IllegalStateException("Unrecognized image quality policy : " + policy);
        }

        if ((preferredLinks == null || preferredLinks.size() == 0) && links.size() > 0) {
            preferredLinks = new ArrayList<>();
            preferredLinks.add(links.get(0));
        }

        return preferredLinks;
    }

    private List<String> filterLinksForPreferred(List<String> links, String... policies) {
        for (String policy : policies) {
            List<String> preferredLinks = Stream.of(links)
                    .filter(link -> !TextUtils.isEmpty(link))
                    .filter(link -> link.toLowerCase().contains(policy))
                    .collect(Collectors.toList());
            if (preferredLinks != null && preferredLinks.size() > 0) return links;
        }
        return null;
    }
}
