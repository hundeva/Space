package com.hdeva.space.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.hdeva.space.R;
import com.hdeva.space.base.BaseActivity;
import com.hdeva.space.core.model.images.SearchRequest;

import org.parceler.Parcels;

import butterknife.BindInt;
import butterknife.BindView;

public class SearchActivity extends BaseActivity<SearchActivityPresenter> {

    private static final String SEARCH_REQUEST = "searchRequest";

    @BindView(R.id.search_recycler_view_container)
    ViewGroup recyclerViewContainer;
    @BindView(R.id.search_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.search_progress_bar_container)
    ViewGroup progressBarContainer;
    @BindView(R.id.search_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.search_error_message_container)
    ViewGroup errorMessageContainer;
    @BindView(R.id.search_error_message)
    ViewGroup errorMessage;

    @BindInt(R.integer.grid_columns)
    int gridColumns;

    SearchRequest request;

    public static void launch(Context context, SearchRequest request) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(SEARCH_REQUEST, Parcels.wrap(request));
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        enableActionBarBack();
        request = Parcels.unwrap(getIntent().getParcelableExtra(SEARCH_REQUEST));
    }
}
