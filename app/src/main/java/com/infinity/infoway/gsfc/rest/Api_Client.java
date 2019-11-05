package com.infinity.infoway.gsfc.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api_Client
{

    private static final String BASE_URL = "https://cms.atmiya.edu.in/API_Firebase_Notification.asmx/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient()
    {
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
       return  retrofit;
    }



}
