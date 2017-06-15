package com.hdeva.space.core.service.extractor;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.hdeva.space.core.model.apod.Apod;
import com.hdeva.space.core.model.images.MediaType;
import com.hdeva.space.core.service.quality.MediaQualityPolicy;
import com.hdeva.space.core.service.quality.MediaQualityService;

import javax.inject.Inject;

class ApodLinkExtractor {

    private MediaQualityService mediaQualityService;
    private VideoLinkExtractor videoLinkExtractor;

    @Inject
    public ApodLinkExtractor(MediaQualityService mediaQualityService, VideoLinkExtractor videoLinkExtractor) {
        this.mediaQualityService = mediaQualityService;
        this.videoLinkExtractor = videoLinkExtractor;
    }

    @Nullable
    public String extractMedia(Apod apod) {
        return getPreferredLink(apod);
    }

    @Nullable
    public String extractImage(Apod apod) {
        String url;
        if (MediaType.IMAGE.equalsIgnoreCase(apod.media_type)) {
            url = getPreferredLink(apod);
        } else if (MediaType.VIDEO.equalsIgnoreCase(apod.media_type)) {
            url = videoLinkExtractor.transformUrlToImage(getPreferredLink(apod));
        } else {
            url = null;
        }
        return url;
    }

    @Nullable
    private String getPreferredLink(Apod apod) {
        String url;
        if (mediaQualityService.getImageQualityPolicy() >= MediaQualityPolicy.HIGH_DEFINITION) {
            url = TextUtils.isEmpty(apod.hdurl) ? apod.url : apod.hdurl;
        } else {
            url = TextUtils.isEmpty(apod.url) ? apod.hdurl : apod.url;
        }
        return url;
    }

}
