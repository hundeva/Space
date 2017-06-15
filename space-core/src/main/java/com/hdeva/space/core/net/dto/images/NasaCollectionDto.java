package com.hdeva.space.core.net.dto.images;

import java.util.List;

public class NasaCollectionDto {
    public NasaMetadataDto metadata;
    public List<NasaItemDto> items;
    public List<NasaLinkDto> links;
    public String href;
    public String version;
}
