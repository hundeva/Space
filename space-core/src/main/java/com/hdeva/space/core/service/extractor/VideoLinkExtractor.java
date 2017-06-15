package com.hdeva.space.core.service.extractor;

import android.net.Uri;
import android.text.TextUtils;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.hdeva.space.core.model.images.AssetCollection;
import com.hdeva.space.core.service.quality.MediaQualityPolicy;
import com.hdeva.space.core.service.quality.MediaQualityService;

import java.util.List;

import javax.inject.Inject;

class VideoLinkExtractor {

    private static final String YOUTUBE_WATCH_PATTERN = "youtube.com/watch"; // https://www.youtube.com/watch?v=<videoId>
    private static final String YOUTUBE_EMBED_PATTERN = "youtube.com/embed"; // https://www.youtube.com/embed/<videoId>
    private static final String YOUTUBE_SHORT_PATTERN = "youtu.be"; // https://youtu.be/<videoId>

    private static final String YOUTUBE_THUMBNAIL_ORIGINAL_QUALITY = "https://img.youtube.com/vi/%s/maxresdefault.jpg";
    private static final String YOUTUBE_THUMBNAIL_HIGH_QUALITY = "https://img.youtube.com/vi/%s/sddefault.jpg";
    private static final String YOUTUBE_THUMBNAIL_MEDIUM_QUALITY = "https://img.youtube.com/vi/%s/hqdefault.jpg";
    private static final String YOUTUBE_THUMBNAIL_STANDARD_QUALITY = "https://img.youtube.com/vi/%s/mqdefault.jpg";

    private MediaQualityService mediaQualityService;
    private PreferredLinkExtractor preferredLinkExtractor;

    @Inject
    public VideoLinkExtractor(MediaQualityService mediaQualityService, PreferredLinkExtractor preferredLinkExtractor) {
        this.mediaQualityService = mediaQualityService;
        this.preferredLinkExtractor = preferredLinkExtractor;
    }

    public List<String> extractLinks(AssetCollection collection) {
        return Stream.of(collection.items)
                .filter(this::isVideo)
                .collect(Collectors.toList());
    }

    public String getPreferredLink(List<String> links) {
        return preferredLinkExtractor.getPreferredLink(links);
    }

    public String transformUrlToImage(String url) {
        if (isYouTubeUrl(url)) {
            String youTubeId = extractYouTubeId(url);
            url = getYouTubeThumbnail(youTubeId);
        }
        return url;
    }

    boolean isVideo(String url) {
        return url.contains(".mp4")
                || url.contains(".mov");
    }

    boolean isYouTubeUrl(String url) {
        return url.contains(YOUTUBE_WATCH_PATTERN)
                || url.contains(YOUTUBE_EMBED_PATTERN)
                || url.contains(YOUTUBE_SHORT_PATTERN);
    }

    String extractYouTubeId(String url) {
        String youtubeId;
        if (TextUtils.isEmpty(url)) {
            youtubeId = null;
        } else {
            String strippedUrl = url.substring(0, url.lastIndexOf("?"));
            if (url.toLowerCase().contains(YOUTUBE_WATCH_PATTERN)) {
                Uri uri = Uri.parse(strippedUrl);
                youtubeId = uri.getQueryParameter("v");
            } else if (url.toLowerCase().contains(YOUTUBE_EMBED_PATTERN)) {
                String[] splitted = strippedUrl.split("/");
                youtubeId = splitted[splitted.length - 1];
            } else if (url.toLowerCase().contains(YOUTUBE_SHORT_PATTERN)) {
                String[] splitted = strippedUrl.split("/");
                youtubeId = splitted[splitted.length - 1];
            } else {
                youtubeId = null;
            }
        }
        return youtubeId;
    }

    private String getYouTubeThumbnail(String youtubeId) {
        String youtubeUrl;
        if (TextUtils.isEmpty(youtubeId)) {
            youtubeUrl = null;
        } else {
            @MediaQualityPolicy
            int policy = mediaQualityService.getImageQualityPolicy();

            String youtubePattern;
            switch (policy) {
                case MediaQualityPolicy.ORIGINAL:
                    youtubePattern = YOUTUBE_THUMBNAIL_ORIGINAL_QUALITY;
                    break;
                case MediaQualityPolicy.HIGH_DEFINITION:
                    youtubePattern = YOUTUBE_THUMBNAIL_HIGH_QUALITY;
                    break;
                case MediaQualityPolicy.STANDARD:
                    youtubePattern = YOUTUBE_THUMBNAIL_MEDIUM_QUALITY;
                    break;
                case MediaQualityPolicy.LOW_DEFINITION:
                    youtubePattern = YOUTUBE_THUMBNAIL_STANDARD_QUALITY;
                    break;
                default:
                    throw new IllegalStateException("Unrecognized image quality policy : " + policy);
            }
            youtubeUrl = String.format(youtubePattern, youtubeId);
        }
        return youtubeUrl;
    }

}
