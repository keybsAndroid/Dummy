package com.royalcommission.bs.modules.api.network;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.royalcommission.bs.app.AppController;
import com.royalcommission.bs.modules.utils.SharedPreferenceUtils;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Prashant.
 */
public class APIClient {

    private static final long TIMEOUT = 1;
    private static final int MAX_CACHE_SIZE_IN_BYTES = 5 * 1024 * 1024;
    private static final String CHILD = "okhttp_cache";

    public static WebService getClient() {
        return getRetrofit().create(WebService.class);
    }

    private static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(EndPoints.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(getOkHttpClient())
                .build();
    }

    private static OkHttpClient getOkHttpClient() {
        return new OkHttpClient().newBuilder()
                .connectTimeout(TIMEOUT, TimeUnit.MINUTES)
                .readTimeout(TIMEOUT, TimeUnit.MINUTES)
                .writeTimeout(TIMEOUT, TimeUnit.MINUTES)
                .cache(null)
                .addInterceptor(getHttpLoggingInterceptor())
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder()
                            .addHeader("Accept", "application/json")
                            .addHeader("ChannelType", "2")
                            .addHeader("Authorization", "Basic dXNlcjpwYXNzd29yZA==")
                            .addHeader("Content-Type", "application/json")
                            .addHeader("PatientID", Objects.requireNonNull(SharedPreferenceUtils.getPatientID(AppController.getInstance())));
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                })
                .cache(new Cache(new File(AppController.getInstance().getCacheDir(), CHILD), MAX_CACHE_SIZE_IN_BYTES))
                .build();
    }

    private static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }
}
