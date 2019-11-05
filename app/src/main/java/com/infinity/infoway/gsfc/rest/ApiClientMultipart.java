package com.infinity.infoway.gsfc.rest;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientMultipart
{
//    private static final String BASE_URL_s = "http://api.sauedu.in/";
    private static final String BASE_URL_s = "http://cmsapi.icrp.in";
    private static Retrofit retrofit = null;
    private static final String XYZ=null;
    public static Retrofit getClient()
    {
        if (retrofit == null)
        {

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_s)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


        }


        return retrofit;
    }

    Request request = new Request.Builder()
            .url("http://cms.atmiya.edu.in/api.txt")
            .build();

    public static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(120, TimeUnit.SECONDS)
            .connectTimeout(120, TimeUnit.SECONDS)
            .build();
}
