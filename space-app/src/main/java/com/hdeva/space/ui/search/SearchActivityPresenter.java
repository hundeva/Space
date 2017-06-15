package com.hdeva.space.ui.search;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.hdeva.space.base.ActivityPresenter;
import com.hdeva.space.core.model.images.ImageSearchResponse;
import com.hdeva.space.core.repository.NasaImagesRepository;
import com.hdeva.space.ui.adapter.NasaMediaAdapter;

import javax.inject.Inject;

import io.reactivex.Observable;

class SearchActivityPresenter extends ActivityPresenter<SearchActivity> {

    private NasaImagesRepository repository;
    private NasaMediaAdapter adapter;

    private Observable<ImageSearchResponse> observable;

    @Inject
    public SearchActivityPresenter(NasaImagesRepository repository, NasaMediaAdapter adapter) {
        this.repository = repository;
        this.adapter = adapter;
    }

    @Override
    public void attach(SearchActivity activity) {
        super.attach(activity);
        activity.errorMessage.setOnClickListener(v -> search(true));
        activity.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(activity.gridColumns, StaggeredGridLayoutManager.VERTICAL));
        activity.recyclerView.setAdapter(adapter);
        search(false);
    }

    private void search(boolean force) {
        if (force || observable == null) {
            showProgress();
            observable = repository.searchMedia(activity.request);
            observable.subscribe(this::handleResponse, this::handleError, this::handleComplete);
        }
    }

    private void handleResponse(ImageSearchResponse response) {
        if (!isAttached()) return;

        adapter.setItems(response);
        showResponse();
    }

    private void handleError(Throwable throwable) {
        if (!isAttached()) return;

        showError();
    }

    private void handleComplete() {
        if (!isAttached()) return;
    }

    private void showResponse() {
        activity.recyclerViewContainer.setVisibility(View.VISIBLE);
        activity.progressBarContainer.setVisibility(View.GONE);
        activity.errorMessageContainer.setVisibility(View.GONE);
    }

    private void showProgress() {
        activity.recyclerViewContainer.setVisibility(View.GONE);
        activity.progressBarContainer.setVisibility(View.VISIBLE);
        activity.errorMessageContainer.setVisibility(View.GONE);
    }

    private void showError() {
        activity.recyclerViewContainer.setVisibility(View.GONE);
        activity.progressBarContainer.setVisibility(View.GONE);
        activity.errorMessageContainer.setVisibility(View.VISIBLE);
    }
}
