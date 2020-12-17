package com.example.testcatfacts.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {
    private static ApiFactory factory;
    private static final String BASE_URL = "https://cat-fact.herokuapp.com";
    private static Retrofit retrofit;

    private ApiFactory() {
        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    public static ApiFactory getInstance() {
        if (factory == null) {
            factory = new ApiFactory();
        }
        return factory;
    }

    public ApiService getApiService () {
        return retrofit.create(ApiService.class);
    }
}
