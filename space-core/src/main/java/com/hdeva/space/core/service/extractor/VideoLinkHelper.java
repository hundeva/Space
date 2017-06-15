package com.hdeva.space.core.service.extractor;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import javax.inject.Inject;

public class VideoLinkHelper {

    private VideoLinkExtractor videoLinkExtractor;

    @Inject
    public VideoLinkHelper(VideoLinkExtractor videoLinkExtractor) {
        this.videoLinkExtractor = videoLinkExtractor;
    }

    public void openVideoLink(Context context, String videoUrl) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(videoUrl));
        context.startActivity(intent);
    }

    public boolean isVideo(String url) {
        return videoLinkExtractor.isVideo(url);
    }

    public boolean isYouTubeVideo(String url) {
        return videoLinkExtractor.isYouTubeUrl(url);
    }

    public String extractYouTubeId(String url) {
        return videoLinkExtractor.extractYouTubeId(url);
    }
}
