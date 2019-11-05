package com.infinity.infoway.gsfc.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user02 on 10/6/2017.
 */

public class Api_Rec_Client
{

    private static final String BASE_URL = "http://cms.atmiya.edu.in/API_Fees_Collection.asmx/";
    private static final String BASE_URL_s = "https://cms.atmiya.edu.in/API_Fees_Collection.asmx/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient()
    {
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_s)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


}
