package com.hdeva.space.ui.home.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.hdeva.space.R;
import com.hdeva.space.base.BaseFragment;

import butterknife.BindInt;
import butterknife.BindView;

public class RecentFragment extends BaseFragment<RecentFragmentPresenter> implements HomeFragment {

    public static final String TAG = "RecentFragment";

    @BindView(R.id.nasa_images_refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.nasa_images_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.nasa_images_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.nasa_images_error_message_container)
    LinearLayout errorMessageContainer;

    @BindInt(R.integer.grid_columns)
    int gridColumns;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nasa_images, container, false);
    }

    @Override
    public void onFragmentReselected() {
        getPresenter().tabReselected();
    }

}
