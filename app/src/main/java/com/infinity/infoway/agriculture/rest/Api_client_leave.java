package com.infinity.infoway.agriculture.rest;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user01 on 1/2/2018.
 */

public class Api_client_leave
{



        private static final String BASE_URL = "http://119.160.199.67:81/FeesTest/Leave/";
        private static Retrofit retrofit = null;

        public static Retrofit getClient()
        {
            if (retrofit==null)
            {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }

        public static OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .build();


    }



