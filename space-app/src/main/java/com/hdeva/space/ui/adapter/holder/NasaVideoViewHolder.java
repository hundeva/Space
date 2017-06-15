package com.hdeva.space.ui.adapter.holder;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdeva.space.R;
import com.hdeva.space.base.BaseViewHolder;

import butterknife.BindView;

public class NasaVideoViewHolder extends BaseViewHolder {

    @BindView(R.id.nasa_video_card_view)
    public CardView cardView;
    @BindView(R.id.nasa_video_image)
    public ImageView imageView;
    @BindView(R.id.nasa_video_indicator)
    public ImageView indicator;
    @BindView(R.id.nasa_video_title)
    public TextView title;
    @BindView(R.id.nasa_video_sub_title)
    public TextView subTitle;

    public String tag;
    
    public NasaVideoViewHolder(View itemView) {
        super(itemView);
    }
}
