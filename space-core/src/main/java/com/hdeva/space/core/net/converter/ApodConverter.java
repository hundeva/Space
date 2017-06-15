package com.hdeva.space.core.net.converter;

import com.hdeva.space.core.model.apod.Apod;
import com.hdeva.space.core.net.dto.apod.ApodDto;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import java.io.IOException;

public class ApodConverter {

    @FromJson
    Apod fromJson(ApodDto dto) throws IOException {
        Apod model = new Apod();
        model.date = dto.date;
        model.explanation = dto.explanation;
        model.hdurl = dto.hdurl;
        model.media_type = dto.media_type;
        model.service_version = dto.service_version;
        model.title = dto.title;
        model.url = dto.url;
        model.copyright = dto.copyright;
        return model;
    }

    @ToJson
    ApodDto toJson(Apod model) throws IOException {
        ApodDto dto = new ApodDto();
        dto.date = model.date;
        dto.explanation = model.explanation;
        dto.hdurl = model.hdurl;
        dto.media_type = model.media_type;
        dto.service_version = model.service_version;
        dto.title = model.title;
        dto.url = model.url;
        dto.copyright = model.copyright;
        return dto;
    }
}
