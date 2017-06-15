package com.hdeva.space.ui.adapter.holder;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdeva.space.R;
import com.hdeva.space.base.BaseViewHolder;

import butterknife.BindView;

public class NasaAudioViewHolder extends BaseViewHolder {

    @BindView(R.id.nasa_audio_card_view)
    public CardView cardView;
    @BindView(R.id.nasa_audio_title)
    public TextView title;
    @BindView(R.id.nasa_audio_sub_title)
    public TextView subTitle;
    @BindView(R.id.nasa_audio_indicator)
    public ImageView indicator;

    public String tag;

    public NasaAudioViewHolder(View itemView) {
        super(itemView);
    }
}
