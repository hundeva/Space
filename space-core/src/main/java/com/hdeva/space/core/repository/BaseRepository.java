package com.hdeva.space.core.repository;

import android.util.Log;

import com.hdeva.space.core.net.error.EndpointException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

abstract class BaseRepository {

    private static final String TAG = "BaseRepository";

    protected <T> Observable<T> queue(Call<T> call) {
        return Observable
                .fromCallable(() -> execute(call))
                .timeout(3, TimeUnit.MINUTES)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    protected <T> T execute(Call<T> call) throws EndpointException {
        T result;
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                result = response.body();
            } else {
                String reason = response.errorBody() == null ? "Unknown" : response.errorBody().string();
                String error = String.format("Error response received, reason is: %s", reason);
                Log.e(TAG, error);
                throw new EndpointException(error);
            }
        } catch (IOException e) {
            Log.e(TAG, "Error executing request", e);
            throw new EndpointException(e);
        }
        return result;
    }
}
