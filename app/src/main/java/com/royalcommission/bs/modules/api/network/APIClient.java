package com.qanawat.modules.api.network;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.qanawat.app.AppController;
import com.qanawat.database.model.IP;
import com.qanawat.database.table.IPTable;
import com.qanawat.modules.utils.CommonUtils;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by Prashant.
 */
public class APIClient {

    private static final long TIMEOUT = 1;

    public static WebService getClient() {
        return getRetrofit().create(WebService.class);
    }

    private static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Objects.requireNonNull(getBaseURL()))
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient())
                .build();
    }

    private static OkHttpClient getOkHttpClient() {
        return new OkHttpClient().newBuilder()
                .connectTimeout(TIMEOUT, TimeUnit.MINUTES)
                .readTimeout(TIMEOUT, TimeUnit.MINUTES)
                .writeTimeout(TIMEOUT, TimeUnit.MINUTES)
                .cache(null)
                .addInterceptor(new HttpLoggingInterceptor())
                .build();
    }

    private static String getBaseURL() {
        IPTable iptable = new IPTable(AppController.getInstance());
        IP ip = iptable.getServerIP();
        String ipAddress = ip.getServerIP();
        String ipPort = ip.getServerPort();
        if (CommonUtils.isValidString(ipAddress) && CommonUtils.isValidString(ipAddress)) {
            return "http://" + ipAddress + ":" + ipPort + "/";
        } else {
            return null;
        }
    }

}
