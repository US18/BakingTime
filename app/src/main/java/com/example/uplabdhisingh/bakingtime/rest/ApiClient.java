package com.example.uplabdhisingh.bakingtime.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by uplabdhisingh on 11/02/18.
 */

public class ApiClient
{
    public static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient()
    {
        if (retrofit==null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
