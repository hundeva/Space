package com.hdeva.space.core.model.images;

import android.support.annotation.StringDef;

import static com.hdeva.space.core.model.images.MediaType.AUDIO;
import static com.hdeva.space.core.model.images.MediaType.IMAGE;
import static com.hdeva.space.core.model.images.MediaType.VIDEO;

@StringDef({IMAGE, VIDEO, AUDIO})
public @interface MediaType {
    String IMAGE = "image";
    String VIDEO = "video";
    String AUDIO = "audio";
}
