package com.hdeva.space.core.net.interceptor;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NasaApiKeyInterceptor implements Interceptor {

    private static final String API_KEY_HEADER = "api_key";
    private static final String API_KEY_VALUE = "API_KEY";

    @Inject
    public NasaApiKeyInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl newUrl = request
                .url()
                .newBuilder()
                .addQueryParameter(API_KEY_HEADER, API_KEY_VALUE)
                .build();
        Request newRequest = request
                .newBuilder()
                .url(newUrl)
                .build();
        return chain.proceed(newRequest);
    }
}
