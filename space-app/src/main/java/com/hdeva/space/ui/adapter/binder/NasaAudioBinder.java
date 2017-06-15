package com.hdeva.space.ui.adapter.binder;

import android.text.TextUtils;
import android.view.View;

import com.hdeva.space.R;
import com.hdeva.space.base.ViewHolderBinder;
import com.hdeva.space.core.helper.NasaItemHelper;
import com.hdeva.space.core.model.images.AssetCollection;
import com.hdeva.space.core.model.images.NasaItem;
import com.hdeva.space.core.repository.NasaImagesRepository;
import com.hdeva.space.ui.adapter.holder.NasaAudioViewHolder;
import com.hdeva.space.ui.helper.AnimatorHelper;
import com.hdeva.space.ui.navigator.MediaViewerNavigator;

import javax.inject.Inject;

public class NasaAudioBinder implements ViewHolderBinder<NasaItem, NasaAudioViewHolder> {

    private NasaItemHelper helper;
    private NasaImagesRepository repository;
    private MediaViewerNavigator navigator;
    private AnimatorHelper animatorHelper;

    @Inject
    public NasaAudioBinder(NasaItemHelper helper, NasaImagesRepository repository, MediaViewerNavigator navigator, AnimatorHelper animatorHelper) {
        this.helper = helper;
        this.repository = repository;
        this.navigator = navigator;
        this.animatorHelper = animatorHelper;
    }

    @Override
    public void bind(NasaItem model, NasaAudioViewHolder holder) {
        holder.tag = model.data.get(0).nasaId;

        animatorHelper.pulsateImageView(holder.indicator);
        holder.indicator.setVisibility(View.VISIBLE);
        holder.cardView.setOnClickListener(null);
        holder.cardView.setClickable(false);

        holder.title.setText(helper.extractTitle(model));
        holder.subTitle.setText(helper.extractSubTitle(model));

        repository.getAssetForItem(model)
                .subscribe(item -> handleItem(model, item, holder), throwable -> handleError(model, holder));
    }

    private void handleItem(NasaItem model, AssetCollection item, NasaAudioViewHolder holder) {
        if (validate(model, holder)) {
            holder.indicator.setImageResource(R.drawable.ic_audio_white_72dp);
            holder.indicator.clearAnimation();
            holder.cardView.setOnClickListener(v -> navigator.openNasaItem(v.getContext(), model, item, null));
        }
    }

    private void handleError(NasaItem model, NasaAudioViewHolder holder) {
        if (validate(model, holder)) {
            holder.indicator.setImageResource(R.drawable.ic_broken_image_white_72dp);
            holder.indicator.clearAnimation();
        }
    }

    private boolean validate(NasaItem model, NasaAudioViewHolder holder) {
        return TextUtils.equals(holder.tag, model.data.get(0).nasaId);
    }
}
