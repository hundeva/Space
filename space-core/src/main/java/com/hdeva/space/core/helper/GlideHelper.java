package com.hdeva.space.core.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.hdeva.space.core.model.apod.Apod;
import com.hdeva.space.core.model.images.AssetCollection;
import com.hdeva.space.core.service.extractor.MediaLinkExtractor;

import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

public class GlideHelper {

    private MediaLinkExtractor extractor;

    @Inject
    public GlideHelper(MediaLinkExtractor extractor) {
        this.extractor = extractor;
    }

    public void setApodImage(ImageView imageView, Apod apod) {
        setImage(imageView, extractor.extractThumbnailUrl(apod));
    }

    public void setApodImageAsync(ImageView imageView, Apod apod, GlideCallback callback) {
        setImageAsync(imageView, extractor.extractThumbnailUrl(apod), callback);
    }

    public void setNasaMediaImage(ImageView imageView, AssetCollection collection) {
        setImage(imageView, extractor.extractThumbnailUrl(collection));
    }

    public void setNasaMediaImageAsync(ImageView imageView, AssetCollection collection, GlideCallback callback) {
        setImageAsync(imageView, extractor.extractThumbnailUrl(collection), callback);
    }

    public void setImage(ImageView into, String url) {
        withGlide(into, url)
                .into(into);
    }

    public Bitmap getRawBitmap(Context context, String url) throws ExecutionException, InterruptedException {
        return Glide.with(context).load(url).asBitmap().into(100, 100).get();
    }

    private void setImageAsync(ImageView into, String url, GlideCallback callback) {
        withGlide(into, url)
                .placeholder(into.getDrawable())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        callback.onGlideReady();
                        return false;
                    }
                })
                .into(into);
    }

    private DrawableRequestBuilder<String> withGlide(ImageView into, String url) {
        return Glide.with(into.getContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE);
    }

    public interface GlideCallback {
        void onGlideReady();
    }
}
