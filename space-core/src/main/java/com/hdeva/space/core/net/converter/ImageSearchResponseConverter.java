package com.hdeva.space.core.net.converter;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.hdeva.space.core.model.images.ImageSearchResponse;
import com.hdeva.space.core.model.images.NasaData;
import com.hdeva.space.core.model.images.NasaItem;
import com.hdeva.space.core.model.images.NasaLink;
import com.hdeva.space.core.model.images.NasaMetadata;
import com.hdeva.space.core.net.dto.images.ImageSearchResponseDto;
import com.hdeva.space.core.net.dto.images.NasaDataDto;
import com.hdeva.space.core.net.dto.images.NasaItemDto;
import com.hdeva.space.core.net.dto.images.NasaLinkDto;
import com.hdeva.space.core.net.dto.images.NasaMetadataDto;
import com.squareup.moshi.FromJson;

import java.util.List;

public class ImageSearchResponseConverter {

    @FromJson
    ImageSearchResponse fromJson(ImageSearchResponseDto dto) {
        ImageSearchResponse model = new ImageSearchResponse();
        if (dto.collection != null) {
            model.metadata = fromMetadataDto(dto.collection.metadata);
            model.items = fromItemListDto(dto.collection.items);
            model.links = fromLinkListDto(dto.collection.links);
            model.href = dto.collection.href;
            model.version = dto.collection.version;
        }
        return model;
    }

    private NasaMetadata fromMetadataDto(NasaMetadataDto dto) {
        if (dto == null) return null;

        NasaMetadata model = new NasaMetadata();
        model.totalHits = dto.total_hits;
        return model;
    }

    private List<NasaItem> fromItemListDto(List<NasaItemDto> dtos) {
        return dtos == null ? null : Stream
                .of(dtos)
                .filter((dto) -> dto != null)
                .map(this::fromItemDto)
                .collect(Collectors.toList());
    }

    private NasaItem fromItemDto(NasaItemDto dto) {
        NasaItem model = new NasaItem();
        model.data = fromDataListDto(dto.data);
        model.links = fromLinkListDto(dto.links);
        return model;
    }

    private List<NasaData> fromDataListDto(List<NasaDataDto> dtos) {
        return dtos == null ? null : Stream
                .of(dtos)
                .filter((dto) -> dto != null)
                .map(this::fromDataDto)
                .collect(Collectors.toList());
    }

    private NasaData fromDataDto(NasaDataDto dto) {
        NasaData model = new NasaData();
        model.title = dto.title;
        model.nasaId = dto.nasa_id;
        model.dateCreated = dto.date_created;
        model.photographer = dto.photographer;
        model.keywords = dto.keywords;
        model.mediaType = dto.media_type;
        model.center = dto.center;
        model.description = dto.description;
        model.description508 = dto.description_508;
        return model;
    }

    private List<NasaLink> fromLinkListDto(List<NasaLinkDto> dtos) {
        return dtos == null ? null : Stream
                .of(dtos)
                .filter((dto) -> dto != null)
                .map(this::fromLinkDto)
                .collect(Collectors.toList());
    }

    private NasaLink fromLinkDto(NasaLinkDto dto) {
        NasaLink model = new NasaLink();
        model.render = dto.render;
        model.prompt = dto.prompt;
        model.rel = dto.rel;
        model.href = dto.href;
        return model;
    }
}
