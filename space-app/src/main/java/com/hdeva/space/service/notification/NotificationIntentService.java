package com.hdeva.space.service.notification;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.hdeva.space.R;
import com.hdeva.space.core.helper.GlideHelper;
import com.hdeva.space.core.model.MediaDescriptor;
import com.hdeva.space.core.model.apod.Apod;
import com.hdeva.space.core.net.error.EndpointException;
import com.hdeva.space.core.repository.ApodRepository;
import com.hdeva.space.core.service.extractor.MediaLinkExtractor;
import com.hdeva.space.ui.navigator.MediaDescriptorFactory;
import com.hdeva.space.ui.viewer.MediaViewerActivity;

import org.parceler.Parcels;

import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import dagger.android.DaggerIntentService;

public class NotificationIntentService extends DaggerIntentService {

    private static final int TRY_COUNT_THRESHOLD = 3;
    private static final int NOTIFICATION_ID = 7331;

    @Inject
    ApodRepository apodRepository;
    @Inject
    GlideHelper glideHelper;
    @Inject
    MediaDescriptorFactory mediaDescriptorFactory;
    @Inject
    NotificationManagerCompat notificationManagerCompat;
    @Inject
    MediaLinkExtractor mediaLinkExtractor;

    public NotificationIntentService() {
        super(NotificationIntentService.class.getName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        getApod(0);
    }

    private void getApod(int tryCount) {
        if (tryCount >= TRY_COUNT_THRESHOLD) return;
        try {
            Apod apod = apodRepository.getApodForTodaySync();
            String thumbnail = mediaLinkExtractor.extractThumbnailUrl(apod);
            Bitmap bitmap = glideHelper.getRawBitmap(this, thumbnail);
            notify(this, bitmap, mediaDescriptorFactory.fromApod(this, apod));
        } catch (EndpointException e) {
            getApod(++tryCount);
        } catch (InterruptedException e) {
            getApod(++tryCount);
        } catch (ExecutionException e) {
            getApod(++tryCount);
        }
    }

    private void notify(Context context, Bitmap bitmap, MediaDescriptor descriptor) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(descriptor.title)
                .setContentText(descriptor.subTitle)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(descriptor.description))
                .setSmallIcon(R.drawable.ic_notification_icon)
                .setLargeIcon(bitmap)
                .setContentIntent(createPendingIntent(context, descriptor))
                .setAutoCancel(true);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }

    private PendingIntent createPendingIntent(Context context, MediaDescriptor descriptor) {
        Intent intent = new Intent(context, MediaViewerActivity.class);
        intent.putExtra(MediaViewerActivity.DESCRIPTOR, Parcels.wrap(descriptor));
        intent.putExtra(MediaViewerActivity.FROM_NOTIFICATION, true);
        return PendingIntent.getActivity(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }
}
