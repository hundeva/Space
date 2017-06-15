package com.hdeva.space.core.service.quality;

import android.support.annotation.IntDef;

import static com.hdeva.space.core.service.quality.MediaQualityPolicy.HIGH_DEFINITION;
import static com.hdeva.space.core.service.quality.MediaQualityPolicy.LOW_DEFINITION;
import static com.hdeva.space.core.service.quality.MediaQualityPolicy.ORIGINAL;
import static com.hdeva.space.core.service.quality.MediaQualityPolicy.STANDARD;

@IntDef({ORIGINAL, HIGH_DEFINITION, STANDARD, LOW_DEFINITION})
public @interface MediaQualityPolicy {
    int ORIGINAL = 0;
    int HIGH_DEFINITION = 1;
    int STANDARD = 2;
    int LOW_DEFINITION = 3;
}
