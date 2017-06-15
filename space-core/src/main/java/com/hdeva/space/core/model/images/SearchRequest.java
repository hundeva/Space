package com.hdeva.space.core.model.images;

import android.text.TextUtils;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Parcel
public class SearchRequest {

    public String query;
    public boolean image;
    public boolean video;
    public boolean audio;
    public int minimumYear;
    public int maximumYear;

    public static SearchRequest create(String query, boolean image, boolean video, boolean audio, int minimumYear, int maximumYear) {
        SearchRequest request = new SearchRequest();
        request.query = query;
        request.image = image;
        request.video = video;
        request.audio = audio;
        request.minimumYear = minimumYear;
        request.maximumYear = maximumYear;
        return request;
    }

    public boolean isRequestValid() {
        return !TextUtils.isEmpty(query) || image || video || audio;
    }

    public Map<String, String> toRequestParameters() {
        Map<String, String> parameters = new HashMap<>();

        if (!TextUtils.isEmpty(query)) parameters.put("q", query);
        List<String> mediaTypes = new ArrayList<>();

        if (image) mediaTypes.add("image");
        if (video) mediaTypes.add("video");
        if (audio) mediaTypes.add("audio");
        if (mediaTypes.size() != 0) parameters.put("media_type", TextUtils.join(",", mediaTypes));
        parameters.put("year_start", String.valueOf(minimumYear));
        parameters.put("year_end", String.valueOf(maximumYear));
        return parameters;
    }
}
