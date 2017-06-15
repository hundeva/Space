package com.hdeva.space.ui.home;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.SwitchCompat;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.hdeva.space.R;
import com.hdeva.space.base.BaseActivity;

import butterknife.BindView;

public class HomeActivity extends BaseActivity<HomeActivityPresenter> {

    @BindView(R.id.home_drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.home_navigation_view)
    NavigationView navigationView;
    @BindView(R.id.support_appbar)
    AppBarLayout appBar;
    @BindView(R.id.home_parallax_title)
    TextView parallaxTitle;
    @BindView(R.id.home_parallax_sub_title)
    TextView parallaxSubTitle;
    @BindView(R.id.home_parallax)
    ImageView parallax;
    @BindView(R.id.home_activity_title)
    TextView title;
    @BindView(R.id.home_bottom_navigation)
    AHBottomNavigation bottomNavigation;
    @BindView(R.id.home_fab)
    FloatingActionButton fab;
    @BindView(R.id.home_bottom_sheet)
    ViewGroup bottomSheet;
    @BindView(R.id.bottom_sheet_close)
    ImageView bottomSheetClose;
    @BindView(R.id.bottom_sheet_search)
    ImageView bottomSheetSearch;
    @BindView(R.id.home_bottom_sheet_free_text)
    EditText bottomSheetText;
    @BindView(R.id.home_bottom_sheet_date_range)
    CrystalRangeSeekbar dateRange;
    @BindView(R.id.home_bottom_sheet_date_range_from)
    TextView dateRangeFrom;
    @BindView(R.id.home_bottom_sheet_date_range_to)
    TextView dateRangeTo;
    @BindView(R.id.home_bottom_sheet_image)
    SwitchCompat bottomSheetImage;
    @BindView(R.id.home_bottom_sheet_video)
    SwitchCompat bottomSheetVideo;
    @BindView(R.id.home_bottom_sheet_audio)
    SwitchCompat bottomSheetAudio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Space_Theme_Home);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public void onBackPressed() {
        if (!presenter.onBackPressed()) {
            super.onBackPressed();
        }
    }

}
