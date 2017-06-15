package com.hdeva.space.ui.navigator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.ImageView;

import com.hdeva.space.R;
import com.hdeva.space.core.model.MediaDescriptor;
import com.hdeva.space.core.model.apod.Apod;
import com.hdeva.space.core.model.images.AssetCollection;
import com.hdeva.space.core.model.images.NasaItem;
import com.hdeva.space.ui.viewer.MediaViewerActivity;
import com.hdeva.space.ui.viewer.content.ContentHostActivity;

import org.parceler.Parcels;

import javax.inject.Inject;

public class MediaViewerNavigator extends BaseNavigator {

    private MediaDescriptorFactory mediaDescriptorFactory;

    @Inject
    public MediaViewerNavigator(MediaDescriptorFactory mediaDescriptorFactory) {
        this.mediaDescriptorFactory = mediaDescriptorFactory;
    }

    public void openNasaItem(Context context, NasaItem item, AssetCollection collection, @Nullable ImageView image) {
        Intent intent = new Intent(context, MediaViewerActivity.class);
        intent.putExtra(MediaViewerActivity.DESCRIPTOR, fromNasaItem(item, collection));

        if (context instanceof Activity && image != null) {
            Pair<View, String> pair = Pair.create(image, context.getString(R.string.image_viewer_photo_view_transition));
            context.startActivity(intent, createTransitionOptions((Activity) context, pair));
        } else {
            context.startActivity(intent);
        }
    }

    public void openApod(Context context, Apod apod, @Nullable ImageView image) {
        Intent intent = new Intent(context, MediaViewerActivity.class);
        intent.putExtra(MediaViewerActivity.DESCRIPTOR, fromApod(context, apod));

        if (context instanceof Activity && image != null) {
            Pair<View, String> pair = Pair.create(image, context.getString(R.string.image_viewer_photo_view_transition));
            context.startActivity(intent, createTransitionOptions((Activity) context, pair));
        } else {
            context.startActivity(intent);
        }
    }

    public void openMedia(Context context, MediaDescriptor descriptor) {
        Intent intent = new Intent(context, ContentHostActivity.class);
        intent.putExtra(ContentHostActivity.DESCRIPTOR, Parcels.wrap(descriptor));
        context.startActivity(intent);
    }

    private Parcelable fromNasaItem(NasaItem item, AssetCollection collection) {
        return Parcels.wrap(mediaDescriptorFactory.fromNasaItem(item, collection));
    }

    private Parcelable fromApod(Context context, Apod apod) {
        return Parcels.wrap(mediaDescriptorFactory.fromApod(context, apod));
    }
}
