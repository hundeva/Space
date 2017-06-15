package com.hdeva.space.ui.home;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.view.View;
import android.widget.Toast;

import com.hdeva.space.R;
import com.hdeva.space.core.model.images.SearchRequest;
import com.hdeva.space.core.service.DateService;
import com.hdeva.space.ui.search.SearchActivity;

import org.threeten.bp.LocalDate;

import javax.inject.Inject;

class HomeSearchService {


    private int minimumYearRange;
    private int maximumYearRange;
    private int minimumYear;
    private int maximumYear;
    private BottomSheetBehavior bottomSheetBehavior;

    @BottomSheetBehavior.State
    private int bottomSheetState = BottomSheetBehavior.STATE_COLLAPSED;

    @Inject
    public HomeSearchService(DateService dateService) {
        LocalDate today = dateService.today();
        minimumYearRange = 1900;
        maximumYearRange = today.getYear();
        minimumYear = minimumYearRange;
        maximumYear = maximumYearRange;
    }

    public void setup(HomeActivity activity) {
        setupBottomSheet(activity);
        setupDateRange(activity);
        setupOther(activity);
    }

    private void setupBottomSheet(HomeActivity activity) {
        bottomSheetBehavior = BottomSheetBehavior.from(activity.bottomSheet);
        bottomSheetBehavior.setState(bottomSheetState);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                bottomSheetState = newState;
                setFabVisibility(activity);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    private void setupDateRange(HomeActivity activity) {
        activity.dateRange.setMinValue(minimumYearRange);
        activity.dateRange.setMaxValue(maximumYearRange);
        activity.dateRange.setMinStartValue(minimumYear);
        activity.dateRange.setMaxStartValue(maximumYear);
        activity.dateRange.setOnRangeSeekbarChangeListener((i1, i2) -> {
            minimumYear = activity.dateRange.getSelectedMinValue().intValue();
            maximumYear = activity.dateRange.getSelectedMaxValue().intValue();

            activity.dateRangeFrom.setText(String.valueOf(minimumYear));
            activity.dateRangeTo.setText(String.valueOf(maximumYear));
        });
        activity.dateRange.apply();
    }

    private void setupOther(HomeActivity activity) {
        activity.fab.post(() -> setFabVisibility(activity));
        activity.fab.setOnClickListener(v -> bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED));
        activity.bottomSheetClose.setOnClickListener(v -> bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED));
        activity.bottomSheetSearch.setOnClickListener(v -> search(activity));
        activity.bottomSheetText.setOnEditorActionListener((textView, actionId, keyEvent) -> handleEditorAction(activity));
    }

    public boolean hideBottomSheet() {
        boolean handled = bottomSheetState != BottomSheetBehavior.STATE_COLLAPSED;
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        return handled;
    }

    private void setFabVisibility(HomeActivity activity) {
        if (bottomSheetState == BottomSheetBehavior.STATE_COLLAPSED) {
            activity.fab.show();
        } else {
            activity.fab.hide();
        }
    }

    private boolean handleEditorAction(HomeActivity activity) {
        search(activity);
        return true;
    }

    private void search(HomeActivity activity) {
        String query = activity.bottomSheetText.getText().toString();
        boolean image = activity.bottomSheetImage.isChecked();
        boolean video = activity.bottomSheetVideo.isChecked();
        boolean audio = activity.bottomSheetAudio.isChecked();

        SearchRequest request = SearchRequest.create(query, image, video, audio, minimumYear, maximumYear);
        if (request.isRequestValid()) {
            SearchActivity.launch(activity, request);
        } else {
            Toast.makeText(activity, R.string.invalid_search_request, Toast.LENGTH_SHORT).show();
        }
    }

}
