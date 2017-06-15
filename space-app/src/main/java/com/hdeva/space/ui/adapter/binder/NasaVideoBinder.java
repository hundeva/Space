package com.hdeva.space.ui.adapter.binder;

import android.text.TextUtils;
import android.view.View;

import com.hdeva.space.R;
import com.hdeva.space.base.ViewHolderBinder;
import com.hdeva.space.core.helper.GlideHelper;
import com.hdeva.space.core.helper.NasaItemHelper;
import com.hdeva.space.core.model.images.AssetCollection;
import com.hdeva.space.core.model.images.NasaItem;
import com.hdeva.space.core.repository.NasaImagesRepository;
import com.hdeva.space.core.service.extractor.MediaLinkExtractor;
import com.hdeva.space.ui.adapter.holder.NasaVideoViewHolder;
import com.hdeva.space.ui.helper.AnimatorHelper;
import com.hdeva.space.ui.navigator.MediaViewerNavigator;

import javax.inject.Inject;

public class NasaVideoBinder implements ViewHolderBinder<NasaItem, NasaVideoViewHolder> {

    private NasaItemHelper helper;
    private GlideHelper glideHelper;
    private NasaImagesRepository repository;
    private MediaViewerNavigator navigator;
    private AnimatorHelper animatorHelper;
    private MediaLinkExtractor mediaLinkExtractor;

    @Inject
    public NasaVideoBinder(NasaItemHelper helper, GlideHelper glideHelper, NasaImagesRepository repository, MediaViewerNavigator navigator, AnimatorHelper animatorHelper, MediaLinkExtractor mediaLinkExtractor) {
        this.helper = helper;
        this.glideHelper = glideHelper;
        this.repository = repository;
        this.navigator = navigator;
        this.animatorHelper = animatorHelper;
        this.mediaLinkExtractor = mediaLinkExtractor;
    }

    @Override
    public void bind(NasaItem model, NasaVideoViewHolder holder) {
        holder.tag = model.data.get(0).nasaId;

        animatorHelper.pulsateImageView(holder.indicator);
        holder.indicator.setVisibility(View.VISIBLE);
        holder.imageView.setImageDrawable(null);
        holder.cardView.setOnClickListener(null);
        holder.cardView.setClickable(false);

        holder.title.setText(helper.extractTitle(model));
        holder.subTitle.setText(helper.extractSubTitle(model));

        repository.getAssetForItem(model)
                .subscribe(item -> handleItem(model, item, holder), throwable -> handleError(model, holder));
    }

    private void handleItem(NasaItem model, AssetCollection item, NasaVideoViewHolder holder) {
        if (mediaLinkExtractor.hasValidImageUrl(item)) {
            glideHelper.setNasaMediaImageAsync(holder.imageView, item, () -> finaliseImage(model, item, holder));
        } else {
            finaliseImage(model, item, holder);
        }
    }

    private void handleError(NasaItem model, NasaVideoViewHolder holder) {
        if (validate(model, holder)) {
            holder.indicator.setImageResource(R.drawable.ic_broken_image_white_72dp);
            holder.indicator.clearAnimation();
        }
    }

    private void finaliseImage(NasaItem model, AssetCollection item, NasaVideoViewHolder holder) {
        if (validate(model, holder)) {
            holder.indicator.setVisibility(View.VISIBLE);
            holder.indicator.clearAnimation();
            holder.indicator.setImageResource(R.drawable.ic_play_white_72dp);
            holder.cardView.setOnClickListener(v -> navigator.openNasaItem(v.getContext(), model, item, holder.imageView));
        }
    }

    private boolean validate(NasaItem model, NasaVideoViewHolder holder) {
        return TextUtils.equals(holder.tag, model.data.get(0).nasaId);
    }
}
