package com.hdeva.space.ui.home.fragments;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.hdeva.space.base.FragmentPresenter;
import com.hdeva.space.core.model.images.ImageSearchResponse;
import com.hdeva.space.core.repository.NasaImagesRepository;
import com.hdeva.space.core.store.NasaImagesStore;
import com.hdeva.space.ui.adapter.NasaMediaAdapter;

import javax.inject.Inject;

import io.reactivex.Observable;

class RecentFragmentPresenter extends FragmentPresenter<RecentFragment> {

    private NasaImagesRepository repository;
    private NasaImagesStore store;
    private NasaMediaAdapter adapter;

    private Observable<ImageSearchResponse> observable;

    @Inject
    public RecentFragmentPresenter(NasaImagesRepository repository, NasaImagesStore store, NasaMediaAdapter adapter) {
        this.repository = repository;
        this.store = store;
        this.adapter = adapter;
    }

    @Override
    public void attach(RecentFragment fragment) {
        super.attach(fragment);
        setupViews(fragment);
        showStoredItems();
        refreshItems(false);
    }

    public void tabReselected() {
        fragment.recyclerView.smoothScrollToPosition(0);
    }

    private void setupViews(RecentFragment fragment) {
        fragment.errorMessageContainer.setOnClickListener(this::handleErrorRefresh);
        fragment.refreshLayout.setOnRefreshListener(() -> refreshItems(true));

        fragment.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(fragment.gridColumns, StaggeredGridLayoutManager.VERTICAL));
        fragment.recyclerView.setAdapter(adapter);
    }

    private void showStoredItems() {
        ImageSearchResponse storedResponse = store.getRecentItems();
        if (storedResponse != null) {
            adapter.setItems(storedResponse);
            showResponse();
        }
    }

    private void refreshItems(boolean force) {
        if (force || observable == null) {
            if (store.hasRecentItemsStored()) fragment.refreshLayout.setRefreshing(true);
            observable = repository.getRecentItems();
            observable.subscribe(this::handleResponse, this::handleError, this::handleComplete);
        }
    }

    private void handleErrorRefresh(View view) {
        showProgress();
        refreshItems(true);
    }

    private void handleResponse(ImageSearchResponse response) {
        if (!isAttached()) return;

        adapter.setItems(response);
        showResponse();
    }

    private void handleError(Throwable throwable) {
        if (!isAttached()) return;

        ImageSearchResponse storedResponse = store.getRecentItems();
        if (storedResponse == null) {
            showError();
        } else {
            adapter.setItems(storedResponse);
            showResponse();
        }
    }

    private void handleComplete() {
        if (!isAttached()) return;

        fragment.refreshLayout.setRefreshing(false);
    }

    private void showResponse() {
        fragment.refreshLayout.setVisibility(View.VISIBLE);
        fragment.progressBar.setVisibility(View.GONE);
        fragment.errorMessageContainer.setVisibility(View.GONE);
    }

    private void showProgress() {
        fragment.refreshLayout.setVisibility(View.GONE);
        fragment.progressBar.setVisibility(View.VISIBLE);
        fragment.errorMessageContainer.setVisibility(View.GONE);
    }

    private void showError() {
        fragment.refreshLayout.setVisibility(View.GONE);
        fragment.progressBar.setVisibility(View.GONE);
        fragment.errorMessageContainer.setVisibility(View.VISIBLE);
    }

}
