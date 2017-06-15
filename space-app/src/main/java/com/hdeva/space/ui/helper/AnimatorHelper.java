package com.hdeva.space.ui.helper;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.hdeva.space.R;

import javax.inject.Inject;

public class AnimatorHelper {

    @Inject
    public AnimatorHelper() {
    }

    public void pulsateImageView(ImageView imageView) {
        Animation pulsate = AnimationUtils.loadAnimation(imageView.getContext(), R.anim.pulsate_alpha);
        imageView.setImageResource(R.drawable.ic_stars_white_72dp);
        imageView.startAnimation(pulsate);
    }
}
