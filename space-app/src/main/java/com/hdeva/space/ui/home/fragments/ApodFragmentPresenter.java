package com.hdeva.space.ui.home.fragments;

import android.view.View;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.calendardatepicker.MonthAdapter;
import com.hdeva.space.R;
import com.hdeva.space.base.FragmentPresenter;
import com.hdeva.space.core.helper.GlideHelper;
import com.hdeva.space.core.model.apod.Apod;
import com.hdeva.space.core.repository.ApodRepository;
import com.hdeva.space.core.service.DateService;
import com.hdeva.space.ui.helper.AnimatorHelper;
import com.hdeva.space.ui.navigator.MediaViewerNavigator;

import org.threeten.bp.LocalDate;

import javax.inject.Inject;

class ApodFragmentPresenter extends FragmentPresenter<ApodFragment> {

    private static final String DATE_PICKER_TAG = "datePicker";

    private ApodRepository apodRepository;
    private DateService dateService;
    private GlideHelper glideHelper;
    private MediaViewerNavigator navigator;
    private AnimatorHelper animatorHelper;

    private LocalDate date;
    private LocalDate minimumDate;
    private LocalDate maximumDate;

    @Inject
    public ApodFragmentPresenter(ApodRepository apodRepository, DateService dateService, GlideHelper glideHelper, MediaViewerNavigator navigator, AnimatorHelper animatorHelper) {
        this.apodRepository = apodRepository;
        this.dateService = dateService;
        this.glideHelper = glideHelper;
        this.navigator = navigator;
        date = dateService.today().minusDays(1);
        maximumDate = dateService.today();
        this.animatorHelper = animatorHelper;
        minimumDate = LocalDate.of(1995, 6, 16);
    }

    @Override
    public void attach(ApodFragment fragment) {
        super.attach(fragment);
        fragment.errorMessageContainer.setOnClickListener(v -> getApod());
        fragment.yesterday.setOnClickListener(this::handleYesterday);
        fragment.dateSearch.setOnClickListener(this::handleDateSearch);
        fragment.tomorrow.setOnClickListener(this::handleTomorrow);
        getApod();
    }

    public void tabReselected() {
        date = dateService.today().minusDays(1);
        getApod();
    }

    private void handleYesterday(View view) {
        date = date.minusDays(1);
        getApod();
    }

    private void handleDateSearch(View view) {
        MonthAdapter.CalendarDay start = new MonthAdapter.CalendarDay(minimumDate.getYear(), minimumDate.getMonthValue() - 1, minimumDate.getDayOfMonth());
        MonthAdapter.CalendarDay end = new MonthAdapter.CalendarDay(maximumDate.getYear(), maximumDate.getMonthValue() - 1, maximumDate.getDayOfMonth());

        new CalendarDatePickerDialogFragment()
                .setOnDateSetListener(this::fromJavaCalendar)
                .setPreselectedDate(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth())
                .setDateRange(start, end)
                .show(fragment.getFragmentManager(), DATE_PICKER_TAG);
    }

    private void handleTomorrow(View view) {
        date = date.plusDays(1);
        getApod();
    }

    private void fromJavaCalendar(CalendarDatePickerDialogFragment calendarDatePickerDialogFragment, int year, int monthOfYear, int day) {
        date = LocalDate.of(year, monthOfYear + 1, day);
        getApod();
    }

    private void getApod() {
        setButtonStates();
        showProgress();

        apodRepository.getApodForDate(date)
                .subscribe(this::handleApod, this::handleError);
    }

    private void setButtonStates() {
        fragment.tomorrow.setEnabled(!date.isEqual(maximumDate));
        fragment.yesterday.setEnabled(!date.isEqual(minimumDate));
    }

    private void handleApod(Apod apod) {
        if (!isAttached()) return;

        animatorHelper.pulsateImageView(fragment.indicator);
        fragment.indicator.setVisibility(View.VISIBLE);
        fragment.imageView.setImageDrawable(null);
        glideHelper.setApodImageAsync(fragment.imageView, apod, () -> {
            if (isAttached()) {
                fragment.indicator.setVisibility(View.GONE);
                fragment.indicator.clearAnimation();
            }
        });
        fragment.title.setText(apod.title);
        fragment.subTitle.setText(fragment.getContext().getString(R.string.apod_date, dateService.parseApodDateFormat(apod.date)));
        fragment.imageView.setOnClickListener(v -> navigator.openApod(fragment.getContext(), apod, fragment.imageView));
        showResponse();
    }

    private void handleError(Throwable throwable) {
        if (!isAttached()) return;

        showError();
    }

    private void showResponse() {
        fragment.responseContainer.setVisibility(View.VISIBLE);
        fragment.progressBar.setVisibility(View.GONE);
        fragment.errorMessageContainer.setVisibility(View.GONE);
    }

    private void showProgress() {
        fragment.responseContainer.setVisibility(View.GONE);
        fragment.progressBar.setVisibility(View.VISIBLE);
        fragment.errorMessageContainer.setVisibility(View.GONE);
    }

    private void showError() {
        fragment.responseContainer.setVisibility(View.GONE);
        fragment.progressBar.setVisibility(View.GONE);
        fragment.errorMessageContainer.setVisibility(View.VISIBLE);
    }

}
