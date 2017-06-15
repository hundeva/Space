package com.hdeva.space.service;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.hdeva.space.R;
import com.hdeva.space.core.helper.NasaItemHelper;
import com.hdeva.space.core.model.MediaDescriptor;
import com.hdeva.space.core.model.apod.Apod;
import com.hdeva.space.core.model.images.AssetCollection;
import com.hdeva.space.core.model.images.NasaItem;
import com.hdeva.space.core.service.extractor.MediaLinkExtractor;

import javax.inject.Inject;

public class ContentShareHelper {

    private static final String MIME_TYPE = "text/plain";

    private Context context;
    private MediaLinkExtractor mediaLinkExtractor;
    private NasaItemHelper nasaItemHelper;

    @Inject
    public ContentShareHelper(Context context, MediaLinkExtractor mediaLinkExtractor, NasaItemHelper nasaItemHelper) {
        this.context = context;
        this.mediaLinkExtractor = mediaLinkExtractor;
        this.nasaItemHelper = nasaItemHelper;
    }

    public void shareNasaItem(NasaItem item, AssetCollection collection) {
        share(nasaItemHelper.extractTitle(item), mediaLinkExtractor.extractMediaUrl(item, collection));
    }

    public void shareApod(Apod apod) {
        share(apod.title, mediaLinkExtractor.extractMediaUrl(apod));
    }

    public void shareContent(MediaDescriptor descriptor) {
        share(descriptor.title, TextUtils.isEmpty(descriptor.media) ? descriptor.thumbnail : descriptor.media);
    }

    public void shareApp() {
        share(context.getString(R.string.app_name), "https://play.google.com/store/apps/details?id=com.hdeva.space");
    }

    private void share(String title, String link) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType(MIME_TYPE);
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, String.format("%1$s\n\n%2$s", title, fixWhitespaces(link)));
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.share_x, title)));
    }

    private String fixWhitespaces(String url) {
        return url.trim().replace(" ", "%20");
    }
}
