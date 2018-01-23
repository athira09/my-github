package com.athira.mygithub.rest;

/**
 * Created by Athira.
 */

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static String BASE_URL = "https://api.github.com";
    public static String LOGIN_BASE_URL = "https://github.com";
    private static Retrofit retrofit = null;
    private static ApiInterface apiInterface;

    public static ApiInterface getClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class);

        return apiInterface;
    }

    public static ApiInterface getLoginClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(LOGIN_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class);

        return apiInterface;
    }
}