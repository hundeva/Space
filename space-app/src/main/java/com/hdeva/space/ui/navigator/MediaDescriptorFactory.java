package com.hdeva.space.ui.navigator;

import android.content.Context;

import com.hdeva.space.R;
import com.hdeva.space.core.helper.NasaItemHelper;
import com.hdeva.space.core.model.MediaDescriptor;
import com.hdeva.space.core.model.apod.Apod;
import com.hdeva.space.core.model.images.AssetCollection;
import com.hdeva.space.core.model.images.NasaItem;
import com.hdeva.space.core.service.DateService;
import com.hdeva.space.core.service.extractor.MediaLinkExtractor;

import javax.inject.Inject;

public class MediaDescriptorFactory {

    private NasaItemHelper nasaItemHelper;
    private MediaLinkExtractor mediaLinkExtractor;
    private DateService dateService;

    @Inject
    public MediaDescriptorFactory(NasaItemHelper nasaItemHelper, MediaLinkExtractor mediaLinkExtractor, DateService dateService) {
        this.nasaItemHelper = nasaItemHelper;
        this.mediaLinkExtractor = mediaLinkExtractor;
        this.dateService = dateService;
    }

    public MediaDescriptor fromNasaItem(NasaItem item, AssetCollection collection) {
        MediaDescriptor descriptor = new MediaDescriptor();
        descriptor.mediaType = nasaItemHelper.extractMediaType(item);
        descriptor.title = nasaItemHelper.extractTitle(item);
        descriptor.subTitle = nasaItemHelper.extractSubTitle(item);
        descriptor.description = nasaItemHelper.extractDescription(item);
        descriptor.thumbnail = mediaLinkExtractor.extractThumbnailUrl(collection);
        descriptor.media = mediaLinkExtractor.extractMediaUrl(item, collection);
        return descriptor;
    }

    public MediaDescriptor fromApod(Context context, Apod apod) {
        MediaDescriptor descriptor = new MediaDescriptor();
        descriptor.mediaType = apod.media_type;
        descriptor.title = apod.title;
        descriptor.subTitle = context.getString(R.string.apod_date, dateService.parseApodDateFormat(apod.date));
        descriptor.description = apod.explanation;
        descriptor.thumbnail = mediaLinkExtractor.extractThumbnailUrl(apod);
        descriptor.media = mediaLinkExtractor.extractMediaUrl(apod);
        return descriptor;
    }
}
