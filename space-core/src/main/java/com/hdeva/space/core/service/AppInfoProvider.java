package com.hdeva.space.core.service;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.Locale;

import javax.inject.Inject;

public class AppInfoProvider {

    private static final String USER_AGENT_PATTERN = "NewHorizons v$1%s[$2%s] @ Android $3%s";

    private Context context;

    @Inject
    public AppInfoProvider(Context context) {
        this.context = context;
    }

    public AppInfo getAppInfo() {
        AppInfo appInfo = new AppInfo();
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            appInfo.versionName = packageInfo.versionName;
            appInfo.versionCode = String.valueOf(packageInfo.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            appInfo.versionName = "Unknown";
            appInfo.versionCode = "NaN";
        }
        return appInfo;
    }

    public String getUserAgent() {
        AppInfo appInfo = getAppInfo();
        String androidVersion = Build.VERSION.RELEASE;
        return String.format(Locale.getDefault(), USER_AGENT_PATTERN, appInfo.versionName, appInfo.versionCode, androidVersion);
    }

    public static class AppInfo {
        public String versionName;
        public String versionCode;
    }
}
