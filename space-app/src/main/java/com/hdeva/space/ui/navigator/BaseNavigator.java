package com.hdeva.space.ui.navigator;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.text.TextUtils;
import android.view.View;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;

class BaseNavigator {

    @SuppressWarnings("unchecked")
    @SafeVarargs
    protected final Bundle createTransitionOptions(Activity activity, Pair<View, String>... pairs) {
        List<Pair> filteredPairs = Stream.of(pairs)
                .filter(pair -> pair.first != null)
                .filter(pair -> !TextUtils.isEmpty(pair.second))
                .collect(Collectors.toList());

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            View navigationBar = activity.findViewById(android.R.id.navigationBarBackground);
            View statusBar = activity.findViewById(android.R.id.statusBarBackground);

            if (statusBar != null) {
                filteredPairs.add(Pair.create(statusBar, statusBar.getTransitionName()));
            }

            if (navigationBar != null) {
                filteredPairs.add(Pair.create(navigationBar, navigationBar.getTransitionName()));
            }
        }

        return ActivityOptionsCompat.makeSceneTransitionAnimation(activity, filteredPairs.toArray(new Pair[filteredPairs.size()])).toBundle();
    }

}
