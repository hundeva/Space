package com.hdeva.space.core.helper;

import android.text.TextUtils;

import com.annimon.stream.Stream;
import com.hdeva.space.core.model.images.MediaType;
import com.hdeva.space.core.model.images.NasaData;
import com.hdeva.space.core.model.images.NasaItem;
import com.hdeva.space.core.service.DateService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class NasaItemHelper {

    private static final String JOIN_CHARACTER = " \u2022 "; // \u2022 is '•'

    private DateService dateService;

    @Inject
    public NasaItemHelper(DateService dateService) {
        this.dateService = dateService;
    }

    public String extractTitle(NasaItem item) {
        if (item.data == null || item.data.size() == 0) return null;
        return item.data.get(0).title;
    }

    public String extractSubTitle(NasaItem item) {
        if (item.data == null || item.data.size() == 0) return null;

        NasaData data = item.data.get(0);
        List<String> infos = new ArrayList<>();

        if (!TextUtils.isEmpty(data.dateCreated))
            infos.add(dateService.parseIsoDateTimeFormat(data.dateCreated));

        if (!TextUtils.isEmpty(data.photographer))
            infos.add(data.photographer);

        if (data.keywords != null) {
            Stream.of(data.keywords)
                    .filter(keyword -> !TextUtils.isEmpty(keyword))
                    .forEach(infos::add);
        }
        return TextUtils.join(JOIN_CHARACTER, infos);
    }

    public String extractDescription(NasaItem item) {
        NasaData data = item.data.get(0);
        return TextUtils.isEmpty(data.description508) ? data.description : data.description;
    }

    @MediaType
    public String extractMediaType(NasaItem item) {
        NasaData data = item.data.get(0);
        return TextUtils.isEmpty(data.mediaType) ? MediaType.IMAGE : data.mediaType;
    }
}
