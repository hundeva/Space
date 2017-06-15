package com.hdeva.space.core.model;

import com.hdeva.space.core.model.images.MediaType;

import org.parceler.Parcel;

@Parcel
public class MediaDescriptor {
    @MediaType
    public String mediaType;
    public String title;
    public String subTitle;
    public String description;
    public String thumbnail;
    public String media;
}
