package com.hdeva.space.core.model.images;

import java.util.List;

public class ImageSearchResponse {
    public NasaMetadata metadata;
    public List<NasaItem> items;
    public List<NasaLink> links;
    public String href;
    public String version;
}
