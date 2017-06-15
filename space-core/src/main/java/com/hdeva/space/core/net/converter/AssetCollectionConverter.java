package com.hdeva.space.core.net.converter;

import com.annimon.stream.Collectors;
import com.annimon.stream.Optional;
import com.annimon.stream.Stream;
import com.hdeva.space.core.model.images.AssetCollection;
import com.hdeva.space.core.net.dto.images.AssetSearchResponseDto;
import com.squareup.moshi.FromJson;

import java.util.Collections;

public class AssetCollectionConverter {

    @FromJson
    AssetCollection fromJson(AssetSearchResponseDto dto) {
        AssetCollection model = new AssetCollection();
        if (dto.collection != null) {
            model.href = dto.collection.href;
            model.items = Stream.of(Optional
                    .of(dto.collection.items)
                    .orElse(Collections.emptyList()))
                    .map(item -> item.href)
                    .collect(Collectors.toList());
            model.version = dto.collection.version;
        }
        return model;
    }
}
