package com.hdeva.space.core.service.quality;

import javax.inject.Inject;

import io.paperdb.Book;

public class MediaQualityService {

    private static final String IMAGE_QUALITY_POLICY = "imageQualityPolicy";

    private Book book;

    @Inject
    public MediaQualityService(Book book) {
        this.book = book;
    }

    @MediaQualityPolicy
    public int getImageQualityPolicy() {
        @MediaQualityPolicy
        int policy = book.read(IMAGE_QUALITY_POLICY, MediaQualityPolicy.STANDARD);
        return policy;
    }

    public void setImageQualityPolicy(@MediaQualityPolicy int policy) {
        book.write(IMAGE_QUALITY_POLICY, policy);
    }
}
