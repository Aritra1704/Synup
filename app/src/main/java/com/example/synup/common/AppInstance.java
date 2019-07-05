package com.example.synup.common;

import android.app.Application;
import android.content.Context;


import com.example.synup.BuildConfig;
import com.example.synup.webservices.ApiCalls;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppInstance extends Application {

    public static Retrofit retrofit;
    private static ApiCalls apiCalls;
    private OkHttpClient client;
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        initRetrofit();
    }

    private void initHTTPClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG)
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        else
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);//add None for prod env
        client = new OkHttpClient.Builder().connectTimeout(45, TimeUnit.SECONDS).readTimeout(45, TimeUnit.SECONDS).addInterceptor(interceptor).build();
    }

    private void initRetrofit() {
        initHTTPClient();
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiCalls = retrofit.create(ApiCalls.class);
    }

    public static ApiCalls getApiService() {
        return apiCalls;
    }

    public static Context getContext() {
        return sContext;
    }

    /*!
    Returns true if app is in debug mode.
     */
    public static boolean isDebugMode() {
        return BuildConfig.DEBUG;
    }
}

