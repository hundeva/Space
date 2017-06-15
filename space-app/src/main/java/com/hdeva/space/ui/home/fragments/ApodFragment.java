package com.hdeva.space.ui.home.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hdeva.space.R;
import com.hdeva.space.base.BaseFragment;

import butterknife.BindView;

public class ApodFragment extends BaseFragment<ApodFragmentPresenter> implements HomeFragment {

    public static final String TAG = "ApodFragment";

    @BindView(R.id.apod_card_view)
    CardView cardView;
    @BindView(R.id.apod_image)
    ImageView imageView;
    @BindView(R.id.apod_indicator)
    ImageView indicator;
    @BindView(R.id.apod_media_type)
    ImageView mediaType;
    @BindView(R.id.apod_title)
    TextView title;
    @BindView(R.id.apod_sub_title)
    TextView subTitle;
    @BindView(R.id.apod_yesterday)
    TextView yesterday;
    @BindView(R.id.apod_date_search)
    TextView dateSearch;
    @BindView(R.id.apod_tomorrow)
    TextView tomorrow;
    @BindView(R.id.apod_response)
    ViewGroup responseContainer;
    @BindView(R.id.apod_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.apod_error_message_container)
    LinearLayout errorMessageContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_apod, container, false);
    }

    @Override
    public void onFragmentReselected() {
        getPresenter().tabReselected();
    }

}
